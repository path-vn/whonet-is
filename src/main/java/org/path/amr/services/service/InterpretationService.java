package org.path.amr.services.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import liquibase.util.csv.CSVWriter;
import liquibase.util.csv.opencsv.CSVParser;
import liquibase.util.csv.opencsv.CSVReader;
import org.apache.commons.compress.utils.IOUtils;
import org.path.amr.services.config.WhonetConfiguration;
import org.path.amr.services.domain.Antibiotic;
import org.path.amr.services.domain.ExpertInterpretationRules;
import org.path.amr.services.domain.Organism;
import org.path.amr.services.repository.*;
import org.path.amr.services.service.dto.*;
import org.path.amr.services.service.impl.InterpretationWorker;
import org.path.amr.services.service.mapper.*;
import org.path.amr.services.web.rest.WhonetResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
public class InterpretationService {

    private final Logger log = LoggerFactory.getLogger(WhonetResource.class);

    public static final String PATTERN_0 = "[^0-9.RSI<=>]";
    public static final String PATTERN_1 = "[^0-9.]";
    public static final String PATTERN_2 = "[^0-9]";
    public static final String PATTERN_3 = "[0-9.RSI]";
    public static final String PATTERN_4 = "[0-9.<=>]";

    public static final String GENUS_CODE = "GENUS_CODE";
    public static final String FAMILY_CODE = "FAMILY_CODE";
    public static final String SEROVAR_GROUP = "SEROVAR_GROUP";
    public static final String DEFAULT_BREAKPOINT_TYPE = "Human";

    public static final short LESS_THAN = 1;
    public static final short LESS_THAN_OR_EQUAL = 2;
    public static final short EQUAL = 3;
    public static final short GREATER_THAN_OR_EQUAL = 4;
    public static final short GREATER_THAN = 5;

    public static final short RESULT_TYPE_QUESTION_MARK = 1;
    public static final short RESULT_TYPE_NORMAL = 0;

    CustomRepository customRepository;
    OrganismRepository organismRepository;
    OrganismMapper organismMapper;
    BreakpointRepository breakpointRepository;
    BreakpointMapper breakpointMapper;
    AntibioticRepository antibioticRepository;
    AntibioticMapper antibioticMapper;
    IntrinsicResistanceRepository intrinsicResistanceRepository;
    IntrinsicResistanceMapper intrinsicResistanceMapper;
    ExpertInterpretationRulesRepository expertInterpretationRulesRepository;
    ExpertInterpretationRulesMapper expertInterpretationRulesMapper;
    WhonetConfiguration whonetConfiguration;
    Map<String, Integer> specTypeSort = new HashMap<>();
    Map<String, List<OrganismBreakPointDTO>> cacheBreakpoints = new HashMap<>();
    Map<String, List<OrganismIntrinsicResistanceAntibioticDTO>> cacheIntrinsics = new HashMap<>();

    public InterpretationService(
        CustomRepository customRepository,
        OrganismRepository organismRepository,
        OrganismMapper organismMapper,
        BreakpointRepository breakpointRepository,
        BreakpointMapper breakpointMapper,
        AntibioticRepository antibioticRepository,
        AntibioticMapper antibioticMapper,
        IntrinsicResistanceRepository intrinsicResistanceRepository,
        IntrinsicResistanceMapper intrinsicResistanceMapper,
        ExpertInterpretationRulesRepository expertInterpretationRulesRepository,
        ExpertInterpretationRulesMapper expertInterpretationRulesMapper,
        WhonetConfiguration whonetConfiguration
    ) {
        this.customRepository = customRepository;
        this.organismRepository = organismRepository;
        this.breakpointRepository = breakpointRepository;
        this.antibioticRepository = antibioticRepository;
        this.organismMapper = organismMapper;
        this.breakpointMapper = breakpointMapper;
        this.antibioticMapper = antibioticMapper;
        this.intrinsicResistanceMapper = intrinsicResistanceMapper;
        this.intrinsicResistanceRepository = intrinsicResistanceRepository;
        this.expertInterpretationRulesMapper = expertInterpretationRulesMapper;
        this.expertInterpretationRulesRepository = expertInterpretationRulesRepository;
        this.whonetConfiguration = whonetConfiguration;

        if (this.whonetConfiguration.getPriority() != null) {
            List<String> spec = Arrays.asList(this.whonetConfiguration.getPriority().split(","));
            for (int i = 0; i < spec.size(); i++) {
                specTypeSort.put(spec.get(i), i + 1);
            }
        }
        log.info(this.whonetConfiguration.getPriority(), this.specTypeSort.size());
    }

    public List<OrganismBreakPointDTO> getBreakpoints(
        String orgCode,
        String whonet5Test,
        String breakpointType,
        Integer year,
        List<String> guidelines,
        String organismCodeType
    ) {
        if (year == null) {
            year = whonetConfiguration.getYear();
        }
        if (guidelines == null) {
            guidelines = new ArrayList<>();
            guidelines.addAll(Arrays.asList(whonetConfiguration.getClsi().split(",")));
        }
        String key = String.format(
            "%s%s%s%s%s%s",
            orgCode,
            whonet5Test,
            breakpointType,
            year.toString(),
            String.join(",", guidelines),
            organismCodeType
        );
        if (cacheBreakpoints.containsKey(key)) {
            return cacheBreakpoints.get(key);
        }
        List<OrganismBreakPointDTO> newPoint = customRepository
            .getBreakPoints(orgCode, whonet5Test, breakpointType, "C", year, guidelines, organismCodeType)
            .stream()
            .peek(
                ob -> {
                    breakpointRepository.findById(ob.getBreakPointID()).map(breakpointMapper::toDto).ifPresent(ob::setBreakpoint);
                    organismRepository.findById(ob.getOrganismID()).map(organismMapper::toDto).ifPresent(ob::setOrganism);
                }
            )
            .collect(Collectors.toList());
        cacheBreakpoints.put(key, newPoint);
        return newPoint;
    }

    private double mode2(Double value, Double micS, Double micR, Short oper) {
        if (micS == null) {
            return value;
        }

        if (micR == null) {
            return value;
        }

        double Rrange = value <= micS ? micS / 4 : micR / 4;
        if (oper.equals(GREATER_THAN) || oper.equals(EQUAL)) {
            boolean uplevel = oper.equals(GREATER_THAN);
            double valueNextLevel = Rrange;
            while (!(valueNextLevel >= value)) {
                valueNextLevel = valueNextLevel * 2;
            }
            value = uplevel ? valueNextLevel * 2 : valueNextLevel;
        }

        if (oper.equals(LESS_THAN)) {
            double valueNextLevel = value > micS ? micS : micS / 2;
            do {
                valueNextLevel = valueNextLevel * 2;
            } while (!(valueNextLevel >= value));
            value = valueNextLevel / 2;
        }

        return value;
    }

    public double mode(double a) {
        double result = a;
        for (int i = 0; i < a / 2 + 2; i++) {
            result = Math.pow(2, i);
            if (result >= a) {
                break;
            }
        }
        return result;
    }

    /**
     * A. Interpretation algorithm – single measurement
     * <p>
     * 1.Determine whether the organism and antibiotic combination in question has a matching rule in
     * the Intrinsic Resistance table. This will also involve the Organisms table to extract information
     * about the organism (such as the genus for intrinsic rules which apply to a whole genus).
     * a. If yes, then this organism is intrinsically resistant to the antibiotic, and should be
     * interpreted as resistant.
     * <p>
     * 2. If the organism is not intrinsically resistant (an absence of matching intrinsic resistance rules
     * above), then we determine if there are applicable breakpoints. After filtering the Breakpoints
     * table on your desired guideline and year, the Organisms table is used to further match the
     * Breakpoints table with your organism. For example, a breakpoint rule might apply to the
     * GENUS_CODE “HA-“, so we must use the Organisms table to determine this membership based
     * on the actual organism code, which might be “hin” for Haemophilus influenzae.
     * a. If one or more breakpoint rules match the organism and antibiotic, you can either
     * evaluate and report all of them, or sort them in a particular order to determine the
     * “default” or “best” interpretation.
     * b. The precise order of breakpoints partially depends upon your needs. For example, a
     * veterinary isolate might require that the BREAKPOINT_TYPE = “Animal” take precedence
     * over any “Human” breakpoints.
     * c. There are also non-variable parts to the sorting order. For example, we must prefer a
     * more-specific breakpoint over a less-specific, such as favoring a breakpoint specifically
     * for E. coli over a broader breakpoint which applies for all Enterobacteriaceae.
     * <p>
     * 3. Now that you have a sorted list of breakpoints, you can evaluate one or more of them to
     * determine the interpretation for your measurement.
     * <p>
     * B. Interpretation algorithm – complete isolate
     */
    public void execute(IsolateDTO isolate) {
        isolate.setBreakpointType(isolate.getBreakpointType() == null ? DEFAULT_BREAKPOINT_TYPE : isolate.getBreakpointType());
        Optional<Organism> organism = organismRepository.findFirstByWhonetOrgCode(isolate.getOrgCode());
        organism.ifPresent(value -> isolate.setOrganism(organismMapper.toDto(value)));
        for (int i = 0; i < isolate.getTest().size(); i++) {
            TestDTO test = isolate.getTest().get(i);
            String tmpStringValue = test.getRawValue();
            tmpStringValue = tmpStringValue.replaceAll(PATTERN_0, "");
            String testValue = tmpStringValue.replaceAll(PATTERN_1, "");
            String oper = tmpStringValue.replaceAll(PATTERN_3, "");
            String result = tmpStringValue.replaceAll(PATTERN_4, "");
            String abxCode = test.getWhonet5Code().split("_")[0];

            Optional<Antibiotic> antibiotic = antibioticRepository.findFirstByWhonetAbxCode(abxCode);
            if (antibiotic.isEmpty()) {
                continue;
            }
            test.setAntibiotic(antibioticMapper.toDto(antibiotic.get()));

            if (!testValue.isEmpty()) {
                test.setValue(Double.valueOf(testValue));
            }

            switch (oper) {
                case "<":
                    test.setOper(LESS_THAN);
                    break;
                case "<=":
                    test.setOper(LESS_THAN_OR_EQUAL);
                    break;
                case ">=":
                    test.setOper(GREATER_THAN_OR_EQUAL);
                    break;
                case ">":
                    test.setOper(GREATER_THAN);
                    break;
                default:
                    test.setOper(EQUAL);
                    break;
            }
            if (!result.isEmpty()) {
                test.addResult(result);
            }

            // A.1 Check intrinsic resistance
            List<OrganismIntrinsicResistanceAntibioticDTO> organismIntrinsicResistanceAntibioticDTOList = getIntrinsicResistance(
                isolate.getOrgCode(),
                test.getWhonet5Code().replaceAll("_NE", "_NM").replaceAll("\\.", "_"),
                isolate.getGuidelines(),
                isolate.getOrganismCodeTypeOrder()
            );

            if (organismIntrinsicResistanceAntibioticDTOList.size() > 0) {
                test.setIntrinsicResistance(organismIntrinsicResistanceAntibioticDTOList);
                test.addResult("R");
            } else {
                // A.2, 3 Apply breakpoints
                List<OrganismBreakPointDTO> organismBreakPointDTOList = getBreakpoints(
                    isolate.getOrgCode(),
                    test.getWhonet5Code().replaceAll("_NE", "_NM").replaceAll("\\.", "_"),
                    isolate.getBreakpointType(),
                    isolate.getYear(),
                    isolate.getGuidelines(),
                    isolate.getOrganismCodeTypeOrder()
                );

                for (int ob = 0; ob < organismBreakPointDTOList.size(); ob++) {
                    OrganismBreakPointDTO o = organismBreakPointDTOList.get(ob);
                    InterpretationResult interpretationResult = interpretation(test, o);
                    if (interpretationResult != null && interpretationResult.getResult() != null) {
                        interpretationResult.setPriority(ob);
                        isolate.setOrganism(o.getOrganism());
                        test.addResult(interpretationResult);
                    }
                }

                if (organismBreakPointDTOList.size() == 0) {
                    test.addResult(new InterpretationResult("?"));
                }
            }

            test.sort(this.specTypeSort);
        }
        if (isolate.getOrganism() == null) {
            return;
        }
        // B. Apply expert rules
        applyExpertRules(isolate);
    }

    public List<OrganismIntrinsicResistanceAntibioticDTO> getIntrinsicResistance(
        String orgCode,
        String whonet5Test,
        List<String> guidelines,
        String organismCodeTypeOrder
    ) {
        if (guidelines == null) {
            guidelines = new ArrayList<>();
            guidelines.add("CLSI");
        }
        String key = String.format("%s%s%s%s", orgCode, whonet5Test, String.join(",", guidelines), organismCodeTypeOrder);
        if (cacheIntrinsics.containsKey(key)) {
            return cacheIntrinsics.get(key);
        }
        List<OrganismIntrinsicResistanceAntibioticDTO> newList = customRepository
            .getIntrinsicResistance(orgCode, whonet5Test, guidelines, organismCodeTypeOrder)
            .stream()
            .peek(
                oir -> {
                    antibioticRepository.findById(oir.getAntibioticId()).map(antibioticMapper::toDto).ifPresent(oir::setAntibiotic);
                    intrinsicResistanceRepository
                        .findById(oir.getIntrinsicResistanceId())
                        .map(intrinsicResistanceMapper::toDto)
                        .ifPresent(oir::setIntrinsicResistance);
                }
            )
            .collect(Collectors.toList());
        cacheIntrinsics.put(key, newList);
        return newList;
    }

    @Transactional(readOnly = true)
    public void applyExpertRules(IsolateDTO isolate) {
        List<ExpertInterpretationRules> expertInterpretationRulesList = expertInterpretationRulesRepository.findAll();
        for (int i = 0; i < expertInterpretationRulesList.size(); i++) {
            ExpertInterpretationRules expertInterpretationRules = expertInterpretationRulesList.get(i);

            switch (expertInterpretationRules.getOrganismCodeType()) {
                case GENUS_CODE:
                    if (
                        isolate.getOrganism().getGenusCode() != null &&
                        isolate.getOrganism().getGenusCode().equals(expertInterpretationRules.getOrganismCode())
                    ) {
                        applyExpertRule(expertInterpretationRules, isolate);
                    }
                    break;
                case FAMILY_CODE:
                    if (
                        isolate.getOrganism().getFamilyCode() != null &&
                        isolate.getOrganism().getFamilyCode().equals(expertInterpretationRules.getOrganismCode())
                    ) {
                        applyExpertRule(expertInterpretationRules, isolate);
                    }
                    break;
                case SEROVAR_GROUP:
                    if (
                        isolate.getOrganism().getSerovarGroup() != null &&
                        isolate.getOrganism().getSerovarGroup().equals(expertInterpretationRules.getOrganismCode())
                    ) {
                        applyExpertRule(expertInterpretationRules, isolate);
                    }
                    break;
                default:
            }
        }
    }

    public boolean notMatchRuleCriteria(ExpertInterpretationRules expertInterpretationRules, IsolateDTO isolate) {
        List<RuleDTO> ruleDTOList = parseRules(expertInterpretationRules.getRuleCriteria())
            .stream()
            .map(
                r -> {
                    processRule(r, isolate);
                    return r;
                }
            )
            .collect(Collectors.toList());
        // Rebuild the rule to boolean logic
        String rule = expertInterpretationRules.getRuleCriteria();
        for (int i = 0; i < ruleDTOList.size(); i++) {
            rule = rule.replace(ruleDTOList.get(i).getRaw(), ruleDTOList.get(i).getResult() ? "true" : "false");
        }
        ExpressionParser parser = new SpelExpressionParser();
        Boolean apply = parser.parseExpression(rule).getValue(Boolean.class);
        if (apply == null || !apply) {
            return true;
        }
        return false;
    }

    public void applyExpertRule(ExpertInterpretationRules expertInterpretationRules, IsolateDTO isolate) {
        // CHECK RULE CRITERIA
        if (notMatchRuleCriteria(expertInterpretationRules, isolate)) {
            return;
        }

        // ANTIBIOTIC
        String affectAntibiotic = expertInterpretationRules.getAffectedAntibiotics() + ",";
        String exceptionAntibiotic = expertInterpretationRules.getAntibioticExceptions() + ",";

        for (int i = 0; i < isolate.getTest().size(); i++) {
            // antibiotic in the exception list => not set
            if (
                exceptionAntibiotic.contains(isolate.getTest().get(i).getAntibiotic().getWhonetAbxCode() + ",") ||
                exceptionAntibiotic.contains(isolate.getTest().get(i).getAntibiotic().getAntiboticClass() + ",")
            ) {
                continue;
            }
            isolate.getTest().get(i).setExpertRuleCode(expertInterpretationRules.getRuleCode());
            if (
                affectAntibiotic.contains(isolate.getTest().get(i).getAntibiotic().getWhonetAbxCode() + ",") ||
                affectAntibiotic.contains(isolate.getTest().get(i).getAntibiotic().getAntiboticClass() + ",")
            ) {
                // no result => add
                if (isolate.getTest().get(i).getResult() == null) {
                    isolate
                        .getTest()
                        .get(i)
                        .addResult(expertInterpretationRules.getResult() == null ? "R" : expertInterpretationRules.getResult());
                    continue;
                }
                for (int j = 0; j < isolate.getTest().get(i).getResult().size(); j++) {
                    isolate
                        .getTest()
                        .get(i)
                        .getResult()
                        .get(j)
                        .setResult(expertInterpretationRules.getResult() == null ? "R" : expertInterpretationRules.getResult());
                }
            }
        }
    }

    /***
     *
     *
     * @param r
     * @param isolate
     *
     * RULE_CRITERIA are of three types.
     *   i. A three-letter antibiotic code. e.g., OXA=NS, which means “Not susceptible to
     * Oxacillin”
     *
     *   ii. A data field. e.g., ESBL=+, which means “The ESBL data field must have a value
     * of ‘+’”
     *
     *   iii. Antibiotic class. There is only one example in use presently: CEPH3=R, meaning
     * “Resistant to any of the Cephalosporin III antibiotics.” In this case, it will be
     * necessary to join or otherwise lookup the PROF_CLASS=CEPH3 antibiotics.
     *
     */
    public void processRule(RuleDTO r, IsolateDTO isolate) {
        // case 2
        if (
            isolate.getDataFields() != null &&
            isolate.getDataFields().containsKey(r.getName()) &&
            isolate.getDataFields().get(r.getName()) != null
        ) {
            r.setResult(isolate.getDataFields().get(r.getName()).contains(r.getValue()));
            return;
        }

        // case 1
        r.setResult(false);
        for (int i = 0; i < isolate.getTest().size(); i++) {
            if (isolate.getTest().get(i).getAntibiotic() == null) {
                continue;
            }
            // case 2
            if (
                (
                    isolate.getTest().get(i).getAntibiotic().getWhonetAbxCode() != null &&
                    isolate.getTest().get(i).getAntibiotic().getWhonetAbxCode().equals(r.getName())
                ) ||
                // case 3
                (
                    isolate.getTest().get(i).getAntibiotic().getProfClass() != null &&
                    isolate.getTest().get(i).getAntibiotic().getProfClass().equals(r.getName())
                )
            ) {
                if (isolate.getTest() == null || isolate.getTest().get(i).getResult() == null) {
                    continue;
                }
                for (int j = 0; j < isolate.getTest().get(i).getResult().size(); j++) {
                    if (isolate.getTest().get(i).getResult().get(j).getResult().equals(r.getValue())) {
                        r.setResult(true);
                        break;
                    }
                    // todo: cần confirm lại, với expert rule yc test với KS có kết qủa là XX
                    // trong trường hợp ra nhiều kết quả thì chỉ xét kết quả đầu hay lấy cả kết quả sau
                    break;
                }
            }
        }
    }

    /***
     *
     * Parse rules into chunks
     *      *
     *      * MECA_PCR=+ OR MRSA=+ OR OXA_SCREEN=+ OR MRSA_SCRN=+ OR PBP2A_AGGL=+
     *      *
     *      * => List Rule
     *      *        - name = MECA_PCR, value = +
     *      *        - name = MRSA, value = +
     *      *        - name = OXA_SCREEN, value = +
     *      *        ...
     *
     * */
    public List<RuleDTO> parseRules(String ruleCriteria) {
        String pattern = "(\\w+)=(\\w+|-|\\+)";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(ruleCriteria);
        List<RuleDTO> result = new ArrayList<>();
        while (m.find()) {
            if (m.groupCount() == 2) {
                result.add(new RuleDTO(m.group(0), m.group(1), m.group(2)));
            }
        }
        return result;
    }

    private boolean notEmpty(String s) {
        return s != null && !s.equals("");
    }

    public InterpretationResult interpretation(TestDTO testResult, OrganismBreakPointDTO organismBreakPointDTO) {
        InterpretationResult result = new InterpretationResult();
        Double valueToInterpretation = testResult.getValue();
        if (valueToInterpretation == null) {
            return null;
        }
        BreakpointDTO g = organismBreakPointDTO.getBreakpoint();
        result.setOrganismCodeType(g.getOrganismCodeType());
        String method = g.getTestMethod();
        short oper = testResult.getOper();
        boolean hasBreakpoint = notEmpty(g.getI()) || notEmpty(g.getR()) || notEmpty(g.getS());
        result.setSpecType(g.getSiteOfInfection());
        if (hasBreakpoint) {
            Double R = notEmpty(g.getR()) ? Double.valueOf(g.getR()) : null;
            Double S = notEmpty(g.getS()) ? Double.valueOf(g.getS()) : null;
            String[] Ix = notEmpty(g.getI()) ? g.getI().replace("'", "").split("-") : null;
            Double IMin = Ix != null && Ix.length > 0 && notEmpty(Ix[0]) ? Double.valueOf(Ix[0]) : null;
            Double IMax = null;
            if (Ix != null && Ix.length > 1) {
                IMax = notEmpty(Ix[Ix.length - 1]) ? Double.valueOf(Ix[Ix.length - 1]) : null;
            }

            if (method.equalsIgnoreCase("DISK")) {
                String diskr = R != null ? R.toString() : "";
                String diskimin = IMin != null ? IMin.toString() : "";
                String diskimax = IMax != null ? IMax.toString() : "";
                String disks = S != null ? S.toString() : "";
                result.setBreaking(diskr + "," + diskimin + "-" + diskimax + "," + disks);
            } else {
                String mics = S != null ? S.toString() : "";
                String micr = R != null ? R.toString() : "";
                result.setBreaking(mics + "," + micr);
            }

            result.setIsQuestionMark(RESULT_TYPE_NORMAL);
            if (method.equalsIgnoreCase("DISK")) {
                if (R != null && R >= 0) {
                    if ((valueToInterpretation < R) || (valueToInterpretation.doubleValue() == R.doubleValue() && oper <= EQUAL)) {
                        result.setResult("R");
                    }
                }

                if (S != null && S > 0) {
                    if ((valueToInterpretation > S) || (valueToInterpretation.doubleValue() == S.doubleValue() && oper >= EQUAL)) {
                        result.setResult("S");
                    }
                }

                if (IMin != null && IMin >= 0 && IMax != null && IMax >= 0) {
                    if (
                        (valueToInterpretation < IMin) || (valueToInterpretation.doubleValue() == IMin.doubleValue() && oper <= LESS_THAN)
                    ) {
                        result.setResult("R");
                    } else if (
                        (valueToInterpretation > IMax) ||
                        (valueToInterpretation.doubleValue() == IMax.doubleValue() && oper >= GREATER_THAN)
                    ) {
                        result.setResult("S");
                    } else {
                        result.setResult("I");
                    }
                } else if (R != null && R >= 0 && S != null && S > 0) {
                    if ((valueToInterpretation > R) || (valueToInterpretation.doubleValue() == R.doubleValue() && oper >= GREATER_THAN)) {
                        if ((valueToInterpretation < S) || (valueToInterpretation.doubleValue() == S.doubleValue() && oper <= LESS_THAN)) {
                            result.setResult("I");
                        }
                    }
                }

                if (S != null && (oper == GREATER_THAN || oper == GREATER_THAN_OR_EQUAL) && valueToInterpretation < S) {
                    result.setResult("S");
                    result.setIsQuestionMark(RESULT_TYPE_QUESTION_MARK);
                }

                if (R != null && (oper == LESS_THAN || oper == LESS_THAN_OR_EQUAL) && valueToInterpretation > R) {
                    result.setResult("R");
                    result.setIsQuestionMark(RESULT_TYPE_QUESTION_MARK);
                }

                if (S != null && valueToInterpretation < S && R == null && oper < GREATER_THAN_OR_EQUAL) {
                    result.setResult("NS");
                }

                if (R != null && valueToInterpretation > R && S == null && oper > LESS_THAN_OR_EQUAL) {
                    result.setResult("NR");
                }
            } else if (method.equalsIgnoreCase("MIC") || method.equalsIgnoreCase("Etest")) {
                double value = this.mode2(valueToInterpretation, S, R, oper);
                Short newOper = oper;
                // mode2 do that thing!!!
                newOper = newOper.equals(LESS_THAN) ? LESS_THAN_OR_EQUAL : newOper;
                newOper = newOper.equals(GREATER_THAN) ? GREATER_THAN_OR_EQUAL : newOper;

                if (R != null && R >= 0) {
                    if ((value > R) || (value == R && newOper >= EQUAL)) {
                        result.setResult("R");
                    } else if (value < R || (value == R && newOper <= LESS_THAN)) {
                        if (S != null && S > 0) {
                            if ((value > S) || (value == S && newOper >= GREATER_THAN)) {
                                result.setResult("I");
                            }
                        }
                    }
                }

                if (S != null && S > 0) {
                    if ((value < S) || (value == S && newOper <= EQUAL)) {
                        result.setResult("S");
                    } else if (value > S || (value == S && newOper >= GREATER_THAN)) {
                        if (R != null && R > 0) {
                            if ((value < R) || (value == R && newOper <= LESS_THAN)) {
                                result.setResult("I");
                            }
                        }
                    }
                }

                if (R != null && (newOper == GREATER_THAN || newOper == GREATER_THAN_OR_EQUAL) && value < R) {
                    result.setResult("R");
                    result.setIsQuestionMark(RESULT_TYPE_QUESTION_MARK);
                    // todo: remove this NS logic
                    if (S != null && value <= R / 2 && value >= S) {
                        result.setResult("NS");
                        result.setIsQuestionMark(RESULT_TYPE_NORMAL);
                    }
                }

                if (S != null && (newOper == LESS_THAN || newOper == LESS_THAN_OR_EQUAL) && value > S) {
                    result.setResult("S");
                    result.setIsQuestionMark(RESULT_TYPE_QUESTION_MARK);
                }

                if (S != null && value > S && R == null && newOper > LESS_THAN_OR_EQUAL) {
                    result.setResult("NS");
                }

                if (R != null && value < R && S == null && newOper < GREATER_THAN_OR_EQUAL) {
                    result.setResult("NR");
                }
            }
        }
        if (!hasBreakpoint) {
            result.setResult("?");
        }
        return result;
    }

    InputStream zip(InputStream inp, String name) throws IOException {
        ByteArrayOutputStream zipOutputStream = new ByteArrayOutputStream();

        ZipOutputStream zipOut = new ZipOutputStream(zipOutputStream);
        ZipEntry zipEntry = new ZipEntry(name);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = inp.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        zipOut.close();
        zipOut.close();
        return new ByteArrayInputStream(zipOutputStream.toByteArray());
    }

    @Async
    public void processFile(
        MailService mailService,
        List<String[]> lines,
        String filename,
        String email,
        String action,
        String breakpoint,
        String intrinsic,
        String noEmpty,
        String filterEqual,
        String organismCodeTypeOrder,
        int thread
    )
        throws IOException, ExecutionException, InterruptedException, ServerException, InsufficientDataException, NoSuchAlgorithmException, InternalException, InvalidResponseException, XmlParserException, InvalidKeyException, ErrorResponseException {
        List<List<String>> outputLine = processLineData(
            lines,
            action,
            breakpoint,
            intrinsic,
            noEmpty,
            filterEqual,
            organismCodeTypeOrder,
            thread
        );

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        CSVWriter writer = new CSVWriter(new OutputStreamWriter(byteArrayOutputStream));
        for (int i = 0; i < outputLine.size(); i++) {
            writer.writeNext(outputLine.get(i).toArray(new String[0]));
        }
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss");
        String strDate = dateFormat.format(date);

        writer.flush();
        putObject(
            new ByteArrayInputStream(byteArrayOutputStream.toByteArray()),
            "kks/" + strDate + "_" + filename,
            "static",
            "application/csv",
            false
        );

        writer.close();
        byteArrayOutputStream.close();
        log.info("Sending mail {}", email);
        mailService.sendEmail(
            email,
            "hkien02@gmail.com",
            "[Interpretation result] file: " + strDate + "_" + filename,
            String.format(
                "Please find the download <a href='%s'>file for the calculation</a>",
                "http://server.zmedia.vn/static/kks/" + strDate + "_" + filename
            ),
            true,
            true
        );
    }

    public void putObject(InputStream inp, String key, String bucket, String contentType, boolean createBucket)
        throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, XmlParserException {
        MinioClient minioClient = MinioClient
            .builder()
            .endpoint("https://oss2.nextidc.net")
            .credentials("zXzL2c3iEdgWJDV8RQnUBg", "MVKaThUtQ5tuCUfyh5zA6A")
            .build();

        // Make 'asiatrip' bucket if not exist.
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (!found && createBucket) {
            // Make a new bucket called 'asiatrip'.
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            found = true;
        }

        if (!found) {
            throw new IOException(
                "Bucket not found " + bucket + " " + minioClient.listBuckets().stream().map(b -> b.name()).collect(Collectors.joining(","))
            );
        }

        minioClient.putObject(
            PutObjectArgs.builder().bucket(bucket).contentType("text/javascript").object(key).stream(inp, -1, 50 * 1024 * 1024).build()
        );
    }

    public String getMethodByColumnName(String name) {
        String result = "";
        if (name.toUpperCase(Locale.ROOT).contains("_ND")) {
            result = "DISK";
        }
        if (name.toUpperCase(Locale.ROOT).contains("_NE")) {
            result = "ETEST";
        }
        if (name.toUpperCase(Locale.ROOT).contains("_NM")) {
            result = "MIC";
        }
        if (name.toUpperCase(Locale.ROOT).contains("INTERP")) {
            result = "";
        }
        return result;
    }

    public char getWhonetFileSeparator(String inp, int check) {
        Matcher m = Pattern.compile("COUNTRY_A(?<sep>.*)LABORATORY").matcher(inp.toUpperCase(Locale.ROOT));
        char rs = ',';
        boolean found = false;
        while (m.find()) {
            String text = m.group(0).replace("COUNTRY_A", "").replace("LABORATORY", "");
            if (text.length() == 1) {
                rs = text.charAt(0);
            }
            if (text.length() > 1) {
                rs = text.charAt(1);
            }

            found = true;
        }
        if (!found && check == 0) {
            return getWhonetFileSeparator(inp, 1);
        }
        if (!found && check == 1) {
            inp = inp.toUpperCase(Locale.ROOT).replace("COUNTRY", "Country_A");
            return getWhonetFileSeparator(inp, 2);
        }

        return rs;
    }

    public char getWhonetFileSeparator(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        char sep = ',';
        while (reader.ready()) {
            String line = reader.readLine();
            sep = getWhonetFileSeparator(line, 0);
            break;
        }
        return sep;
    }

    public List<List<String>> processLineData(
        List<String[]> data,
        String action,
        String breakpoint,
        String intrinsic,
        String noEmpty,
        String filterEqual,
        String organismCodeTypeOrder,
        int thread
    ) throws ExecutionException, InterruptedException {
        log.info("processLineData {}", data.size());
        boolean unpivod = action.equals("unpivot");
        int maxPivotSize = 1;
        String[] columnHeaders = mapHeader(data.get(0));
        Map<String, Integer> testColumnMaps = new HashMap<>();
        Map<String, Integer> headerMaps = new HashMap<>();
        Map<String, Integer> baseColumnList = new HashMap<>();
        Map<String, List<String>> testColumnRefMap = new HashMap<>();
        for (int i = 0; i < columnHeaders.length; i++) {
            String method = getMethodByColumnName(columnHeaders[i]);
            if (!method.equals("")) {
                testColumnMaps.put(columnHeaders[i], i);
            } else if (!columnHeaders[i].contains("_INTERP")) {
                baseColumnList.put(columnHeaders[i], i);
            }
            headerMaps.put(columnHeaders[i].toUpperCase(Locale.ROOT), i);
        }
        log.info("processLineData test size {}", testColumnMaps.size());
        for (Map.Entry<String, Integer> entry : testColumnMaps.entrySet()) {
            List<String> ref = new ArrayList<>();
            for (String columnHeader : columnHeaders) {
                if (columnHeader.contains(entry.getKey()) && !columnHeader.equals(entry.getKey())) {
                    ref.add(columnHeader);
                }
            }
            if (ref.size() > maxPivotSize) {
                maxPivotSize = ref.size();
            }
            testColumnRefMap.put(entry.getKey(), ref);
        }

        if (testColumnMaps.size() == 0) {
            throw new RuntimeException("No test columns found");
        }
        int organismIndex = headerMaps.get("ORGANISM");
        if (organismIndex < 0 || organismIndex > columnHeaders.length) {
            throw new RuntimeException("ORGANISM index invalid");
        }
        List<IsolateDTO> isolateDTOList = new ArrayList<>();
        for (int i = 1; i < data.size(); i++) {
            String[] columns = data.get(i);
            if (columns.length != columnHeaders.length) {
                throw new RuntimeException(
                    "Data columns not match header columns " + columns.length + " " + columnHeaders.length + " row : " + i
                );
            }
            IsolateDTO isolate = new IsolateDTO();
            isolate.setOrgCode(columns[organismIndex]);
            isolate.setDataFields(headerMaps, columns);
            isolate.setRequestID(i + "");
            isolate.setOrganismCodeTypeOrder(organismCodeTypeOrder);
            for (Map.Entry<String, Integer> entry : testColumnMaps.entrySet()) {
                TestDTO test = new TestDTO();
                if (entry.getValue() < 0 || entry.getValue() > columns.length) {
                    continue;
                }
                if (columns[entry.getValue()].trim().equals("")) {
                    continue;
                }
                test.setRawValue(columns[entry.getValue()]);
                test.setWhonet5Code(entry.getKey());
                isolate.addTest(test);
            }
            isolateDTOList.add(isolate);
        }
        log.info("processLineData isolateDTOList size {}", isolateDTOList.size());
        List<IsolateDTO> result = execIsolateDTOS(isolateDTOList, thread);
        log.info("processLineData result size {}", result.size());
        Map<String, Integer> isolateResultMap = new HashMap<>();
        for (int i = 0; i < result.size(); i++) {
            isolateResultMap.put(result.get(i).getRequestID(), i);
        }
        // re-build csv file
        List<String> headerBuilder = new ArrayList<>();
        List<List<String>> dataRebuild = new ArrayList<>();
        for (int i = 0; i < columnHeaders.length; i++) {
            String cur = columnHeaders[i];
            if (unpivod && !baseColumnList.containsKey(cur)) {
                continue;
            }
            headerBuilder.add(cur);
            if (testColumnMaps.containsKey(columnHeaders[i])) {
                String inpCol = columnHeaders[i] + "_INTERP";
                if (headerMaps.containsKey(inpCol)) {
                    inpCol = inpCol + "_1";
                }
                headerBuilder.add(inpCol);
            }
        }
        if (unpivod) {
            headerBuilder.add("antibiotic");
            headerBuilder.add("raw");
            headerBuilder.add("result");

            if (breakpoint.equalsIgnoreCase("yes")) {
                headerBuilder.add("breakpoint");
            }
            if (intrinsic.equalsIgnoreCase("yes")) {
                headerBuilder.add("intrinsic");
            }
            for (int k = 0; k < maxPivotSize * 2; k++) {
                headerBuilder.add("OLD_" + k);
            }
        }
        TestDTO defaultDTo = new TestDTO();
        defaultDTo.addResult("");
        dataRebuild.add(headerBuilder);
        for (int i = 1; i < data.size(); i++) {
            String[] columns = data.get(i);

            IsolateDTO thisIsolate = result.get(isolateResultMap.get("" + i));
            Map<String, TestDTO> testResult = new HashMap<>();
            for (int k = 0; k < thisIsolate.getTest().size(); k++) {
                TestDTO test = thisIsolate.getTest().get(k);
                if (test.getResult() != null && test.getResult().size() > 0) {
                    testResult.put(test.getWhonet5Code(), test);
                }
            }
            if (!unpivod) {
                List<String> columnBuilder = new ArrayList<>();
                for (int j = 0; j < columns.length; j++) {
                    columnBuilder.add(columns[j]);
                    if (testColumnMaps.containsKey(columnHeaders[j])) {
                        columnBuilder.add(testResult.getOrDefault(columnHeaders[j], defaultDTo).getResult().get(0).getResult());
                    }
                }
                dataRebuild.add(columnBuilder);
            }
            if (unpivod) {
                List<String> columnBuilder = new ArrayList<>();
                for (int j = 0; j < columns.length; j++) {
                    if (!baseColumnList.containsKey(columnHeaders[j])) {
                        continue;
                    }
                    columnBuilder.add(columns[j]);
                }
                for (Map.Entry<String, Integer> entry : testColumnMaps.entrySet()) {
                    List<String> columnPivotBuilder = new ArrayList<>();
                    columnPivotBuilder.addAll(columnBuilder);
                    // tên ks
                    columnPivotBuilder.add(entry.getKey());
                    // kết quả
                    columnPivotBuilder.add(columns[entry.getValue()]);
                    // phiên giải
                    String interpretationResult = testResult.getOrDefault(entry.getKey(), defaultDTo).getResult().get(0).getResult();
                    String questionMark = testResult.getOrDefault(entry.getKey(), defaultDTo).getResult().get(0).getIsQuestionMark() == 1
                        ? "?"
                        : "";
                    interpretationResult = interpretationResult + questionMark;
                    columnPivotBuilder.add(interpretationResult);

                    if (breakpoint.equalsIgnoreCase("yes")) {
                        String breaking = testResult.getOrDefault(entry.getKey(), defaultDTo).getResult().get(0).getBreaking();

                        columnPivotBuilder.add(breaking);
                    }

                    if (intrinsic.equalsIgnoreCase("yes")) {
                        String intrinsicValue = "";
                        if (
                            testResult.getOrDefault(entry.getKey(), defaultDTo).getIntrinsicResistance() != null &&
                            testResult.getOrDefault(entry.getKey(), defaultDTo).getIntrinsicResistance().size() > 0
                        ) {
                            intrinsicValue = testResult.getOrDefault(entry.getKey(), defaultDTo).getIntrinsicResistance().get(0).toString();
                        }
                        columnPivotBuilder.add(intrinsicValue);
                    }

                    List<String> ref = testColumnRefMap.get(entry.getKey());
                    boolean isEmpty = true;
                    Map<String, Integer> resultList = new HashMap<>();
                    for (String columnRef : ref) {
                        // kết quả liên quan
                        columnPivotBuilder.add(columnRef);
                        String thisResult = columns[headerMaps.get(columnRef)].trim();

                        columnPivotBuilder.add(thisResult);
                        if (!thisResult.equals("")) {
                            isEmpty = false;
                        }

                        resultList.put(thisResult, 1);
                    }

                    if (filterEqual.equals("yes") && resultList.size() == 1 && resultList.containsKey(interpretationResult)) {
                        continue;
                    }

                    if (noEmpty.equals("yes") && interpretationResult.equals("") && isEmpty) {
                        continue;
                    }

                    if (ref.size() < maxPivotSize) {
                        for (int k = 0; k < maxPivotSize - ref.size(); k++) {
                            columnPivotBuilder.add("");
                            columnPivotBuilder.add("");
                        }
                    }

                    dataRebuild.add(columnPivotBuilder);
                }
            }
        }
        log.info("processLineData dataRebuild size {}", dataRebuild.size());

        return dataRebuild;
    }

    public List<IsolateDTO> execIsolateDTOS(List<IsolateDTO> isolateDTO, int thread) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(thread);
        Set<Callable<IsolateDTO>> callables = new HashSet<Callable<IsolateDTO>>();
        for (int i = 0; i < isolateDTO.size(); i++) {
            callables.add(new InterpretationWorker(this, isolateDTO.get(i)));
        }
        List<Future<IsolateDTO>> futures = executorService.invokeAll(callables);
        List<IsolateDTO> result = new ArrayList<>();
        for (Future<IsolateDTO> future : futures) {
            result.add(future.get());
        }
        executorService.shutdown();
        return result;
    }

    private String[] mapHeader(String[] headers) {
        String[] nameCode =
            "COUNTRY_A,Laboratory,Origin,PATIENT_ID,LAST_NAME,FIRST_NAME,Sex,DATE_BIRTH,Age,PAT_TYPE,WARD,INSTITUT,Department,WARD_TYPE,DATE_ADMIS,SPEC_NUM,SPEC_DATE,SPEC_TYPE,LOCAL_SPEC,SPEC_CODE,SPEC_REAS,ISOL_NUM,Organism,X_INFSOUR,LOCAL_ORG,ORG_TYPE,Serotype,MRSA,VRE,BETA_LACT,ESBL,CARBAPENEM,MRSA_SCRN,INDUC_CLI,Comment,DATE_DATA,N219001073,FULL_NAME".split(
                    ","
                );
        String[] nameDisplay =
            "Country\tLaboratory\tOrigin\tIdentification number\tLast name\tFirst name\tSex\tDate of birth\tAge\tAge category\tLocation\tInstitution\tDepartment\tLocation type\tDate of admission\tSpecimen number\tSpecimen date\tSpecimen type\tLocal specimen code\tSpecimen type (Numeric)\tReason\tIsolate number\tOrganism\tX_INFSOUR\tLocal organism code\tOrganism type\tSerotype\tMRSA\tVRE\tBeta-lactamase\tESBL\tCarbapenem resistance\tMRSA screening test\tInducible clindamycin resistance\tComment\tDate of data entry\tN219001073\tFull name".split(
                    "\\t"
                );
        Map<String, String> mapName = new HashMap<>();
        for (int i = 0; i < nameCode.length; i++) {
            mapName.put(nameDisplay[i], nameCode[i]);
        }
        return Arrays
            .stream(headers)
            .map(
                f -> {
                    if (mapName.containsKey(f)) {
                        return mapName.get(f);
                    }
                    return f;
                }
            )
            .toArray(String[]::new);
    }

    public List<String[]> mergeInputStreams(MultipartFile[] files) throws IOException {
        log.info("Merge inputstream {}", Arrays.stream(files).map(MultipartFile::getOriginalFilename).collect(Collectors.joining(",")));

        int master = -1;
        Map<Integer, List<String[]>> data = new HashMap<>();
        Map<Integer, Map<String, Integer>> testColumnMaps = new HashMap<>();
        Map<Integer, Map<String, Integer>> fullColumnMaps = new HashMap<>();

        for (int i = 0; i < files.length; i++) {
            InputStream inputStream = files[i].getInputStream();
            byte[] bytes = IOUtils.toByteArray(inputStream);

            char sep = getWhonetFileSeparator(new ByteArrayInputStream(bytes));

            CSVParser parser = new CSVParser(sep);
            CSVReader csvReader = new CSVReader(new InputStreamReader(new ByteArrayInputStream(bytes)), 0, parser);

            List<String[]> lines = csvReader.readAll();
            data.put(i, lines);

            if (lines.size() <= 1) {
                throw new RuntimeException("Data only have header");
            }

            Map<String, Integer> mapColums = new HashMap<>();
            Map<String, Integer> mapFullColums = new HashMap<>();

            String[] columnHeaders = mapHeader(lines.get(0));
            for (int j = 0; j < columnHeaders.length; j++) {
                String method = getMethodByColumnName(columnHeaders[j]);
                if (!method.equals("")) {
                    mapColums.put(columnHeaders[j], j);
                }
                mapFullColums.put(columnHeaders[j], j);
            }

            log.info("******* {} {} {}", sep, lines.get(0).length, lines.get(1).length);
            if (master == -1 && isMaster(mapColums, lines.get(1))) {
                master = i;
            }
            testColumnMaps.put(i, mapColums);
            fullColumnMaps.put(i, mapFullColums);
            if (mapColums.containsKey("PATIENT_ID")) {
                throw new RuntimeException("PATIENT_ID column not found");
            }
            if (mapColums.containsKey("SPEC_DATE")) {
                throw new RuntimeException("SPEC_DATE column not found");
            }
            if (mapColums.containsKey("SPEC_TYPE")) {
                throw new RuntimeException("SPEC_TYPE column not found");
            }
            if (mapColums.containsKey("SPEC_NUM")) {
                throw new RuntimeException("SPEC_NUM column not found");
            }
        }
        if (data.size() == 1) {
            return data.get(0);
        }

        if (master == -1) {
            log.info("Not master found, get first one {-1}");
            master = 0;
        }
        List<String[]> dataMaster = data.get(master);
        Map<String, Integer> masterFullColumnMaps = fullColumnMaps.get(master);

        Map<Integer, List<String>> result = new HashMap<>();
        for (int i = 0; i < dataMaster.size(); i++) {
            result.put(i, Arrays.asList(dataMaster.get(i)));
        }

        for (Map.Entry<Integer, List<String[]>> entry : data.entrySet()) {
            if (entry.getKey() == master) {
                continue;
            }
            if (result.size() != entry.getValue().size()) {
                throw new RuntimeException(String.format("Data size not match %d %d", master, entry.getKey()));
            }
            Map<String, List<String>> mapSlave = new HashMap<>();
            Map<String, Integer> columnMaps = fullColumnMaps.get(entry.getKey());
            Map<Integer, String> newColumnMaps = new HashMap<>();

            for (Map.Entry<String, Integer> sub : columnMaps.entrySet()) {
                String columnName = sub.getKey();
                boolean dup = masterFullColumnMaps.containsKey(sub.getKey());

                if (dup && getMethodByColumnName(sub.getKey()).equals("") && !sub.getKey().contains("INTERP")) {
                    continue;
                }
                if (dup) {
                    columnName = columnName + "_INTERP_1";
                }

                newColumnMaps.put(sub.getValue(), columnName);
            }

            if (newColumnMaps.size() == 0) {
                throw new RuntimeException("Not found columns to map");
            }

            String[] header = entry.getValue().get(0);
            List<String> newHeader = IntStream
                .range(0, header.length)
                .filter(newColumnMaps::containsKey)
                .mapToObj(newColumnMaps::get)
                .collect(Collectors.toList());

            mapListAppend(result, 0, newHeader);

            for (int i = 1; i < entry.getValue().size(); i++) {
                String[] thisColumns = entry.getValue().get(i);
                String[] masterColumns = dataMaster.get(i);
                String masterKey = buildKey(masterColumns, masterFullColumnMaps);
                String thisKey = buildKey(thisColumns, columnMaps);
                if (!masterKey.equals(thisKey)) {
                    throw new RuntimeException(String.format("Key not match %s vs %s", masterKey, thisKey));
                }
                List<String> newColumns = IntStream
                    .range(0, thisColumns.length)
                    .filter(newColumnMaps::containsKey)
                    .mapToObj(index -> thisColumns[index])
                    .collect(Collectors.toList());

                mapListAppend(result, i, newColumns);
            }
        }

        return result.values().stream().map(s -> s.toArray(new String[0])).collect(Collectors.toList());
    }

    private void mapListAppend(Map<Integer, List<String>> result, int i, List<String> toAppend) {
        List<String> newList = new ArrayList<>();
        List<String> old = result.get(i);
        newList.addAll(old);
        newList.addAll(toAppend);
        result.put(i, newList);
    }

    private String buildKey(String[] thisColumns, Map<String, Integer> columnMaps) {
        return String.format(
            "%s-%s-%s-%s",
            thisColumns[columnMaps.get("PATIENT_ID")],
            thisColumns[columnMaps.get("SPEC_DATE")],
            thisColumns[columnMaps.get("SPEC_TYPE")],
            thisColumns[columnMaps.get("SPEC_NUM")]
        );
    }

    private boolean isMaster(Map<String, Integer> testColumnMaps, String[] columns) {
        boolean result = false;
        for (Map.Entry<String, Integer> entry : testColumnMaps.entrySet()) {
            String pattern = "(\\d+)";

            // Create a Pattern object
            Pattern r = Pattern.compile(pattern);

            // Now create matcher object.
            Matcher m = r.matcher(columns[entry.getValue()]);
            while (m.find()) {
                if (m.groupCount() > 0) {
                    result = true;
                    break;
                }
            }
            if (result) {
                break;
            }
        }
        return result;
    }
}
