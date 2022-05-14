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
import org.junit.jupiter.api.Test;
import org.path.amr.services.AmrInterpreationApp;
import org.path.amr.services.service.*;
import org.path.amr.services.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = AmrInterpreationApp.class)
@ActiveProfiles("testcontainers")
public class AntibioticServiceImplTest {

    private final Logger log = LoggerFactory.getLogger(AntibioticServiceImplTest.class);

    @Autowired
    AntibioticService antibioticService;

    @Autowired
    OrganismService organismService;

    @Autowired
    BreakpointService breakpointService;

    @Autowired
    IntrinsicResistanceService intrinsicResistanceService;

    @Autowired
    ExpertInterpretationRulesService expertInterpretationRulesService;

    @JsonIgnore
    public static Map<String, Method> getMethods(Class c) {
        Map<String, Method> result = new HashMap<>();
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            result.put(method.getName().replace("_", "").toUpperCase(Locale.ROOT), method);
        }
        return result;
    }

    @Test
    public void ImportAntibioticTest() throws IOException, InvocationTargetException, IllegalAccessException {
        URL url = AntibioticServiceImplTest.class.getClassLoader().getResource("Resources/Antibiotics.txt");

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

    @Test
    public void ImportOrganismTest() throws IOException, InvocationTargetException, IllegalAccessException {
        URL url = AntibioticServiceImplTest.class.getClassLoader().getResource("Resources/Organisms.txt");

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

    @Test
    public void ImportBreakpointsTest() throws IOException, InvocationTargetException, IllegalAccessException {
        URL url = AntibioticServiceImplTest.class.getClassLoader().getResource("Resources/Breakpoints.txt");
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

    @Test
    public void ImportExpertRulesTest() throws IOException, InvocationTargetException, IllegalAccessException {
        URL url = AntibioticServiceImplTest.class.getClassLoader().getResource("Resources/ExpertInterpretationRules.txt");

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

        expertInterpretationRulesService.flushAllAndSaveAll(newDTO);
    }

    @Test
    public void ImportIntrinsicResistanceTest() throws IOException, InvocationTargetException, IllegalAccessException {
        URL url = AntibioticServiceImplTest.class.getClassLoader().getResource("Resources/ExpectedResistancePhenotypes.txt");

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
