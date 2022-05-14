package org.path.amr.services.service.impl;

import java.util.Optional;
import org.path.amr.services.domain.Breakpoint;
import org.path.amr.services.repository.BreakpointRepository;
import org.path.amr.services.service.BreakpointService;
import org.path.amr.services.service.dto.BreakpointDTO;
import org.path.amr.services.service.mapper.BreakpointMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Breakpoint}.
 */
@Service
@Transactional
public class BreakpointServiceImpl implements BreakpointService {

    private final Logger log = LoggerFactory.getLogger(BreakpointServiceImpl.class);

    private final BreakpointRepository breakpointRepository;

    private final BreakpointMapper breakpointMapper;

    public BreakpointServiceImpl(BreakpointRepository breakpointRepository, BreakpointMapper breakpointMapper) {
        this.breakpointRepository = breakpointRepository;
        this.breakpointMapper = breakpointMapper;
    }

    @Override
    public BreakpointDTO save(BreakpointDTO breakpointDTO) {
        log.debug("Request to save Breakpoint : {}", breakpointDTO);
        Breakpoint breakpoint = breakpointMapper.toEntity(breakpointDTO);
        breakpoint = breakpointRepository.save(breakpoint);
        return breakpointMapper.toDto(breakpoint);
    }

    @Override
    public Optional<BreakpointDTO> partialUpdate(BreakpointDTO breakpointDTO) {
        log.debug("Request to partially update Breakpoint : {}", breakpointDTO);

        return breakpointRepository
            .findById(breakpointDTO.getId())
            .map(
                existingBreakpoint -> {
                    breakpointMapper.partialUpdate(existingBreakpoint, breakpointDTO);
                    return existingBreakpoint;
                }
            )
            .map(breakpointRepository::save)
            .map(breakpointMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BreakpointDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Breakpoints");
        return breakpointRepository.findAll(pageable).map(breakpointMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BreakpointDTO> findOne(Long id) {
        log.debug("Request to get Breakpoint : {}", id);
        return breakpointRepository.findById(id).map(breakpointMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Breakpoint : {}", id);
        breakpointRepository.deleteById(id);
    }
}
