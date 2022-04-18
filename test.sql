-- show
-- tables;
--
-- select *
-- from breakpoints limit 10;

SELECT o.ID as organismID, b.ID breakpointID, b.WHONET_TEST
FROM organisms o
         INNER JOIN breakpoints b ON b.ORGANISM_CODE_TYPE <> 'ALL' AND
                                     (o.SEROVAR_GROUP IS NOT NULL AND b.ORGANISM_CODE_TYPE = 'SEROVAR_GROUP' AND
                                      o.SEROVAR_GROUP = b.ORGANISM_CODE) OR
                                     (b.ORGANISM_CODE_TYPE = 'WHONET_ORG_CODE' AND
                                      o.WHONET_ORG_CODE = b.ORGANISM_CODE) OR
                                     (o.SPECIES_GROUP IS NOT NULL AND b.ORGANISM_CODE_TYPE = 'SPECIES_GROUP' AND
                                      o.SPECIES_GROUP = b.ORGANISM_CODE) OR
                                     (o.GENUS_CODE IS NOT NULL AND b.ORGANISM_CODE_TYPE = 'GENUS_CODE' AND
                                      o.GENUS_CODE = b.ORGANISM_CODE) OR
                                     (o.GENUS_GROUP IS NOT NULL AND b.ORGANISM_CODE_TYPE = 'GENUS_GROUP' AND
                                      o.GENUS_GROUP = b.ORGANISM_CODE) OR
                                     (o.FAMILY_CODE IS NOT NULL AND b.ORGANISM_CODE_TYPE = 'FAMILY_CODE' AND
                                      o.FAMILY_CODE = b.ORGANISM_CODE) OR
                                     (o.ANAEROBE = 'X' AND b.ORGANISM_CODE_TYPE = 'ANAEROBE+SUBKINGDOM_CODE' AND
                                      ((o.SUBKINGDOM_CODE = '+' AND b.ORGANISM_CODE = 'AN+') OR
                                       (o.SUBKINGDOM_CODE = '-' AND b.ORGANISM_CODE = 'AN-'))) OR
                                     (o.ANAEROBE = 'X' AND b.ORGANISM_CODE_TYPE = 'ANAEROBE' AND
                                      b.ORGANISM_CODE = 'ANA')
WHERE o.WHONET_ORG_CODE = 'eco'
--   AND o.TAXONOMIC_STATUS = 'C'
--   AND b.YEAR = 2021
--   AND b.BREAKPOINT_TYPE =
--       'Human'
  AND b.WHONET_TEST = 'AMK_NM'
ORDER BY o.WHONET_ORG_CODE ASC, b.GUIDELINES ASC, b.YEAR ASC, b.TEST_METHOD ASC,
         (CASE b.BREAKPOINT_TYPE WHEN 'Human' THEN 1 WHEN 'Animal' THEN 2 WHEN 'ECOFF' THEN 3 END) ASC, b.HOST ASC,
         (CASE b.ORGANISM_CODE_TYPE
              WHEN 'SEROVAR_GROUP' THEN 1
              WHEN 'WHONET_ORG_CODE' THEN 2
              WHEN 'SPECIES_GROUP' THEN 3
              WHEN 'GENUS_CODE' THEN 4
              WHEN 'GENUS_GROUP' THEN 5
              WHEN 'FAMILY_CODE' THEN 6
              WHEN 'ANAEROBE+SUBKINGDOM_CODE' THEN 7
              WHEN 'ANAEROBE' THEN 8
              ELSE 9 END) ASC, b.WHONET_TEST ASC, b.SITE_OF_INFECTION ASC
