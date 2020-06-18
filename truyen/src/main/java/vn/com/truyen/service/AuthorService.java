package vn.com.truyen.service;

import vn.com.truyen.service.dto.AuthorDTO;
import vn.com.truyen.service.mess.AuthorMess;

import org.hibernate.type.descriptor.java.IntegerTypeDescriptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link vn.com.truyen.domain.Author}.
 */
public interface AuthorService {

    /**
     * Save a author.
     *
     * @param authorDTO the entity to save.
     * @return the persisted entity.
     */
    AuthorDTO save(AuthorDTO authorDTO);

    /**
     * Get all the authors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    //Page<AuthorDTO> findAll(Pageable pageable);
    
    AuthorMess getAllAuthors(Integer pageNo, Integer pageSize, String name, String sortType, String sortBy);

    /**
     * Get the "id" author.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AuthorDTO> findOne(Long id);

    /**
     * Delete the "id" author.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
