package vn.com.truyen.usermanagent.service.impl;

import vn.com.truyen.usermanagent.service.UsersService;
import vn.com.truyen.usermanagent.domain.Users;
import vn.com.truyen.usermanagent.repository.UsersRepository;
import vn.com.truyen.usermanagent.service.dto.UsersDTO;
import vn.com.truyen.usermanagent.service.mapper.UsersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Users}.
 */
@Service
@Transactional
public class UsersServiceImpl implements UsersService {

    private final Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final UsersRepository usersRepository;

    private final UsersMapper usersMapper;

    public UsersServiceImpl(UsersRepository usersRepository, UsersMapper usersMapper) {
        this.usersRepository = usersRepository;
        this.usersMapper = usersMapper;
    }

    /**
     * Save a users.
     *
     * @param usersDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UsersDTO save(UsersDTO usersDTO) {
        log.debug("Request to save Users : {}", usersDTO);
        Users users = usersMapper.toEntity(usersDTO);
        users = usersRepository.save(users);
        return usersMapper.toDto(users);
    }

    /**
     * Get all the users.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UsersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Users");
        return usersRepository.findAll(pageable)
            .map(usersMapper::toDto);
    }


    /**
     * Get all the users with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<UsersDTO> findAllWithEagerRelationships(Pageable pageable) {
        return usersRepository.findAllWithEagerRelationships(pageable).map(usersMapper::toDto);
    }

    /**
     * Get one users by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UsersDTO> findOne(Long id) {
        log.debug("Request to get Users : {}", id);
        return usersRepository.findOneWithEagerRelationships(id)
            .map(usersMapper::toDto);
    }

    /**
     * Delete the users by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Users : {}", id);
        usersRepository.deleteById(id);
    }
}
