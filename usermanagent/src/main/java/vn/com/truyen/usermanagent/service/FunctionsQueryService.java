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

import vn.com.truyen.usermanagent.domain.Functions;
import vn.com.truyen.usermanagent.domain.*; // for static metamodels
import vn.com.truyen.usermanagent.repository.FunctionsRepository;
import vn.com.truyen.usermanagent.service.dto.FunctionsCriteria;
import vn.com.truyen.usermanagent.service.dto.FunctionsDTO;
import vn.com.truyen.usermanagent.service.mapper.FunctionsMapper;

/**
 * Service for executing complex queries for {@link Functions} entities in the database.
 * The main input is a {@link FunctionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FunctionsDTO} or a {@link Page} of {@link FunctionsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FunctionsQueryService extends QueryService<Functions> {

    private final Logger log = LoggerFactory.getLogger(FunctionsQueryService.class);

    private final FunctionsRepository functionsRepository;

    private final FunctionsMapper functionsMapper;

    public FunctionsQueryService(FunctionsRepository functionsRepository, FunctionsMapper functionsMapper) {
        this.functionsRepository = functionsRepository;
        this.functionsMapper = functionsMapper;
    }

    /**
     * Return a {@link List} of {@link FunctionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FunctionsDTO> findByCriteria(FunctionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Functions> specification = createSpecification(criteria);
        return functionsMapper.toDto(functionsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FunctionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FunctionsDTO> findByCriteria(FunctionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Functions> specification = createSpecification(criteria);
        return functionsRepository.findAll(specification, page)
            .map(functionsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FunctionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Functions> specification = createSpecification(criteria);
        return functionsRepository.count(specification);
    }

    /**
     * Function to convert {@link FunctionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Functions> createSpecification(FunctionsCriteria criteria) {
        Specification<Functions> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Functions_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Functions_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Functions_.description));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Functions_.code));
            }
            if (criteria.getNameId() != null) {
                specification = specification.and(buildSpecification(criteria.getNameId(),
                    root -> root.join(Functions_.names, JoinType.LEFT).get(Role_.id)));
            }
        }
        return specification;
    }
}
