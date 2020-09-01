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

import vn.com.truyen.usermanagent.domain.GroupTranslate;
import vn.com.truyen.usermanagent.domain.*; // for static metamodels
import vn.com.truyen.usermanagent.repository.GroupTranslateRepository;
import vn.com.truyen.usermanagent.service.dto.GroupTranslateCriteria;
import vn.com.truyen.usermanagent.service.dto.GroupTranslateDTO;
import vn.com.truyen.usermanagent.service.mapper.GroupTranslateMapper;

/**
 * Service for executing complex queries for {@link GroupTranslate} entities in the database.
 * The main input is a {@link GroupTranslateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GroupTranslateDTO} or a {@link Page} of {@link GroupTranslateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GroupTranslateQueryService extends QueryService<GroupTranslate> {

    private final Logger log = LoggerFactory.getLogger(GroupTranslateQueryService.class);

    private final GroupTranslateRepository groupTranslateRepository;

    private final GroupTranslateMapper groupTranslateMapper;

    public GroupTranslateQueryService(GroupTranslateRepository groupTranslateRepository, GroupTranslateMapper groupTranslateMapper) {
        this.groupTranslateRepository = groupTranslateRepository;
        this.groupTranslateMapper = groupTranslateMapper;
    }

    /**
     * Return a {@link List} of {@link GroupTranslateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GroupTranslateDTO> findByCriteria(GroupTranslateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GroupTranslate> specification = createSpecification(criteria);
        return groupTranslateMapper.toDto(groupTranslateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link GroupTranslateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GroupTranslateDTO> findByCriteria(GroupTranslateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GroupTranslate> specification = createSpecification(criteria);
        return groupTranslateRepository.findAll(specification, page)
            .map(groupTranslateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GroupTranslateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<GroupTranslate> specification = createSpecification(criteria);
        return groupTranslateRepository.count(specification);
    }

    /**
     * Function to convert {@link GroupTranslateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<GroupTranslate> createSpecification(GroupTranslateCriteria criteria) {
        Specification<GroupTranslate> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), GroupTranslate_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), GroupTranslate_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), GroupTranslate_.description));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), GroupTranslate_.code));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(GroupTranslate_.users, JoinType.LEFT).get(Users_.id)));
            }
            if (criteria.getRoleId() != null) {
                specification = specification.and(buildSpecification(criteria.getRoleId(),
                    root -> root.join(GroupTranslate_.roles, JoinType.LEFT).get(Role_.id)));
            }
        }
        return specification;
    }
}
