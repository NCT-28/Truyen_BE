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

import vn.com.truyen.usermanagent.domain.Role;
import vn.com.truyen.usermanagent.domain.*; // for static metamodels
import vn.com.truyen.usermanagent.repository.RoleRepository;
import vn.com.truyen.usermanagent.service.dto.RoleCriteria;
import vn.com.truyen.usermanagent.service.dto.RoleDTO;
import vn.com.truyen.usermanagent.service.mapper.RoleMapper;

/**
 * Service for executing complex queries for {@link Role} entities in the database.
 * The main input is a {@link RoleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RoleDTO} or a {@link Page} of {@link RoleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RoleQueryService extends QueryService<Role> {

    private final Logger log = LoggerFactory.getLogger(RoleQueryService.class);

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public RoleQueryService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    /**
     * Return a {@link List} of {@link RoleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RoleDTO> findByCriteria(RoleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Role> specification = createSpecification(criteria);
        return roleMapper.toDto(roleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RoleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RoleDTO> findByCriteria(RoleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Role> specification = createSpecification(criteria);
        return roleRepository.findAll(specification, page)
            .map(roleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RoleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Role> specification = createSpecification(criteria);
        return roleRepository.count(specification);
    }

    /**
     * Function to convert {@link RoleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Role> createSpecification(RoleCriteria criteria) {
        Specification<Role> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Role_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Role_.name));
            }
            if (criteria.getContent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContent(), Role_.content));
            }
            if (criteria.getLocked() != null) {
                specification = specification.and(buildSpecification(criteria.getLocked(), Role_.locked));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Role_.code));
            }
            if (criteria.getFunctionId() != null) {
                specification = specification.and(buildSpecification(criteria.getFunctionId(),
                    root -> root.join(Role_.functions, JoinType.LEFT).get(Functions_.id)));
            }
            if (criteria.getNameId() != null) {
                specification = specification.and(buildSpecification(criteria.getNameId(),
                    root -> root.join(Role_.names, JoinType.LEFT).get(GroupTranslate_.id)));
            }
            if (criteria.getLoginId() != null) {
                specification = specification.and(buildSpecification(criteria.getLoginId(),
                    root -> root.join(Role_.logins, JoinType.LEFT).get(Users_.id)));
            }
        }
        return specification;
    }
}
