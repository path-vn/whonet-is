package org.path.amr.services.service.impl;

import java.util.Optional;
import org.path.amr.services.domain.Execute;
import org.path.amr.services.repository.ExecuteRepository;
import org.path.amr.services.service.ExecuteService;
import org.path.amr.services.service.dto.ExecuteDTO;
import org.path.amr.services.service.mapper.ExecuteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Execute}.
 */
@Service
@Transactional
public class ExecuteServiceImpl implements ExecuteService {

    private final Logger log = LoggerFactory.getLogger(ExecuteServiceImpl.class);

    private final ExecuteRepository executeRepository;

    private final ExecuteMapper executeMapper;

    public ExecuteServiceImpl(ExecuteRepository executeRepository, ExecuteMapper executeMapper) {
        this.executeRepository = executeRepository;
        this.executeMapper = executeMapper;
    }

    @Override
    public ExecuteDTO save(ExecuteDTO executeDTO) {
        log.debug("Request to save Execute : {}", executeDTO);
        Execute execute = executeMapper.toEntity(executeDTO);
        execute = executeRepository.save(execute);
        return executeMapper.toDto(execute);
    }

    @Override
    public Optional<ExecuteDTO> partialUpdate(ExecuteDTO executeDTO) {
        log.debug("Request to partially update Execute : {}", executeDTO);

        return executeRepository
            .findById(executeDTO.getId())
            .map(
                existingExecute -> {
                    executeMapper.partialUpdate(existingExecute, executeDTO);
                    return existingExecute;
                }
            )
            .map(executeRepository::save)
            .map(executeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExecuteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Executes");
        return executeRepository.findAll(pageable).map(executeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExecuteDTO> findOne(Long id) {
        log.debug("Request to get Execute : {}", id);
        return executeRepository.findById(id).map(executeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Execute : {}", id);
        executeRepository.deleteById(id);
    }
}
