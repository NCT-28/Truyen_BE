package vn.com.truyen.service;

import vn.com.truyen.service.dto.ChuongDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link vn.com.truyen.domain.Chuong}.
 */
public interface ChuongService {

    /**
     * Save a chuong.
     *
     * @param chuongDTO the entity to save.
     * @return the persisted entity.
     */
    ChuongDTO save(ChuongDTO chuongDTO);

    /**
     * Get all the chuongs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChuongDTO> findAll(Pageable pageable);


    /**
     * Get the "id" chuong.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChuongDTO> findOne(Long id);

    /**
     * Delete the "id" chuong.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
