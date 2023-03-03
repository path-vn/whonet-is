package org.path.amr.services.web.rest;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import org.path.amr.services.service.mapper.IsolateRequestMapper;
import org.springframework.beans.factory.annotation.Autowired;

class WhonetResourceTest {

    @Autowired
    IsolateRequestMapper isolateRequestMapper;

    String testData =
        "ROW_IDX|COUNTRY_A|LABORATORY|PATIENT_ID|FIRST_NAME|LAST_NAME|SEX|AGE|DATE_BIRTH|WARD|INSTITUT|DEPARTMENT|WARD_TYPE|PAT_TYPE|SPEC_NUM|SPEC_DATE|SPEC_TYPE|SPEC_CODE|SPEC_REAS|DATE_DATA|ORGANISM|ORG_TYPE|BETA_LACT|MECA_PCR|INDUC_CLI|COMMENT|ORIGIN|ESBL|PEN_ND10|ERY_ND15|TCY_ND30|CHL_ND30|NIT_ND300|SSS_ND200|TOB_ND10|CLI_ND2|CEP_ND30|AMP_ND10|AMK_ND30|OXA_ND1|GEN_ND10|CRB_ND100|VAN_ND30|SXT_ND1_2|FOX_ND30|OFX_ND5|CTX_ND30|PIP_ND100|CAZ_ND30|IPM_ND10|CZX_ND30|AMC_ND20|TCC_ND75|CXM_ND30|MEZ_ND75|CIP_ND5|NOR_ND10|ATM_ND30|MAN_ND30|TIC_ND75|RIF_ND5|DOX_ND30|MNO_ND30|TEC_ND30|NOV_ND5|PEN_NM|CTX_NM|VAN_NM|PEN_NE|CTX_NE|VAN_NE" +
        "VNM,CRH,,XUAN VINHHU?NHm43,***,***,m,1977-01-01 00:00:00,43,adu,6B1,CRH,6B1,in,,300120-1197883,2020-01-30 00:00:00,fl,DICH,18,,,eco,Escherichia coli,-,,,,,-,,,,,2021-04-09 00:00:00,,,,,***,,21.0,,>16.0,R,24.0,,<=1.0,S,<=0.25,S,<=1.0,S,32.0,,4.0,S,4.0,,30.0,,<=0.25,S,<=0.5,S,<=1.0,S,<=0.25,S,<=0.25,S,0.5,,>64.0,R,16.0,S,<=0.5,,>160.0,R,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,";
    WhonetResource r = new WhonetResource(null, null, isolateRequestMapper, null);

    public String getWhonetFileSeparator(String inp) {
        Matcher m = Pattern.compile("COUNTRY_A(?<sep>.*)LABORATORY").matcher(inp.toUpperCase(Locale.ROOT));
        String rs = "";
        while (m.find()) {
            rs = m.group(1);
        }
        return rs;
    }

    @Test
    void getInterpretation() {
        System.out.println(getWhonetFileSeparator(testData));
    }

    @Test
    void testGetInterpretation() {}
}
