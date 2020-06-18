package vn.com.truyen.service;

import vn.com.truyen.service.dto.ViewDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link vn.com.truyen.domain.View}.
 */
public interface ViewService {

    /**
     * Save a view.
     *
     * @param viewDTO the entity to save.
     * @return the persisted entity.
     */
    ViewDTO save(ViewDTO viewDTO);

    /**
     * Get all the views.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ViewDTO> findAll(Pageable pageable);


    /**
     * Get the "id" view.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ViewDTO> findOne(Long id);

    /**
     * Delete the "id" view.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
