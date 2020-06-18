package vn.com.truyen.usermanagent.service.impl;

import vn.com.truyen.usermanagent.service.FunctionsService;
import vn.com.truyen.usermanagent.domain.Functions;
import vn.com.truyen.usermanagent.repository.FunctionsRepository;
import vn.com.truyen.usermanagent.service.dto.FunctionsDTO;
import vn.com.truyen.usermanagent.service.mapper.FunctionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Functions}.
 */
@Service
@Transactional
public class FunctionsServiceImpl implements FunctionsService {

    private final Logger log = LoggerFactory.getLogger(FunctionsServiceImpl.class);

    private final FunctionsRepository functionsRepository;

    private final FunctionsMapper functionsMapper;

    public FunctionsServiceImpl(FunctionsRepository functionsRepository, FunctionsMapper functionsMapper) {
        this.functionsRepository = functionsRepository;
        this.functionsMapper = functionsMapper;
    }

    /**
     * Save a functions.
     *
     * @param functionsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FunctionsDTO save(FunctionsDTO functionsDTO) {
        log.debug("Request to save Functions : {}", functionsDTO);
        Functions functions = functionsMapper.toEntity(functionsDTO);
        functions = functionsRepository.save(functions);
        return functionsMapper.toDto(functions);
    }

    /**
     * Get all the functions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FunctionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Functions");
        return functionsRepository.findAll(pageable)
            .map(functionsMapper::toDto);
    }


    /**
     * Get one functions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FunctionsDTO> findOne(Long id) {
        log.debug("Request to get Functions : {}", id);
        return functionsRepository.findById(id)
            .map(functionsMapper::toDto);
    }

    /**
     * Delete the functions by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Functions : {}", id);
        functionsRepository.deleteById(id);
    }
}
