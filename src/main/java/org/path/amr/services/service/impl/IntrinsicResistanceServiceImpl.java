package org.path.amr.services.service.impl;

import java.util.List;
import java.util.Optional;
import org.path.amr.services.domain.IntrinsicResistance;
import org.path.amr.services.repository.CustomRepository;
import org.path.amr.services.repository.IntrinsicResistanceRepository;
import org.path.amr.services.service.IntrinsicResistanceService;
import org.path.amr.services.service.dto.IntrinsicResistanceDTO;
import org.path.amr.services.service.mapper.IntrinsicResistanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IntrinsicResistance}.
 */
@Service
@Transactional
public class IntrinsicResistanceServiceImpl implements IntrinsicResistanceService {

    private final Logger log = LoggerFactory.getLogger(IntrinsicResistanceServiceImpl.class);

    private final IntrinsicResistanceRepository intrinsicResistanceRepository;

    private final IntrinsicResistanceMapper intrinsicResistanceMapper;

    private final CustomRepository customRepository;

    public IntrinsicResistanceServiceImpl(
        IntrinsicResistanceRepository intrinsicResistanceRepository,
        IntrinsicResistanceMapper intrinsicResistanceMapper,
        CustomRepository customRepository
    ) {
        this.intrinsicResistanceRepository = intrinsicResistanceRepository;
        this.intrinsicResistanceMapper = intrinsicResistanceMapper;
        this.customRepository = customRepository;
    }

    @Override
    public IntrinsicResistanceDTO save(IntrinsicResistanceDTO intrinsicResistanceDTO) {
        log.debug("Request to save IntrinsicResistance : {}", intrinsicResistanceDTO);
        IntrinsicResistance intrinsicResistance = intrinsicResistanceMapper.toEntity(intrinsicResistanceDTO);
        intrinsicResistance = intrinsicResistanceRepository.save(intrinsicResistance);
        return intrinsicResistanceMapper.toDto(intrinsicResistance);
    }

    @Override
    public Optional<IntrinsicResistanceDTO> partialUpdate(IntrinsicResistanceDTO intrinsicResistanceDTO) {
        log.debug("Request to partially update IntrinsicResistance : {}", intrinsicResistanceDTO);

        return intrinsicResistanceRepository
            .findById(intrinsicResistanceDTO.getId())
            .map(
                existingIntrinsicResistance -> {
                    intrinsicResistanceMapper.partialUpdate(existingIntrinsicResistance, intrinsicResistanceDTO);
                    return existingIntrinsicResistance;
                }
            )
            .map(intrinsicResistanceRepository::save)
            .map(intrinsicResistanceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IntrinsicResistanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IntrinsicResistances");
        return intrinsicResistanceRepository.findAll(pageable).map(intrinsicResistanceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IntrinsicResistanceDTO> findOne(Long id) {
        log.debug("Request to get IntrinsicResistance : {}", id);
        return intrinsicResistanceRepository.findById(id).map(intrinsicResistanceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IntrinsicResistance : {}", id);
        intrinsicResistanceRepository.deleteById(id);
    }

    @Override
    public List<String> findGroups(String key) {
        return customRepository.findIntrinsicResistanceGroupByField(key);
    }
}
