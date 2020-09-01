package vn.com.truyen.web.rest;

import vn.com.truyen.service.ChuongService;
import vn.com.truyen.web.rest.errors.BadRequestAlertException;
import vn.com.truyen.service.dto.ChuongDTO;
import vn.com.truyen.service.dto.ChuongCriteria;

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
 * REST controller for managing {@link vn.com.truyen.domain.Chuong}.
 */
@RestController
@RequestMapping("/api")
public class ChuongResource {

    private final Logger log = LoggerFactory.getLogger(ChuongResource.class);

    private static final String ENTITY_NAME = "truyenChuong";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChuongService chuongService;


    public ChuongResource(ChuongService chuongService) {
        this.chuongService = chuongService;
    }

    /**
     * {@code POST  /chuongs} : Create a new chuong.
     *
     * @param chuongDTO the chuongDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chuongDTO, or with status {@code 400 (Bad Request)} if the chuong has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chuongs")
    public ResponseEntity<ChuongDTO> createChuong(@Valid @RequestBody ChuongDTO chuongDTO) throws URISyntaxException {
        log.debug("REST request to save Chuong : {}", chuongDTO);
        if (chuongDTO.getId() != null) {
            throw new BadRequestAlertException("A new chuong cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChuongDTO result = chuongService.save(chuongDTO);
        return ResponseEntity.created(new URI("/api/chuongs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chuongs} : Updates an existing chuong.
     *
     * @param chuongDTO the chuongDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chuongDTO,
     * or with status {@code 400 (Bad Request)} if the chuongDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chuongDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chuongs")
    public ResponseEntity<ChuongDTO> updateChuong(@Valid @RequestBody ChuongDTO chuongDTO) throws URISyntaxException {
        log.debug("REST request to update Chuong : {}", chuongDTO);
        if (chuongDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChuongDTO result = chuongService.save(chuongDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chuongDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /chuongs} : get all the chuongs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chuongs in body.
     */
//    @GetMapping("/chuongs")
//    public ResponseEntity<List<ChuongDTO>> getAllChuongs(ChuongCriteria criteria, Pageable pageable) {
//        log.debug("REST request to get Chuongs by criteria: {}", criteria);
//        Page<ChuongDTO> page = chuongQueryService.findByCriteria(criteria, pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//        return ResponseEntity.ok().headers(headers).body(page.getContent());
//    }


    /**
     * {@code GET  /chuongs/:id} : get the "id" chuong.
     *
     * @param id the id of the chuongDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chuongDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chuongs/{id}")
    public ResponseEntity<ChuongDTO> getChuong(@PathVariable Long id) {
        log.debug("REST request to get Chuong : {}", id);
        Optional<ChuongDTO> chuongDTO = chuongService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chuongDTO);
    }

    /**
     * {@code DELETE  /chuongs/:id} : delete the "id" chuong.
     *
     * @param id the id of the chuongDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chuongs/{id}")
    public ResponseEntity<Void> deleteChuong(@PathVariable Long id) {
        log.debug("REST request to delete Chuong : {}", id);
        chuongService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
