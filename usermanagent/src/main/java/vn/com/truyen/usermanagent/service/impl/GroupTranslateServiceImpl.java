package vn.com.truyen.usermanagent.service.impl;

import vn.com.truyen.usermanagent.service.GroupTranslateService;
import vn.com.truyen.usermanagent.domain.GroupTranslate;
import vn.com.truyen.usermanagent.repository.GroupTranslateRepository;
import vn.com.truyen.usermanagent.service.dto.GroupTranslateDTO;
import vn.com.truyen.usermanagent.service.mapper.GroupTranslateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GroupTranslate}.
 */
@Service
@Transactional
public class GroupTranslateServiceImpl implements GroupTranslateService {

    private final Logger log = LoggerFactory.getLogger(GroupTranslateServiceImpl.class);

    private final GroupTranslateRepository groupTranslateRepository;

    private final GroupTranslateMapper groupTranslateMapper;

    public GroupTranslateServiceImpl(GroupTranslateRepository groupTranslateRepository, GroupTranslateMapper groupTranslateMapper) {
        this.groupTranslateRepository = groupTranslateRepository;
        this.groupTranslateMapper = groupTranslateMapper;
    }

    /**
     * Save a groupTranslate.
     *
     * @param groupTranslateDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public GroupTranslateDTO save(GroupTranslateDTO groupTranslateDTO) {
        log.debug("Request to save GroupTranslate : {}", groupTranslateDTO);
        GroupTranslate groupTranslate = groupTranslateMapper.toEntity(groupTranslateDTO);
        groupTranslate = groupTranslateRepository.save(groupTranslate);
        return groupTranslateMapper.toDto(groupTranslate);
    }

    /**
     * Get all the groupTranslates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GroupTranslateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GroupTranslates");
        return groupTranslateRepository.findAll(pageable)
            .map(groupTranslateMapper::toDto);
    }


    /**
     * Get all the groupTranslates with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<GroupTranslateDTO> findAllWithEagerRelationships(Pageable pageable) {
        return groupTranslateRepository.findAllWithEagerRelationships(pageable).map(groupTranslateMapper::toDto);
    }

    /**
     * Get one groupTranslate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GroupTranslateDTO> findOne(Long id) {
        log.debug("Request to get GroupTranslate : {}", id);
        return groupTranslateRepository.findOneWithEagerRelationships(id)
            .map(groupTranslateMapper::toDto);
    }

    /**
     * Delete the groupTranslate by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GroupTranslate : {}", id);
        groupTranslateRepository.deleteById(id);
    }
}
