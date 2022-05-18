package org.path.amr.services.repository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Cacheable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.path.amr.services.service.dto.OrganismBreakPointDTO;
import org.path.amr.services.service.dto.OrganismIntrinsicResistanceAntibioticDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Cacheable
public class CustomRepository {

    private final Logger log = LoggerFactory.getLogger(CustomRepository.class);

    @PersistenceContext
    private EntityManager em;

    private final DateTimeFormatter mysqlDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    protected Session getCurrentSession() {
        return em.unwrap(Session.class);
    }

    public List<String> findBreakpointGroupByField(String field) {
        String sql = String.format("select %s , 'ab' as c from breakpoints group by %s order by %s desc", field, field, field);
        NativeQuery qry = getCurrentSession().createNativeQuery(sql);
        List<String> result = new ArrayList<>();
        List<Object[]> rows = qry.getResultList();
        for (int i = 0; i < rows.size(); i++) {
            result.add(rows.get(i)[0].toString());
        }
        return result;
    }

    public List<OrganismIntrinsicResistanceAntibioticDTO> getIntrinsicResistance(
        String orgCode,
        String abxCode,
        List<String> guidelines,
        String organismCodeTypeOrder
    ) {
        abxCode = abxCode.split("_")[0];
        //        "SELECT i.id as iid, a.id as aid " +
        //
        //        "    AND o.WHONET_ORG_CODE = :orgCode " +
        //            " AND o.TAXONOMIC_STATUS = 'C' " +
        //            " AND i.ABX_CODE = :abxCode " +
        //            " AND i.GUIDELINE in (:guidelines) " +

        String sql =
            //            "-- Find the expected resistance phenotypes (intrinsic resistance) rules for the organism specified in the WHERE clause.  " +
            //                "-- The organism might only match at a higher level. For example, a rule applying to all Enterobacterales should be returned  " +
            //                "-- when the isolate's organism is a Salmonella sp.  " +
            "SELECT i.id as iid, a.id as aid  " +
            "FROM organisms o  " +
            " INNER JOIN (  " +
            "  SELECT *,  " +
            //                "   -- This would need to be expanded if we needed to support more than two exceptions for a single rule.  " +
            //                "   -- In C# it will be implemented to handle an arbitrary number of items.  " +
            //                "   -- Tried a CTE here, but couldn't get EXCEPTION_ORGANISM_CODE to feed into the base case of the recurrsion.  " +
            "   substr(EXCEPTION_ORGANISM_CODE, 1, instr(EXCEPTION_ORGANISM_CODE, ',') - 1)  AS FirstException,  " +
            "   substr(EXCEPTION_ORGANISM_CODE, instr(EXCEPTION_ORGANISM_CODE, ',') + 1) AS SecondException  " +
            "  FROM intrinsic_resistance  " +
            " ) i  " +
            "  ON (  " +
            "   o.SEROVAR_GROUP IS NOT NULL  " +
            "   AND i.ORGANISM_CODE_TYPE = 'SEROVAR_GROUP'  " +
            "   AND o.SEROVAR_GROUP = i.ORGANISM_CODE  " +
            "  ) OR (  " +
            "   i.ORGANISM_CODE_TYPE = 'WHONET_ORG_CODE'  " +
            "   AND o.WHONET_ORG_CODE = i.ORGANISM_CODE  " +
            "  ) OR (  " +
            "   o.SPECIES_GROUP IS NOT NULL  " +
            "   AND i.ORGANISM_CODE_TYPE = 'SPECIES_GROUP'  " +
            "   AND o.SPECIES_GROUP = i.ORGANISM_CODE  " +
            "  ) OR (  " +
            "   o.GENUS_CODE IS NOT NULL  " +
            "   AND i.ORGANISM_CODE_TYPE = 'GENUS_CODE'  " +
            "   AND o.GENUS_CODE = i.ORGANISM_CODE  " +
            "  ) OR (  " +
            "   o.GENUS_GROUP IS NOT NULL  " +
            "   AND i.ORGANISM_CODE_TYPE = 'GENUS_GROUP'  " +
            "   AND o.GENUS_GROUP = i.ORGANISM_CODE  " +
            "  ) OR (  " +
            "   o.FAMILY_CODE IS NOT NULL  " +
            "   AND i.ORGANISM_CODE_TYPE = 'FAMILY_CODE'  " +
            "   AND o.FAMILY_CODE = i.ORGANISM_CODE  " +
            "  ) OR (  " +
            "   o.SUBKINGDOM_CODE IS NOT NULL  " +
            "   AND i.ORGANISM_CODE_TYPE = 'SUBKINGDOM_CODE'  " +
            "   AND o.SUBKINGDOM_CODE = i.ORGANISM_CODE  " +
            "  ) OR (  " +
            "   o.ANAEROBE = 'X'  " +
            "   AND i.ORGANISM_CODE_TYPE = 'ANAEROBE+SUBKINGDOM_CODE'  " +
            "   AND ((  " +
            "     o.SUBKINGDOM_CODE = '+'  " +
            "     AND i.ORGANISM_CODE = 'AN+'  " +
            "    ) OR (  " +
            "     o.SUBKINGDOM_CODE = '-'  " +
            "     AND i.ORGANISM_CODE = 'AN-'  " +
            "   ))  " +
            "  ) OR (  " +
            "   o.ANAEROBE = 'X'  " +
            "   AND i.ORGANISM_CODE_TYPE = 'ANAEROBE'  " +
            "   AND i.ORGANISM_CODE = 'ANA'  " +
            "  )  " +
            " INNER JOIN antibiotics a  " +
            "  ON (  " +
            "    i.ABX_CODE_TYPE = 'ATC_CODE'  " +
            "    AND substr(a.ATC_CODE, 1, length(i.ABX_CODE)) = i.ABX_CODE  " +
            "  ) OR (  " +
            "    i.ABX_CODE_TYPE = 'WHONET_ABX_CODE'  " +
            "    AND i.ABX_CODE = a.WHONET_ABX_CODE  " +
            "  ) AND (  " +
            "   CASE  " +
            "   WHEN i.GUIDELINE = 'CLSI' AND a.CLSI = 'X' THEN 1  " +
            "   WHEN i.GUIDELINE = 'EUCAST' AND a.EUCAST = 'X' THEN 1  " +
            "   WHEN i.GUIDELINE = 'SFM' AND a.SFM = 'X' THEN 1  " +
            "   WHEN i.GUIDELINE = 'SRGA' AND a.SRGA = 'X' THEN 1  " +
            "   WHEN i.GUIDELINE = 'BSAC' AND a.BSAC = 'X' THEN 1  " +
            "   WHEN i.GUIDELINE = 'DIN' AND a.DIN = 'X' THEN 1  " +
            "   WHEN i.GUIDELINE = 'NEO' AND a.NEO = 'X' THEN 1  " +
            "   WHEN i.GUIDELINE = 'AFA' AND a.AFA = 'X' THEN 1  " +
            "   ELSE 0  " +
            "   END  " +
            "  ) = 1  " +
            "WHERE o.WHONET_ORG_CODE = :orgCode  " +
            " AND o.TAXONOMIC_STATUS = 'C'  " +
            " AND (i.ABX_CODE = :abxCode  or a.WHONET_ABX_CODE = :abxCode) " +
            " AND i.GUIDELINE in (:guidelines) " +
            " AND (  " +
            //                "   -- Organism exceptions to the intrinsic rule, if applicable.  " +
            "   coalesce(i.EXCEPTION_ORGANISM_CODE, '') = ''  " +
            "   OR NOT (  " +
            "    (  " +
            "     o.SEROVAR_GROUP IS NOT NULL  " +
            "     AND i.EXCEPTION_ORGANISM_CODE_TYPE = 'SEROVAR_GROUP'  " +
            "     AND (  " +
            "      o.SEROVAR_GROUP = i.FirstException  " +
            "      OR o.SEROVAR_GROUP = i.SecondException  " +
            "     )  " +
            "    ) OR (  " +
            "     i.EXCEPTION_ORGANISM_CODE_TYPE = 'WHONET_ORG_CODE'  " +
            "     AND (  " +
            "      o.WHONET_ORG_CODE = i.FirstException  " +
            "      OR o.WHONET_ORG_CODE = i.SecondException  " +
            "     )  " +
            "    ) OR (  " +
            "     o.SPECIES_GROUP IS NOT NULL  " +
            "     AND i.EXCEPTION_ORGANISM_CODE_TYPE = 'SPECIES_GROUP'  " +
            "     AND (  " +
            "      o.SPECIES_GROUP = i.FirstException  " +
            "      OR o.SPECIES_GROUP = i.SecondException  " +
            "     )  " +
            "    ) OR (  " +
            "     o.GENUS_CODE IS NOT NULL  " +
            "     AND i.EXCEPTION_ORGANISM_CODE_TYPE = 'GENUS_CODE'  " +
            "     AND (  " +
            "      o.GENUS_CODE = i.FirstException  " +
            "      OR o.GENUS_CODE = i.SecondException  " +
            "     )  " +
            "    ) OR (  " +
            "     o.GENUS_GROUP IS NOT NULL  " +
            "     AND i.EXCEPTION_ORGANISM_CODE_TYPE = 'GENUS_GROUP'  " +
            "     AND (  " +
            "      o.GENUS_GROUP = i.FirstException  " +
            "      OR o.GENUS_GROUP = i.SecondException  " +
            "     )  " +
            "    ) OR (  " +
            "     o.FAMILY_CODE IS NOT NULL  " +
            "     AND i.EXCEPTION_ORGANISM_CODE_TYPE = 'FAMILY_CODE'  " +
            "     AND (  " +
            "      o.FAMILY_CODE = i.FirstException  " +
            "      OR o.FAMILY_CODE = i.SecondException  " +
            "     )  " +
            "    ) OR (  " +
            "     o.SUBKINGDOM_CODE IS NOT NULL  " +
            "     AND i.EXCEPTION_ORGANISM_CODE_TYPE = 'SUBKINGDOM_CODE'  " +
            "     AND (  " +
            "      o.SUBKINGDOM_CODE = i.FirstException  " +
            "      OR o.SUBKINGDOM_CODE = i.SecondException  " +
            "     )  " +
            "    ) OR (  " +
            "     o.ANAEROBE = 'X'  " +
            "     AND i.EXCEPTION_ORGANISM_CODE_TYPE = 'ANAEROBE+SUBKINGDOM_CODE'  " +
            "     AND ((  " +
            "       o.SUBKINGDOM_CODE = '+'  " +
            "       AND (  " +
            "        i.FirstException = 'AN+'  " +
            "        OR i.SecondException = 'AN+'  " +
            "       )  " +
            "      ) OR (  " +
            "       o.SUBKINGDOM_CODE = '-'  " +
            "       AND (  " +
            "        i.FirstException = 'AN-'  " +
            "        OR i.SecondException = 'AN-'  " +
            "       )  " +
            "     ))  " +
            "    ) OR (  " +
            "     o.ANAEROBE = 'X'  " +
            "     AND i.EXCEPTION_ORGANISM_CODE_TYPE = 'ANAEROBE'  " +
            "     AND (  " +
            "      i.FirstException = 'ANA'  " +
            "      OR i.SecondException = 'ANA'  " +
            "     )  " +
            "    )  " +
            "   )  " +
            "  )  " +
            " AND (  " +
            //                "  -- Check for antibiotic exceptions to this rule.  " +
            //                "  -- Simpler than organism exclusions because we don't have to worry about the code type.  " +
            "  coalesce(i.ANTIBIOTIC_EXCEPTIONS, '') = ''  " +
            "  OR instr(i.ANTIBIOTIC_EXCEPTIONS, a.WHONET_ABX_CODE) = 0  " +
            " )  " +
            "ORDER BY i.GUIDELINE ASC,  " +
            " (  " +
            buildSQL(organismCodeTypeOrder, "i") +
            " ) ASC,  " +
            " (  " +
            "  CASE i.ABX_CODE_TYPE  " +
            "  WHEN 'WHONET_ABX_CODE' THEN 1  " +
            "  WHEN 'ATC_CODE' THEN 2  " +
            "  ELSE 3  " +
            "  END  " +
            " ) ASC,  " +
            " i.ABX_CODE ASC,  " +
            " a.WHONET_ABX_CODE ASC  ";

        NativeQuery qry = getCurrentSession().createNativeQuery(sql);
        qry.setParameter("abxCode", abxCode);
        qry.setParameter("orgCode", orgCode);
        qry.setParameter("guidelines", guidelines);

        List<OrganismIntrinsicResistanceAntibioticDTO> result = new ArrayList<>();
        List<Object[]> rows = qry.getResultList();
        for (int i = 0; i < rows.size(); i++) {
            OrganismIntrinsicResistanceAntibioticDTO organismIntrinsicResistanceAntibioticDTO = new OrganismIntrinsicResistanceAntibioticDTO();
            organismIntrinsicResistanceAntibioticDTO.setIntrinsicResistanceId(Long.valueOf(rows.get(i)[0].toString()));
            organismIntrinsicResistanceAntibioticDTO.setAntibioticId(Long.valueOf(rows.get(i)[1].toString()));
            organismIntrinsicResistanceAntibioticDTO.setOrgCode(orgCode);
            organismIntrinsicResistanceAntibioticDTO.setAbxCode(abxCode);
            result.add(organismIntrinsicResistanceAntibioticDTO);
        }
        return result;
    }

    private String buildSQL(String organismCodeTypeOrder, String table) {
        if (organismCodeTypeOrder == null || organismCodeTypeOrder.isEmpty()) {
            organismCodeTypeOrder =
                "SEROVAR_GROUP,WHONET_ORG_CODE,SPECIES_GROUP,GENUS_CODE,GENUS_GROUP,FAMILY_CODE,SUBKINGDOM_CODE,ANAEROBE+SUBKINGDOM_CODE,ANAEROBE";
        }
        StringBuilder rs = new StringBuilder(String.format("CASE %s.ORGANISM_CODE_TYPE  ", table));
        String[] order = organismCodeTypeOrder.split(",");
        int i = 0;
        for (i = 0; i < order.length; i++) {
            rs.append(String.format(" WHEN '%s' THEN %d ", order[i], i + 1));
        }
        rs.append(String.format("ELSE %d END", i + 1));
        return rs.toString();
    }

    public List<OrganismBreakPointDTO> getBreakPoints(
        String orgCode,
        String whonetTest,
        String breakpointType,
        String status,
        int year,
        List<String> guidelines,
        String organismCodeType
    ) {
        String sql =
            " SELECT o.ID as organismID, b.ID breakpointID " +
            " FROM organisms o  " +
            " INNER JOIN breakpoints b  " +
            "  ON b.ORGANISM_CODE_TYPE <> 'ALL' AND (  " +
            "   o.SEROVAR_GROUP IS NOT NULL  " +
            "   AND b.ORGANISM_CODE_TYPE = 'SEROVAR_GROUP'  " +
            "   AND o.SEROVAR_GROUP = b.ORGANISM_CODE  " +
            "  ) OR (  " +
            "   b.ORGANISM_CODE_TYPE = 'WHONET_ORG_CODE'  " +
            "   AND o.WHONET_ORG_CODE = b.ORGANISM_CODE  " +
            "  ) OR (  " +
            "   o.SPECIES_GROUP IS NOT NULL  " +
            "   AND b.ORGANISM_CODE_TYPE = 'SPECIES_GROUP'  " +
            "   AND o.SPECIES_GROUP = b.ORGANISM_CODE  " +
            "  ) OR (  " +
            "   o.GENUS_CODE IS NOT NULL  " +
            "   AND b.ORGANISM_CODE_TYPE = 'GENUS_CODE'  " +
            "   AND o.GENUS_CODE = b.ORGANISM_CODE  " +
            "  ) OR (  " +
            "   o.GENUS_GROUP IS NOT NULL  " +
            "   AND b.ORGANISM_CODE_TYPE = 'GENUS_GROUP'  " +
            "   AND o.GENUS_GROUP = b.ORGANISM_CODE  " +
            "  ) OR (  " +
            "   o.FAMILY_CODE IS NOT NULL  " +
            "   AND b.ORGANISM_CODE_TYPE = 'FAMILY_CODE'  " +
            "   AND o.FAMILY_CODE = b.ORGANISM_CODE  " +
            "  ) OR (  " +
            "   o.ANAEROBE = 'X'  " +
            "   AND b.ORGANISM_CODE_TYPE = 'ANAEROBE+SUBKINGDOM_CODE'  " +
            "   AND ((  " +
            "     o.SUBKINGDOM_CODE = '+'  " +
            "     AND b.ORGANISM_CODE = 'AN+'  " +
            "    ) OR (  " +
            "     o.SUBKINGDOM_CODE = '-'  " +
            "     AND b.ORGANISM_CODE = 'AN-'  " +
            "   ))  " +
            "  ) OR (  " +
            "   o.ANAEROBE = 'X'  " +
            "   AND b.ORGANISM_CODE_TYPE = 'ANAEROBE'  " +
            "   AND b.ORGANISM_CODE = 'ANA'  " +
            "  )  " +
            "WHERE o.WHONET_ORG_CODE = :orgCode " +
            " AND o.TAXONOMIC_STATUS = :status " +
            " AND b.GUIDELINES in (:guideline)" +
            " AND b.YEAR = :year  " +
            " AND b.BREAKPOINT_TYPE = :breakpointType  " +
            " AND b.BREAKPOINT_TYPE = :breakpointType  " +
            " AND b.WHONET_TEST = :whonetTest " +
            "ORDER BY o.WHONET_ORG_CODE ASC,  " +
            " b.GUIDELINES ASC,  " +
            " b.YEAR ASC,  " +
            " b.TEST_METHOD ASC,  " +
            " (  " +
            "  CASE b.BREAKPOINT_TYPE  " +
            "  WHEN 'Human' THEN 1  " +
            "  WHEN 'Animal' THEN 2  " +
            "  WHEN 'ECOFF' THEN 3  " +
            "  END  " +
            " ) ASC,  " +
            " b.HOST ASC, (  " +
            // "SEROVAR_GROUP,WHONET_ORG_CODE,SPECIES_GROUP,GENUS_CODE,GENUS_GROUP,FAMILY_CODE,SUBKINGDOM_CODE,ANAEROBE+SUBKINGDOM_CODE,ANAEROBE"
            buildSQL(organismCodeType, "b") +
            " ) ASC,  " +
            " b.WHONET_TEST ASC,  " +
            " b.SITE_OF_INFECTION ASC";

        NativeQuery qry = getCurrentSession().createNativeQuery(sql);
        qry.setParameter("year", year);
        qry.setParameter("orgCode", orgCode);
        qry.setParameter("status", status);
        qry.setParameter("whonetTest", whonetTest);
        qry.setParameter("breakpointType", breakpointType);
        qry.setParameter("guideline", guidelines);
        List<OrganismBreakPointDTO> result = new ArrayList<>();
        List<Object[]> rows = qry.getResultList();
        for (int i = 0; i < rows.size(); i++) {
            OrganismBreakPointDTO breakPoint = new OrganismBreakPointDTO();
            breakPoint.setOrganismID(Long.valueOf(rows.get(i)[0].toString()));
            breakPoint.setBreakPointID(Long.valueOf(rows.get(i)[1].toString()));

            result.add(breakPoint);
        }
        return result;
    }

    public List<String> findAntibioticGroupByField(String field) {
        String sql = String.format("select %s , 'ab' as c from antibiotics group by %s order by %s desc", field, field, field);
        NativeQuery qry = getCurrentSession().createNativeQuery(sql);
        List<String> result = new ArrayList<>();
        List<Object[]> rows = qry.getResultList();
        for (int i = 0; i < rows.size(); i++) {
            result.add(rows.get(i)[0].toString());
        }
        return result;
    }

    public List<String> findOrganismGroupByField(String field) {
        String sql = String.format("select %s , 'ab' as c from organisms group by %s order by %s desc", field, field, field);
        NativeQuery qry = getCurrentSession().createNativeQuery(sql);
        List<String> result = new ArrayList<>();
        List<Object[]> rows = qry.getResultList();
        for (int i = 0; i < rows.size(); i++) {
            result.add(rows.get(i)[0].toString());
        }
        return result;
    }

    public List<String> findIntrinsicResistanceGroupByField(String field) {
        String sql = String.format("select %s , 'ab' as c from intrinsic_resistance group by %s order by %s desc", field, field, field);
        NativeQuery qry = getCurrentSession().createNativeQuery(sql);
        List<String> result = new ArrayList<>();
        List<Object[]> rows = qry.getResultList();
        for (int i = 0; i < rows.size(); i++) {
            result.add(rows.get(i)[0].toString());
        }
        return result;
    }
}
