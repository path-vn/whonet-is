package org.path.amr.services.service.impl;

import java.util.Optional;
import org.path.amr.services.domain.WhonetResource;
import org.path.amr.services.repository.WhonetResourceRepository;
import org.path.amr.services.service.WhonetResourceService;
import org.path.amr.services.service.dto.WhonetResourceDTO;
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

    public WhonetResourceServiceImpl(WhonetResourceRepository whonetResourceRepository, WhonetResourceMapper whonetResourceMapper) {
        this.whonetResourceRepository = whonetResourceRepository;
        this.whonetResourceMapper = whonetResourceMapper;
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
}
