package org.path.amr.services.service;

import java.util.List;
import java.util.stream.Collectors;
import org.path.amr.services.repository.AntibioticRepository;
import org.path.amr.services.repository.BreakpointRepository;
import org.path.amr.services.repository.CustomRepository;
import org.path.amr.services.repository.OrganismRepository;
import org.path.amr.services.service.dto.BreakpointDTO;
import org.path.amr.services.service.dto.OrganismBreakPointDTO;
import org.path.amr.services.service.dto.TestResultDTO;
import org.path.amr.services.service.mapper.AntibioticMapper;
import org.path.amr.services.service.mapper.BreakpointMapper;
import org.path.amr.services.service.mapper.OrganismMapper;
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

    public InterpretationService(
        CustomRepository customRepository,
        OrganismRepository organismRepository,
        OrganismMapper organismMapper,
        BreakpointRepository breakpointRepository,
        BreakpointMapper breakpointMapper,
        AntibioticRepository antibioticRepository,
        AntibioticMapper antibioticMapper
    ) {
        this.customRepository = customRepository;
        this.organismRepository = organismRepository;
        this.breakpointRepository = breakpointRepository;
        this.antibioticRepository = antibioticRepository;
        this.organismMapper = organismMapper;
        this.breakpointMapper = breakpointMapper;
        this.antibioticMapper = antibioticMapper;
    }

    public List<OrganismBreakPointDTO> getBreakpoints(String orgCode, String whonet5Test) {
        return customRepository
            .getBreakPoints(orgCode, whonet5Test)
            .stream()
            .peek(
                ob -> {
                    breakpointRepository.findById(ob.getBreakPointID()).map(breakpointMapper::toDto).ifPresent(ob::setBreakpointDTO);
                    organismRepository.findById(ob.getOrganismID()).map(organismMapper::toDto).ifPresent(ob::setOrganismDTO);
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

    public TestResultDTO execute(String tmpStringValue, String orgCode, String whonet5Test, String specType) {
        TestResultDTO testResult = new TestResultDTO();

        tmpStringValue = tmpStringValue.replaceAll(PATTERN_0, "");
        String testValue = tmpStringValue.replaceAll(PATTERN_1, "");
        String oper = tmpStringValue.replaceAll(PATTERN_3, "");
        String result = tmpStringValue.replaceAll(PATTERN_4, "");
        //Sai tên vi khuẩn, không thể tìm được vi khuẩn hoặc gen tương ứng

        if (!testValue.isEmpty()) {
            testResult.setValue(Double.valueOf(testValue));
        }

        switch (oper) {
            case "<":
                testResult.setOper(LESS_THAN);
                break;
            case "<=":
                testResult.setOper(LESS_THAN_OR_EQUAL);
                break;
            case ">=":
                testResult.setOper(GREATER_THAN_OR_EQUAL);
                break;
            case ">":
                testResult.setOper(GREATER_THAN);
                break;
            default:
                testResult.setOper(EQUAL);
                break;
        }
        if (!result.isEmpty()) {
            testResult.addResult(result);
        }

        testResult.setOrgCode(orgCode);

        List<OrganismBreakPointDTO> organismBreakPointDTOList = getBreakpoints(orgCode, whonet5Test);
        organismBreakPointDTOList.forEach(
            o -> {
                testResult.addResult(interpretation(testResult, o));
            }
        );
        return testResult;
    }

    private boolean notEmpty(String s) {
        return s != null && !s.equals("");
    }

    public InterpretationResult interpretation(TestResultDTO testResult, OrganismBreakPointDTO organismBreakPointDTO) {
        InterpretationResult result = new InterpretationResult();

        Double valueToInterpretation = testResult.getValue();
        BreakpointDTO g = organismBreakPointDTO.getBreakpointDTO();
        String method = g.getTestMethod();
        short oper = testResult.getOper();
        boolean hasBreakpoint = notEmpty(g.getI()) || notEmpty(g.getR()) || notEmpty(g.getS());

        if (hasBreakpoint) {
            Double R = notEmpty(g.getR()) ? Double.valueOf(g.getR()) : null;
            Double S = notEmpty(g.getS()) ? Double.valueOf(g.getS()) : null;
            String[] Ix = g.getI().split("-");
            Double IMin = notEmpty(Ix[0]) ? Double.valueOf(Ix[0]) : null;
            Double IMax = null;
            if (Ix.length > 1) {
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
}
