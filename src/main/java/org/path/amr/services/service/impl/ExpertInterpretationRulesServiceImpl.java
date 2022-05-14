package org.path.amr.services.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.path.amr.services.domain.ExpertInterpretationRules;
import org.path.amr.services.repository.ExpertInterpretationRulesRepository;
import org.path.amr.services.service.ExpertInterpretationRulesService;
import org.path.amr.services.service.dto.ExpertInterpretationRulesDTO;
import org.path.amr.services.service.mapper.ExpertInterpretationRulesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ExpertInterpretationRules}.
 */
@Service
@Transactional
public class ExpertInterpretationRulesServiceImpl implements ExpertInterpretationRulesService {

    private final Logger log = LoggerFactory.getLogger(ExpertInterpretationRulesServiceImpl.class);

    private final ExpertInterpretationRulesRepository expertInterpretationRulesRepository;

    private final ExpertInterpretationRulesMapper expertInterpretationRulesMapper;

    public ExpertInterpretationRulesServiceImpl(
        ExpertInterpretationRulesRepository expertInterpretationRulesRepository,
        ExpertInterpretationRulesMapper expertInterpretationRulesMapper
    ) {
        this.expertInterpretationRulesRepository = expertInterpretationRulesRepository;
        this.expertInterpretationRulesMapper = expertInterpretationRulesMapper;
    }

    @Override
    public ExpertInterpretationRulesDTO save(ExpertInterpretationRulesDTO expertInterpretationRulesDTO) {
        log.debug("Request to save ExpertInterpretationRules : {}", expertInterpretationRulesDTO);
        ExpertInterpretationRules expertInterpretationRules = expertInterpretationRulesMapper.toEntity(expertInterpretationRulesDTO);
        expertInterpretationRules = expertInterpretationRulesRepository.save(expertInterpretationRules);
        return expertInterpretationRulesMapper.toDto(expertInterpretationRules);
    }

    @Override
    public Optional<ExpertInterpretationRulesDTO> partialUpdate(ExpertInterpretationRulesDTO expertInterpretationRulesDTO) {
        log.debug("Request to partially update ExpertInterpretationRules : {}", expertInterpretationRulesDTO);

        return expertInterpretationRulesRepository
            .findById(expertInterpretationRulesDTO.getId())
            .map(
                existingExpertInterpretationRules -> {
                    expertInterpretationRulesMapper.partialUpdate(existingExpertInterpretationRules, expertInterpretationRulesDTO);
                    return existingExpertInterpretationRules;
                }
            )
            .map(expertInterpretationRulesRepository::save)
            .map(expertInterpretationRulesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExpertInterpretationRulesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExpertInterpretationRules");
        return expertInterpretationRulesRepository.findAll(pageable).map(expertInterpretationRulesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExpertInterpretationRulesDTO> findOne(Long id) {
        log.debug("Request to get ExpertInterpretationRules : {}", id);
        return expertInterpretationRulesRepository.findById(id).map(expertInterpretationRulesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExpertInterpretationRules : {}", id);
        expertInterpretationRulesRepository.deleteById(id);
    }

    @Override
    public void flushAllAndSaveAll(List<ExpertInterpretationRulesDTO> newDTO) {
        expertInterpretationRulesRepository.deleteAllInBatch();
        expertInterpretationRulesRepository.saveAll(
            newDTO.stream().map(expertInterpretationRulesMapper::toEntity).collect(Collectors.toList())
        );
    }
}
