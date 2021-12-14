package org.path.amr.services.service.impl;

import java.util.List;
import java.util.Optional;
import org.path.amr.services.domain.Organism;
import org.path.amr.services.repository.CustomRepository;
import org.path.amr.services.repository.OrganismRepository;
import org.path.amr.services.service.OrganismService;
import org.path.amr.services.service.dto.OrganismDTO;
import org.path.amr.services.service.mapper.OrganismMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Organism}.
 */
@Service
@Transactional
public class OrganismServiceImpl implements OrganismService {

    private final Logger log = LoggerFactory.getLogger(OrganismServiceImpl.class);

    private final OrganismRepository organismRepository;

    private final OrganismMapper organismMapper;

    private final CustomRepository customRepository;

    public OrganismServiceImpl(OrganismRepository organismRepository, OrganismMapper organismMapper, CustomRepository customRepository) {
        this.organismRepository = organismRepository;
        this.organismMapper = organismMapper;
        this.customRepository = customRepository;
    }

    @Override
    public OrganismDTO save(OrganismDTO organismDTO) {
        log.debug("Request to save Organism : {}", organismDTO);
        Organism organism = organismMapper.toEntity(organismDTO);
        organism = organismRepository.save(organism);
        return organismMapper.toDto(organism);
    }

    @Override
    public Optional<OrganismDTO> partialUpdate(OrganismDTO organismDTO) {
        log.debug("Request to partially update Organism : {}", organismDTO);

        return organismRepository
            .findById(organismDTO.getId())
            .map(
                existingOrganism -> {
                    organismMapper.partialUpdate(existingOrganism, organismDTO);
                    return existingOrganism;
                }
            )
            .map(organismRepository::save)
            .map(organismMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrganismDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Organisms");
        return organismRepository.findAll(pageable).map(organismMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrganismDTO> findOne(Long id) {
        log.debug("Request to get Organism : {}", id);
        return organismRepository.findById(id).map(organismMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Organism : {}", id);
        organismRepository.deleteById(id);
    }

    @Override
    public List<String> findGroups(String key) {
        return customRepository.findOrganismGroupByField(key);
    }
}
