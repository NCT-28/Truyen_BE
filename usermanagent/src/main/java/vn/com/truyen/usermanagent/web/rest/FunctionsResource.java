package vn.com.truyen.usermanagent.web.rest;

import vn.com.truyen.usermanagent.service.FunctionsService;
import vn.com.truyen.usermanagent.web.rest.errors.BadRequestAlertException;
import vn.com.truyen.usermanagent.service.dto.FunctionsDTO;
import vn.com.truyen.usermanagent.service.dto.FunctionsCriteria;
import vn.com.truyen.usermanagent.service.FunctionsQueryService;

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
 * REST controller for managing {@link vn.com.truyen.usermanagent.domain.Functions}.
 */
@RestController
@RequestMapping("/api")
public class FunctionsResource {

    private final Logger log = LoggerFactory.getLogger(FunctionsResource.class);

    private static final String ENTITY_NAME = "usermanagentFunctions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FunctionsService functionsService;

    private final FunctionsQueryService functionsQueryService;

    public FunctionsResource(FunctionsService functionsService, FunctionsQueryService functionsQueryService) {
        this.functionsService = functionsService;
        this.functionsQueryService = functionsQueryService;
    }

    /**
     * {@code POST  /functions} : Create a new functions.
     *
     * @param functionsDTO the functionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new functionsDTO, or with status {@code 400 (Bad Request)} if the functions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/functions")
    public ResponseEntity<FunctionsDTO> createFunctions(@Valid @RequestBody FunctionsDTO functionsDTO) throws URISyntaxException {
        log.debug("REST request to save Functions : {}", functionsDTO);
        if (functionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new functions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FunctionsDTO result = functionsService.save(functionsDTO);
        return ResponseEntity.created(new URI("/api/functions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /functions} : Updates an existing functions.
     *
     * @param functionsDTO the functionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated functionsDTO,
     * or with status {@code 400 (Bad Request)} if the functionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the functionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/functions")
    public ResponseEntity<FunctionsDTO> updateFunctions(@Valid @RequestBody FunctionsDTO functionsDTO) throws URISyntaxException {
        log.debug("REST request to update Functions : {}", functionsDTO);
        if (functionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FunctionsDTO result = functionsService.save(functionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, functionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /functions} : get all the functions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of functions in body.
     */
    @GetMapping("/functions")
    public ResponseEntity<List<FunctionsDTO>> getAllFunctions(FunctionsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Functions by criteria: {}", criteria);
        Page<FunctionsDTO> page = functionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /functions/count} : count all the functions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/functions/count")
    public ResponseEntity<Long> countFunctions(FunctionsCriteria criteria) {
        log.debug("REST request to count Functions by criteria: {}", criteria);
        return ResponseEntity.ok().body(functionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /functions/:id} : get the "id" functions.
     *
     * @param id the id of the functionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the functionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/functions/{id}")
    public ResponseEntity<FunctionsDTO> getFunctions(@PathVariable Long id) {
        log.debug("REST request to get Functions : {}", id);
        Optional<FunctionsDTO> functionsDTO = functionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(functionsDTO);
    }

    /**
     * {@code DELETE  /functions/:id} : delete the "id" functions.
     *
     * @param id the id of the functionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/functions/{id}")
    public ResponseEntity<Void> deleteFunctions(@PathVariable Long id) {
        log.debug("REST request to delete Functions : {}", id);
        functionsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
