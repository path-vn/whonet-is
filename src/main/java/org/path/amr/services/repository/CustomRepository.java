package org.path.amr.services.repository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.path.amr.services.service.dto.OrganismBreakPointDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CustomRepository {

    private final Logger log = LoggerFactory.getLogger(CustomRepository.class);

    @PersistenceContext
    private EntityManager em;

    private final DateTimeFormatter mysqlDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    protected Session getCurrentSession() {
        return em.unwrap(Session.class);
    }

    public List<OrganismBreakPointDTO> getBreakPoints() {
        return getBreakPoints("spn", "CRO_NM");
    }

    public List<OrganismBreakPointDTO> getBreakPoints(String orgCode, String whonetTest) {
        return this.getBreakPoints(orgCode, whonetTest, "C", 2021);
    }

    public List<OrganismBreakPointDTO> getBreakPoints(String orgCode, String whonetTest, String status, int year) {
        String sql =
            " SELECT o.ID as organismID, b.ID breakpointID " +
            " FROM organisms o " +
            " INNER JOIN breakpoints b " +
            "  ON b.ORGANISM_CODE_TYPE <> 'ALL' AND ( " +
            "   o.SEROVAR_GROUP IS NOT NULL " +
            "   AND b.ORGANISM_CODE_TYPE = 'SEROVAR_GROUP' " +
            "   AND o.SEROVAR_GROUP = b.ORGANISM_CODE " +
            "  ) OR ( " +
            "   b.ORGANISM_CODE_TYPE = 'WHONET_ORG_CODE' " +
            "   AND o.WHONET_ORG_CODE = b.ORGANISM_CODE " +
            "  ) OR ( " +
            "   o.SPECIES_GROUP IS NOT NULL " +
            "   AND b.ORGANISM_CODE_TYPE = 'SPECIES_GROUP' " +
            "   AND o.SPECIES_GROUP = b.ORGANISM_CODE " +
            "  ) OR ( " +
            "   o.GENUS_CODE IS NOT NULL " +
            "   AND b.ORGANISM_CODE_TYPE = 'GENUS_CODE' " +
            "   AND o.GENUS_CODE = b.ORGANISM_CODE " +
            "  ) OR ( " +
            "   o.GENUS_GROUP IS NOT NULL " +
            "   AND b.ORGANISM_CODE_TYPE = 'GENUS_GROUP' " +
            "   AND o.GENUS_GROUP = b.ORGANISM_CODE " +
            "  ) OR ( " +
            "   o.FAMILY_CODE IS NOT NULL " +
            "   AND b.ORGANISM_CODE_TYPE = 'FAMILY_CODE' " +
            "   AND o.FAMILY_CODE = b.ORGANISM_CODE " +
            "  ) OR ( " +
            "   o.ANAEROBE = 'X' " +
            "   AND b.ORGANISM_CODE_TYPE = 'ANAEROBE+SUBKINGDOM_CODE' " +
            "   AND (( " +
            "     o.SUBKINGDOM_CODE = '+' " +
            "     AND b.ORGANISM_CODE = 'AN+' " +
            "    ) OR ( " +
            "     o.SUBKINGDOM_CODE = '-' " +
            "     AND b.ORGANISM_CODE = 'AN-' " +
            "   ))    " +
            "  ) OR ( " +
            "   o.ANAEROBE = 'X' " +
            "   AND b.ORGANISM_CODE_TYPE = 'ANAEROBE' " +
            "   AND b.ORGANISM_CODE = 'ANA' " +
            "  ) " +
            "WHERE o.WHONET_ORG_CODE = :orgCode " +
            " AND o.TAXONOMIC_STATUS = :status " +
            //            "-- AND b.GUIDELINES = 'EUCAST' " +
            " AND b.YEAR = :year  " +
            //            "-- AND b.BREAKPOINT_TYPE = 'Animal'  " +
            //            "-- AND b.TEST_METHOD = 'MIC' " +
            //            "-- Filter on one drug. " +
            " AND b.WHONET_TEST = :whonetTest " +
            //            "-- AND b.WHONET_ABX_CODE = 'CRO' " +
            "ORDER BY o.WHONET_ORG_CODE ASC, " +
            " b.GUIDELINES ASC, " +
            " b.YEAR ASC, " +
            " b.TEST_METHOD ASC, " +
            " ( " +
            "  CASE b.BREAKPOINT_TYPE " +
            "  WHEN 'Human' THEN 1 " +
            "  WHEN 'Animal' THEN 2 " +
            "  WHEN 'ECOFF' THEN 3 " +
            "  END " +
            " ) ASC, " +
            " b.HOST ASC, ( " +
            "  CASE b.ORGANISM_CODE_TYPE " +
            "  WHEN 'SEROVAR_GROUP' THEN 1 " +
            "  WHEN 'WHONET_ORG_CODE' THEN 2 " +
            "  WHEN 'SPECIES_GROUP' THEN 3 " +
            "  WHEN 'GENUS_CODE' THEN 4 " +
            "   " +
            " " +
            "  WHEN 'GENUS_GROUP' THEN 5 " +
            "  WHEN 'FAMILY_CODE' THEN 6 " +
            "  WHEN 'ANAEROBE+SUBKINGDOM_CODE' THEN 7 " +
            "  WHEN 'ANAEROBE' THEN 8 " +
            "  ELSE 9 " +
            "  END " +
            " ) ASC, " +
            " b.WHONET_TEST ASC, " +
            " b.SITE_OF_INFECTION ASC";

        NativeQuery qry = getCurrentSession().createNativeQuery(sql);
        qry.setParameter("year", year);
        qry.setParameter("orgCode", orgCode);
        qry.setParameter("status", status);
        qry.setParameter("whonetTest", whonetTest);

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
}
