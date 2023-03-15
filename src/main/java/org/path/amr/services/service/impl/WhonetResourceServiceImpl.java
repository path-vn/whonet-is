package org.path.amr.services.service.impl;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.*;
import liquibase.util.csv.opencsv.CSVParser;
import liquibase.util.csv.opencsv.CSVReader;
import org.path.amr.services.domain.WhonetResource;
import org.path.amr.services.repository.WhonetResourceRepository;
import org.path.amr.services.service.*;
import org.path.amr.services.service.dto.*;
import org.path.amr.services.service.mapper.WhonetResourceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WhonetResource}.
 */
@Service
@Transactional
public class WhonetResourceServiceImpl implements WhonetResourceService {

    private final Logger log = LoggerFactory.getLogger(WhonetResourceServiceImpl.class);

    private final WhonetResourceRepository whonetResourceRepository;

    private final WhonetResourceMapper whonetResourceMapper;

    private final AntibioticService antibioticService;

    private final OrganismService organismService;

    private final BreakpointService breakpointService;

    private final IntrinsicResistanceService intrinsicResistanceService;

    private final ExpertInterpretationRulesService expertInterpretationRulesService;

    public WhonetResourceServiceImpl(
        WhonetResourceRepository whonetResourceRepository,
        WhonetResourceMapper whonetResourceMapper,
        AntibioticService antibioticService,
        OrganismService organismService,
        BreakpointService breakpointService,
        IntrinsicResistanceService intrinsicResistanceService,
        ExpertInterpretationRulesService expertInterpretationRulesService
    ) {
        this.whonetResourceRepository = whonetResourceRepository;
        this.whonetResourceMapper = whonetResourceMapper;
        this.antibioticService = antibioticService;
        this.organismService = organismService;
        this.breakpointService = breakpointService;
        this.intrinsicResistanceService = intrinsicResistanceService;
        this.expertInterpretationRulesService = expertInterpretationRulesService;
    }

    @Override
    public WhonetResourceDTO save(WhonetResourceDTO whonetResourceDTO) {
        log.debug("Request to save WhonetResource : {}", whonetResourceDTO);
        WhonetResource whonetResource = whonetResourceMapper.toEntity(whonetResourceDTO);
        whonetResource = whonetResourceRepository.save(whonetResource);
        return whonetResourceMapper.toDto(whonetResource);
    }

    @Override
    public Optional<WhonetResourceDTO> partialUpdate(WhonetResourceDTO whonetResourceDTO) {
        log.debug("Request to partially update WhonetResource : {}", whonetResourceDTO);

        return whonetResourceRepository
            .findById(whonetResourceDTO.getId())
            .map(
                existingWhonetResource -> {
                    whonetResourceMapper.partialUpdate(existingWhonetResource, whonetResourceDTO);
                    return existingWhonetResource;
                }
            )
            .map(whonetResourceRepository::save)
            .map(whonetResourceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WhonetResourceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WhonetResources");
        return whonetResourceRepository.findAll(pageable).map(whonetResourceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WhonetResourceDTO> findOne(Long id) {
        log.debug("Request to get WhonetResource : {}", id);
        return whonetResourceRepository.findById(id).map(whonetResourceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WhonetResource : {}", id);
        whonetResourceRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void doImport(WhonetResourceDTO result) throws IOException, IllegalAccessException, InvocationTargetException {
        if (result.getAntibiotic() != null && !result.getAntibiotic().isEmpty()) {
            importAntibiotic(new URL("file://" + result.getAntibiotic()));
        }
        if (result.getOrganism() != null && !result.getOrganism().isEmpty()) {
            importOrganism(new URL("file://" + result.getOrganism()));
        }
        if (result.getIntrinsicResistance() != null && !result.getIntrinsicResistance().isEmpty()) {
            importIntrinsicResistance(new URL("file://" + result.getIntrinsicResistance()));
        }
        if (result.getBreakPoint() != null && !result.getBreakPoint().isEmpty()) {
            importBreakpoints(new URL("file://" + result.getBreakPoint()));
        }
        if (result.getExpertRule() != null && !result.getExpertRule().isEmpty()) {
            importExpertRules(new URL("file://" + result.getExpertRule()));
        }
    }

    @JsonIgnore
    public static Map<String, Method> getMethods(Class c) {
        Map<String, Method> result = new HashMap<>();
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            result.put(method.getName().replace("_", "").toUpperCase(Locale.ROOT), method);
        }
        return result;
    }

    public void importAntibiotic(URL url) throws IOException, InvocationTargetException, IllegalAccessException {
        assert url != null;
        CSVParser parser = new CSVParser('\t');
        File file = new File(url.getPath());
        InputStream fileInp = new FileInputStream(file);
        CSVReader reader = new CSVReader(new InputStreamReader(fileInp), 0, parser);
        List<String[]> lines = reader.readAll();
        reader.close();
        fileInp.close();
        assert lines.size() >= 1;
        String[] head = lines.get(0);
        // check method
        Map<String, Method> methodMap = getMethods(AntibioticDTO.class);
        Map<Integer, Method> setMethods = new HashMap<>();
        for (int i = 0; i < head.length; i++) {
            String field = head[i];
            if (field.equals("CLASS")) {
                field = "antiboticClass";
            }
            String setMethodName = "SET" + field.toUpperCase(Locale.ROOT).replace("_", "");
            log.info(setMethodName);
            assert methodMap.containsKey(setMethodName);
            setMethods.put(i, methodMap.get(setMethodName));
        }
        List<AntibioticDTO> newDTO = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            AntibioticDTO antibioticDTO = new AntibioticDTO();
            for (Map.Entry<Integer, Method> entry : setMethods.entrySet()) {
                entry.getValue().invoke(antibioticDTO, lines.get(i)[entry.getKey()]);
            }
            newDTO.add(antibioticDTO);
        }

        antibioticService.flushAllAndSaveAll(newDTO);
    }

    public void importOrganism(URL url) throws IOException, InvocationTargetException, IllegalAccessException {
        assert url != null;
        CSVParser parser = new CSVParser('\t');
        File file = new File(url.getPath());
        InputStream fileInp = new FileInputStream(file);
        CSVReader reader = new CSVReader(new InputStreamReader(fileInp), 0, parser);
        List<String[]> lines = reader.readAll();
        reader.close();
        fileInp.close();
        assert lines.size() >= 1;
        String[] head = lines.get(0);
        // check method
        Map<String, Method> methodMap = getMethods(OrganismDTO.class);
        Map<Integer, Method> setMethods = new HashMap<>();
        for (int i = 0; i < head.length; i++) {
            String field = head[i];
            String setMethodName = "SET" + field.toUpperCase(Locale.ROOT).replace("_", "");
            if (setMethodName.equals("SETREPLACEDBY")) {
                continue;
            }
            if (setMethodName.equals("SETCLASS")) {
                setMethodName = "SETORGANISMCLASS";
            }
            log.info(setMethodName);
            assert methodMap.containsKey(setMethodName);
            setMethods.put(i, methodMap.get(setMethodName));
        }
        List<OrganismDTO> newDTO = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            OrganismDTO organismDTO = new OrganismDTO();
            for (Map.Entry<Integer, Method> entry : setMethods.entrySet()) {
                entry.getValue().invoke(organismDTO, lines.get(i)[entry.getKey()]);
            }
            newDTO.add(organismDTO);
        }

        organismService.flushAllAndSaveAll(newDTO);
    }

    public void importBreakpoints(URL url) throws IOException, InvocationTargetException, IllegalAccessException {
        String stringDate = ZonedDateTime.now().format(ISO_LOCAL_DATE);
        assert url != null;
        CSVParser parser = new CSVParser('\t');
        File file = new File(url.getPath());
        InputStream fileInp = new FileInputStream(file);
        CSVReader reader = new CSVReader(new InputStreamReader(fileInp), 0, parser);
        List<String[]> lines = reader.readAll();
        reader.close();
        fileInp.close();
        assert lines.size() >= 1;
        String[] head = lines.get(0);
        // check method
        Map<String, Method> methodMap = getMethods(BreakpointDTO.class);
        Map<Integer, Method> setMethods = new HashMap<>();
        for (int i = 0; i < head.length; i++) {
            String field = head[i];
            String setMethodName = "SET" + field.toUpperCase(Locale.ROOT).replace("_", "");
            if (setMethodName.equals("SETCOMMENTS")) {
                continue;
            }
            if (setMethodName.equals("SETDATEMODIFIED")) {
                continue;
            }
            if (setMethodName.equals("SETDATEENTERED")) {
                continue;
            }
            if (setMethodName.equals("SETECVECOFF")) {
                continue;
            }
            if (setMethodName.equals("SETCLASS")) {
                setMethodName = "SETORGANISMCLASS";
            }
            log.info(setMethodName);
            assert methodMap.containsKey(setMethodName);
            setMethods.put(i, methodMap.get(setMethodName));
        }
        List<BreakpointDTO> newDTO = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            BreakpointDTO breakpointDTO = new BreakpointDTO();
            for (Map.Entry<Integer, Method> entry : setMethods.entrySet()) {
                String className = entry.getValue().getParameterTypes()[0].getName();
                switch (className) {
                    case "java.lang.String":
                        entry.getValue().invoke(breakpointDTO, lines.get(i)[entry.getKey()]);
                        break;
                    case "java.lang.Integer":
                        entry.getValue().invoke(breakpointDTO, Integer.valueOf(lines.get(i)[entry.getKey()]));
                        break;
                    default:
                        throw new RuntimeException("Not support " + className);
                }
            }
            if (breakpointDTO.getDateEntered() == null || breakpointDTO.getDateEntered().equals("")) {
                breakpointDTO.setDateEntered(stringDate);
            }
            newDTO.add(breakpointDTO);
        }

        breakpointService.flushAllAndSaveAll(newDTO);
    }

    public void importExpertRules(URL url) throws IOException, InvocationTargetException, IllegalAccessException {
        assert url != null;
        CSVParser parser = new CSVParser('\t');
        File file = new File(url.getPath());
        InputStream fileInp = new FileInputStream(file);
        CSVReader reader = new CSVReader(new InputStreamReader(fileInp), 0, parser);
        List<String[]> lines = reader.readAll();
        reader.close();
        fileInp.close();
        assert lines.size() >= 1;
        String[] head = lines.get(0);
        // check method
        Map<String, Method> methodMap = getMethods(ExpertInterpretationRulesDTO.class);
        Map<Integer, Method> setMethods = new HashMap<>();
        for (int i = 0; i < head.length; i++) {
            String field = head[i];
            String setMethodName = "SET" + field.toUpperCase(Locale.ROOT).replace("_", "");
            log.info(setMethodName);
            assert methodMap.containsKey(setMethodName);
            setMethods.put(i, methodMap.get(setMethodName));
        }
        List<ExpertInterpretationRulesDTO> newDTO = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            ExpertInterpretationRulesDTO expertInterpretationRulesDTO = new ExpertInterpretationRulesDTO();
            for (Map.Entry<Integer, Method> entry : setMethods.entrySet()) {
                String className = entry.getValue().getParameterTypes()[0].getName();
                switch (className) {
                    case "java.lang.String":
                        entry.getValue().invoke(expertInterpretationRulesDTO, lines.get(i)[entry.getKey()]);
                        break;
                    case "java.lang.Integer":
                        entry.getValue().invoke(expertInterpretationRulesDTO, Integer.valueOf(lines.get(i)[entry.getKey()]));
                        break;
                    default:
                        throw new RuntimeException("Not support " + className);
                }
            }
            newDTO.add(expertInterpretationRulesDTO);
        }

        ExpertInterpretationRulesDTO custom = new ExpertInterpretationRulesDTO();
        custom.setRuleCode("MRSi");
        custom.setDescription(
            "PATH - For example, MRSA should be considered resistant to most beta-lactams. So if you have a strain that is “OXA=R”, then WHONET will automatically change “FOX” to “R” as well.\t"
        );
        custom.setOrganismCode("STA");
        custom.setOrganismCodeType("GENUS_CODE");
        custom.setRuleCriteria("OXA=R");
        custom.setAffectedAntibiotics("FOX");
        custom.setResult("R");
        newDTO.add(custom);

        expertInterpretationRulesService.flushAllAndSaveAll(newDTO);
    }

    public void importIntrinsicResistance(URL url) throws IOException, InvocationTargetException, IllegalAccessException {
        assert url != null;
        CSVParser parser = new CSVParser('\t');
        File file = new File(url.getPath());
        InputStream fileInp = new FileInputStream(file);
        CSVReader reader = new CSVReader(new InputStreamReader(fileInp), 0, parser);
        List<String[]> lines = reader.readAll();
        reader.close();
        fileInp.close();
        assert lines.size() >= 1;
        String[] head = lines.get(0);
        // check method
        Map<String, Method> methodMap = getMethods(IntrinsicResistanceDTO.class);
        Map<Integer, Method> setMethods = new HashMap<>();
        for (int i = 0; i < head.length; i++) {
            String field = head[i];
            String setMethodName = "SET" + field.toUpperCase(Locale.ROOT).replace("_", "");
            log.info(setMethodName);
            assert methodMap.containsKey(setMethodName);
            setMethods.put(i, methodMap.get(setMethodName));
        }
        List<IntrinsicResistanceDTO> newDTO = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            IntrinsicResistanceDTO intrinsicResistanceDTO = new IntrinsicResistanceDTO();
            for (Map.Entry<Integer, Method> entry : setMethods.entrySet()) {
                String className = entry.getValue().getParameterTypes()[0].getName();
                switch (className) {
                    case "java.lang.String":
                        entry.getValue().invoke(intrinsicResistanceDTO, lines.get(i)[entry.getKey()]);
                        break;
                    case "java.lang.Integer":
                        entry.getValue().invoke(intrinsicResistanceDTO, Integer.valueOf(lines.get(i)[entry.getKey()]));
                        break;
                    default:
                        throw new RuntimeException("Not support " + className);
                }
            }
            newDTO.add(intrinsicResistanceDTO);
        }

        intrinsicResistanceService.flushAllAndSaveAll(newDTO);
    }
}
