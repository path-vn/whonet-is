package org.path.amr.services.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.path.amr.services.domain.Antibiotic;
import org.path.amr.services.domain.ExpertInterpretationRules;
import org.path.amr.services.repository.*;
import org.path.amr.services.service.dto.*;
import org.path.amr.services.service.mapper.*;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class InterpretationService {

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
        ExpertInterpretationRulesMapper expertInterpretationRulesMapper
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
    }

    public List<OrganismBreakPointDTO> getBreakpoints(String orgCode, String whonet5Test, String breakpointType) {
        return customRepository
            .getBreakPoints(orgCode, whonet5Test, breakpointType)
            .stream()
            .peek(
                ob -> {
                    breakpointRepository.findById(ob.getBreakPointID()).map(breakpointMapper::toDto).ifPresent(ob::setBreakpoint);
                    organismRepository.findById(ob.getOrganismID()).map(organismMapper::toDto).ifPresent(ob::setOrganism);
                }
            )
            .collect(Collectors.toList());
    }

    private double mode2(Double value, Double micS, Double micR, Short oper) {
        if (micS == null) {
            return value;
        }

        if (micR == null) {
            return value;
        }

        double Rrange = micR / 2;
        if (oper.equals(GREATER_THAN)) {
            double valueNextLevel = micS;
            do {
                valueNextLevel = valueNextLevel * 2;
            } while (!(valueNextLevel > value));
            value = valueNextLevel;
        }

        if (oper.equals(LESS_THAN)) {
            double valueNextLevel = micS;
            do {
                valueNextLevel = valueNextLevel * 2;
            } while (!(valueNextLevel >= value));
            value = valueNextLevel / 2;
        }

        if (value > Rrange && value < micR) {
            value = micR;
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
        boolean haveBreakPoint = false;
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
            List<OrganismIntrinsicResistanceAntibioticDTO> organismIntrinsicResistanceAntibioticDTOList = customRepository
                .getIntrinsicResistance(isolate.getOrgCode(), test.getWhonet5Code())
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
            if (organismIntrinsicResistanceAntibioticDTOList.size() > 0) {
                test.setIntrinsicResistance(organismIntrinsicResistanceAntibioticDTOList);
                test.addResult("R");
            } else {
                // A.2, 3 Apply breakpoints
                List<OrganismBreakPointDTO> organismBreakPointDTOList = getBreakpoints(
                    isolate.getOrgCode(),
                    test.getWhonet5Code(),
                    isolate.getBreakpointType()
                );

                if (organismBreakPointDTOList.size() > 0) {
                    haveBreakPoint = true;
                }
                organismBreakPointDTOList.forEach(
                    o -> {
                        InterpretationResult interpretationResult = interpretation(test, o);
                        isolate.setOrganism(o.getOrganism());
                        test.addResult(interpretationResult);
                    }
                );
            }
        }

        // B. Apply expert rules
        if (haveBreakPoint) {
            applyExpertRules(isolate);
        }
    }

    private void applyExpertRules(IsolateDTO isolate) {
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
            // no result => not set
            if (isolate.getTest().get(i).getResult() == null) {
                continue;
            }
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
                for (int j = 0; j < isolate.getTest().get(i).getResult().size(); j++) {
                    isolate.getTest().get(i).getResult().get(j).setResult("R");
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
        if (isolate.getDataFields().containsKey(r.getName())) {
            r.setResult(isolate.getDataFields().get(r.getName()).contains(r.getValue()));
            return;
        }

        // case 1
        r.setResult(false);
        for (int i = 0; i < isolate.getTest().size(); i++) {
            System.out.println(isolate.getTest().get(i).getAntibiotic() == null ? "null" : "?");
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
                for (int j = 0; j < isolate.getTest().get(i).getResult().size(); j++) {
                    if (isolate.getTest().get(i).getResult().get(j).getResult().equals(r.getValue())) {
                        r.setResult(true);
                        break;
                    }
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
        BreakpointDTO g = organismBreakPointDTO.getBreakpoint();
        String method = g.getTestMethod();
        short oper = testResult.getOper();
        boolean hasBreakpoint = notEmpty(g.getI()) || notEmpty(g.getR()) || notEmpty(g.getS());
        result.setSpecType(g.getSiteOfInfection());
        if (hasBreakpoint) {
            Double R = notEmpty(g.getR()) ? Double.valueOf(g.getR()) : null;
            Double S = notEmpty(g.getS()) ? Double.valueOf(g.getS()) : null;
            String[] Ix = notEmpty(g.getI()) ? g.getI().split("-") : null;
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
        return result;
    }

    public List<OrganismIntrinsicResistanceAntibioticDTO> intrinsicResistance(String orgCode, String abxCode) {
        return customRepository
            .getIntrinsicResistance(orgCode, abxCode)
            .stream()
            .map(
                oir -> {
                    antibioticRepository.findById(oir.getAntibioticId()).map(antibioticMapper::toDto).ifPresent(oir::setAntibiotic);
                    intrinsicResistanceRepository
                        .findById(oir.getIntrinsicResistanceId())
                        .map(intrinsicResistanceMapper::toDto)
                        .ifPresent(oir::setIntrinsicResistance);
                    return oir;
                }
            )
            .collect(Collectors.toList());
    }
}
