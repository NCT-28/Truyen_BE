package vn.com.truyen.usermanagent.service;

import vn.com.truyen.usermanagent.service.dto.UsersDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link vn.com.truyen.usermanagent.domain.Users}.
 */
public interface UsersService {

    /**
     * Save a users.
     *
     * @param usersDTO the entity to save.
     * @return the persisted entity.
     */
    UsersDTO save(UsersDTO usersDTO);

    /**
     * Get all the users.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UsersDTO> findAll(Pageable pageable);

    /**
     * Get all the users with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<UsersDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" users.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UsersDTO> findOne(Long id);

    /**
     * Delete the "id" users.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
