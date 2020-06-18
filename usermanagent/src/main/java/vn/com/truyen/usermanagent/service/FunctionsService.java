package vn.com.truyen.usermanagent.service;

import vn.com.truyen.usermanagent.service.dto.FunctionsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link vn.com.truyen.usermanagent.domain.Functions}.
 */
public interface FunctionsService {

    /**
     * Save a functions.
     *
     * @param functionsDTO the entity to save.
     * @return the persisted entity.
     */
    FunctionsDTO save(FunctionsDTO functionsDTO);

    /**
     * Get all the functions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FunctionsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" functions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FunctionsDTO> findOne(Long id);

    /**
     * Delete the "id" functions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
