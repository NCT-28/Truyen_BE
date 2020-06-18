package vn.com.truyen.service;

import vn.com.truyen.service.dto.TruyenDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link vn.com.truyen.domain.Truyen}.
 */
public interface TruyenService {

    /**
     * Save a truyen.
     *
     * @param truyenDTO the entity to save.
     * @return the persisted entity.
     */
    TruyenDTO save(TruyenDTO truyenDTO);

    /**
     * Get all the truyens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TruyenDTO> findAll(Pageable pageable);


    /**
     * Get the "id" truyen.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TruyenDTO> findOne(Long id);

    /**
     * Delete the "id" truyen.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
