package vn.com.truyen.usermanagent.service;

import vn.com.truyen.usermanagent.service.dto.GroupTranslateDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link vn.com.truyen.usermanagent.domain.GroupTranslate}.
 */
public interface GroupTranslateService {

    /**
     * Save a groupTranslate.
     *
     * @param groupTranslateDTO the entity to save.
     * @return the persisted entity.
     */
    GroupTranslateDTO save(GroupTranslateDTO groupTranslateDTO);

    /**
     * Get all the groupTranslates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GroupTranslateDTO> findAll(Pageable pageable);

    /**
     * Get all the groupTranslates with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<GroupTranslateDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" groupTranslate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GroupTranslateDTO> findOne(Long id);

    /**
     * Delete the "id" groupTranslate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
