package vn.com.truyen.usermanagent.web.rest;

import vn.com.truyen.usermanagent.service.GroupTranslateService;
import vn.com.truyen.usermanagent.web.rest.errors.BadRequestAlertException;
import vn.com.truyen.usermanagent.service.dto.GroupTranslateDTO;
import vn.com.truyen.usermanagent.service.dto.GroupTranslateCriteria;
import vn.com.truyen.usermanagent.service.GroupTranslateQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link vn.com.truyen.usermanagent.domain.GroupTranslate}.
 */
@RestController
@RequestMapping("/api")
public class GroupTranslateResource {

    private final Logger log = LoggerFactory.getLogger(GroupTranslateResource.class);

    private static final String ENTITY_NAME = "usermanagentGroupTranslate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupTranslateService groupTranslateService;

    private final GroupTranslateQueryService groupTranslateQueryService;

    public GroupTranslateResource(GroupTranslateService groupTranslateService, GroupTranslateQueryService groupTranslateQueryService) {
        this.groupTranslateService = groupTranslateService;
        this.groupTranslateQueryService = groupTranslateQueryService;
    }

    /**
     * {@code POST  /group-translates} : Create a new groupTranslate.
     *
     * @param groupTranslateDTO the groupTranslateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupTranslateDTO, or with status {@code 400 (Bad Request)} if the groupTranslate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/group-translates")
    public ResponseEntity<GroupTranslateDTO> createGroupTranslate(@Valid @RequestBody GroupTranslateDTO groupTranslateDTO) throws URISyntaxException {
        log.debug("REST request to save GroupTranslate : {}", groupTranslateDTO);
        if (groupTranslateDTO.getId() != null) {
            throw new BadRequestAlertException("A new groupTranslate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupTranslateDTO result = groupTranslateService.save(groupTranslateDTO);
        return ResponseEntity.created(new URI("/api/group-translates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /group-translates} : Updates an existing groupTranslate.
     *
     * @param groupTranslateDTO the groupTranslateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupTranslateDTO,
     * or with status {@code 400 (Bad Request)} if the groupTranslateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupTranslateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/group-translates")
    public ResponseEntity<GroupTranslateDTO> updateGroupTranslate(@Valid @RequestBody GroupTranslateDTO groupTranslateDTO) throws URISyntaxException {
        log.debug("REST request to update GroupTranslate : {}", groupTranslateDTO);
        if (groupTranslateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroupTranslateDTO result = groupTranslateService.save(groupTranslateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groupTranslateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /group-translates} : get all the groupTranslates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupTranslates in body.
     */
    @GetMapping("/group-translates")
    public ResponseEntity<List<GroupTranslateDTO>> getAllGroupTranslates(GroupTranslateCriteria criteria, Pageable pageable) {
        log.debug("REST request to get GroupTranslates by criteria: {}", criteria);
        Page<GroupTranslateDTO> page = groupTranslateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /group-translates/count} : count all the groupTranslates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/group-translates/count")
    public ResponseEntity<Long> countGroupTranslates(GroupTranslateCriteria criteria) {
        log.debug("REST request to count GroupTranslates by criteria: {}", criteria);
        return ResponseEntity.ok().body(groupTranslateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /group-translates/:id} : get the "id" groupTranslate.
     *
     * @param id the id of the groupTranslateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupTranslateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/group-translates/{id}")
    public ResponseEntity<GroupTranslateDTO> getGroupTranslate(@PathVariable Long id) {
        log.debug("REST request to get GroupTranslate : {}", id);
        Optional<GroupTranslateDTO> groupTranslateDTO = groupTranslateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupTranslateDTO);
    }

    /**
     * {@code DELETE  /group-translates/:id} : delete the "id" groupTranslate.
     *
     * @param id the id of the groupTranslateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/group-translates/{id}")
    public ResponseEntity<Void> deleteGroupTranslate(@PathVariable Long id) {
        log.debug("REST request to delete GroupTranslate : {}", id);
        groupTranslateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
