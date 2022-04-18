package org.path.amr.services.service.impl;

import java.util.List;
import org.junit.Test;
import org.path.amr.services.AmrInterpreationApp;
import org.path.amr.services.service.AntibioticQueryService;
import org.path.amr.services.service.AntibioticService;
import org.path.amr.services.service.criteria.AntibioticCriteria;
import org.path.amr.services.service.dto.AntibioticDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AmrInterpreationApp.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AntibioticServiceImplTest {

    @Autowired
    AntibioticQueryService antibioticQueryService;

    @Autowired
    AntibioticService antibioticService;

    @Test
    public void FindAllTest() {
        List<AntibioticDTO> antibioticDTOList = antibioticQueryService.findByCriteria(new AntibioticCriteria());
        assert antibioticDTOList.size() > 0;
    }
}
