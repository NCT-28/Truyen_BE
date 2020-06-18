package vn.com.truyen.usermanagent.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import vn.com.truyen.usermanagent.domain.Users;
import vn.com.truyen.usermanagent.domain.*; // for static metamodels
import vn.com.truyen.usermanagent.repository.UsersRepository;
import vn.com.truyen.usermanagent.service.dto.UsersCriteria;
import vn.com.truyen.usermanagent.service.dto.UsersDTO;
import vn.com.truyen.usermanagent.service.mapper.UsersMapper;

/**
 * Service for executing complex queries for {@link Users} entities in the database.
 * The main input is a {@link UsersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UsersDTO} or a {@link Page} of {@link UsersDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UsersQueryService extends QueryService<Users> {

    private final Logger log = LoggerFactory.getLogger(UsersQueryService.class);

    private final UsersRepository usersRepository;

    private final UsersMapper usersMapper;

    public UsersQueryService(UsersRepository usersRepository, UsersMapper usersMapper) {
        this.usersRepository = usersRepository;
        this.usersMapper = usersMapper;
    }

    /**
     * Return a {@link List} of {@link UsersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UsersDTO> findByCriteria(UsersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Users> specification = createSpecification(criteria);
        return usersMapper.toDto(usersRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UsersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UsersDTO> findByCriteria(UsersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Users> specification = createSpecification(criteria);
        return usersRepository.findAll(specification, page)
            .map(usersMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UsersCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Users> specification = createSpecification(criteria);
        return usersRepository.count(specification);
    }

    /**
     * Function to convert {@link UsersCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Users> createSpecification(UsersCriteria criteria) {
        Specification<Users> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Users_.id));
            }
            if (criteria.getLogin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogin(), Users_.login));
            }
            if (criteria.getPasswordHash() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPasswordHash(), Users_.passwordHash));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Users_.email));
            }
            if (criteria.getImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageUrl(), Users_.imageUrl));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), Users_.activated));
            }
            if (criteria.getLocked() != null) {
                specification = specification.and(buildSpecification(criteria.getLocked(), Users_.locked));
            }
            if (criteria.getCanChange() != null) {
                specification = specification.and(buildSpecification(criteria.getCanChange(), Users_.canChange));
            }
            if (criteria.getMustChange() != null) {
                specification = specification.and(buildSpecification(criteria.getMustChange(), Users_.mustChange));
            }
            if (criteria.getActivationKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActivationKey(), Users_.activationKey));
            }
            if (criteria.getResetKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResetKey(), Users_.resetKey));
            }
            if (criteria.getResetDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResetDate(), Users_.resetDate));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Users_.code));
            }
            if (criteria.getRoleId() != null) {
                specification = specification.and(buildSpecification(criteria.getRoleId(),
                    root -> root.join(Users_.roles, JoinType.LEFT).get(Role_.id)));
            }
            if (criteria.getLoginId() != null) {
                specification = specification.and(buildSpecification(criteria.getLoginId(),
                    root -> root.join(Users_.logins, JoinType.LEFT).get(GroupTranslate_.id)));
            }
        }
        return specification;
    }
}
