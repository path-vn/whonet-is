package org.path.amr.services.service.impl;

import java.util.List;
import java.util.Optional;
import org.path.amr.services.domain.Antibiotic;
import org.path.amr.services.repository.AntibioticRepository;
import org.path.amr.services.repository.CustomRepository;
import org.path.amr.services.service.AntibioticService;
import org.path.amr.services.service.dto.AntibioticDTO;
import org.path.amr.services.service.mapper.AntibioticMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Antibiotic}.
 */
@Service
@Transactional
public class AntibioticServiceImpl implements AntibioticService {

    private final Logger log = LoggerFactory.getLogger(AntibioticServiceImpl.class);

    private final AntibioticRepository antibioticRepository;

    private final AntibioticMapper antibioticMapper;

    private final CustomRepository customRepository;

    public AntibioticServiceImpl(
        AntibioticRepository antibioticRepository,
        AntibioticMapper antibioticMapper,
        CustomRepository customRepository
    ) {
        this.antibioticRepository = antibioticRepository;
        this.antibioticMapper = antibioticMapper;
        this.customRepository = customRepository;
    }

    @Override
    public AntibioticDTO save(AntibioticDTO antibioticDTO) {
        log.debug("Request to save Antibiotic : {}", antibioticDTO);
        Antibiotic antibiotic = antibioticMapper.toEntity(antibioticDTO);
        antibiotic = antibioticRepository.save(antibiotic);
        return antibioticMapper.toDto(antibiotic);
    }

    @Override
    public Optional<AntibioticDTO> partialUpdate(AntibioticDTO antibioticDTO) {
        log.debug("Request to partially update Antibiotic : {}", antibioticDTO);

        return antibioticRepository
            .findById(antibioticDTO.getId())
            .map(
                existingAntibiotic -> {
                    antibioticMapper.partialUpdate(existingAntibiotic, antibioticDTO);
                    return existingAntibiotic;
                }
            )
            .map(antibioticRepository::save)
            .map(antibioticMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AntibioticDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Antibiotics");
        return antibioticRepository.findAll(pageable).map(antibioticMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AntibioticDTO> findOne(Long id) {
        log.debug("Request to get Antibiotic : {}", id);
        return antibioticRepository.findById(id).map(antibioticMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Antibiotic : {}", id);
        antibioticRepository.deleteById(id);
    }

    @Override
    public List<String> findGroups(String key) {
        return customRepository.findAntibioticGroupByField(key);
    }
}
