package vn.com.truyen.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import vn.com.truyen.service.TruyenService;
import vn.com.truyen.service.dto.TruyenDTO;
import vn.com.truyen.service.mess.AuthorMess;
import vn.com.truyen.service.mess.ChuongMess;
import vn.com.truyen.service.mess.FeedbackMess;
import vn.com.truyen.service.mess.TruyenMess;
import vn.com.truyen.service.mess.ViewMess;
import vn.com.truyen.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.com.truyen.domain.Truyen}.
 */
@RestController
@RequestMapping("/api")
public class TruyenResource {

    private final Logger log = LoggerFactory.getLogger(TruyenResource.class);

    private static final String ENTITY_NAME = "truyenTruyen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TruyenService truyenService;


    public TruyenResource(TruyenService truyenService) {
        this.truyenService = truyenService;
    }

    /**
     * {@code POST  /truyens} : Create a new truyen.
     *
     * @param truyenDTO the truyenDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new truyenDTO, or with status {@code 400 (Bad Request)} if the truyen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/truyens")
    public ResponseEntity<TruyenDTO> createTruyen(@Valid @RequestBody TruyenDTO truyenDTO) throws URISyntaxException {
        log.debug("REST request to save Truyen : {}", truyenDTO);
        if (truyenDTO.getId() != null) {
            throw new BadRequestAlertException("A new truyen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TruyenDTO result = truyenService.save(truyenDTO);
        return ResponseEntity.created(new URI("/api/truyens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /truyens} : Updates an existing truyen.
     *
     * @param truyenDTO the truyenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated truyenDTO,
     * or with status {@code 400 (Bad Request)} if the truyenDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the truyenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/truyens")
    public ResponseEntity<TruyenDTO> updateTruyen(@Valid @RequestBody TruyenDTO truyenDTO) throws URISyntaxException {
        log.debug("REST request to update Truyen : {}", truyenDTO);
        if (truyenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TruyenDTO result = truyenService.save(truyenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, truyenDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /truyens} : get all the truyens.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of truyens in body.
     */
    @GetMapping("/truyens")
    public ResponseEntity<TruyenMess> getAllTruyens(
    		@RequestParam(defaultValue = "1") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, 
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "ASC") String sortType, 
			@RequestParam(defaultValue = "name") String sortBy) {
        log.debug("REST request to get Truyens!");
        TruyenMess truyenMess = truyenService.findAllTruyens(pageNo, pageSize, name, sortType, sortBy);

		return new ResponseEntity<>(truyenMess, new HttpHeaders(), HttpStatus.OK);
	}


    /**
     * {@code GET  /truyens/:id} : get the "id" truyen.
     *
     * @param id the id of the truyenDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the truyenDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/truyens/{id}")
    public ResponseEntity<TruyenDTO> getTruyen(@PathVariable Long id) {
        log.debug("REST request to get Truyen : {}", id);
        Optional<TruyenDTO> truyenDTO = truyenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(truyenDTO);
    }
    
    /**
     * 
     * @param id
     * @return
     */
    
    @GetMapping("/truyens/{id}/chuongs")
    public ResponseEntity<ChuongMess> getAllChuongByTruyen(
    		@PathVariable Long id,
    		@RequestParam(defaultValue = "1") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, 
			@RequestParam(defaultValue = "name") String sortBy) {
        log.debug("REST request to get Truyen : {}", id);
        ChuongMess chuongMess=  truyenService.findAllChuongByTruyenId(pageNo, pageSize, id, sortBy);
        return new ResponseEntity<ChuongMess>(chuongMess, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * 
     * @param id
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @return
     */
    @GetMapping("/truyens/{id}/views")
    public ResponseEntity<ViewMess> getAllViewByTruyen(
    		@PathVariable Long id,
    		@RequestParam(defaultValue = "1") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, 
			@RequestParam(defaultValue = "name") String sortBy) {
        log.debug("REST request to get Truyen : {}", id);
       ViewMess viewMess = truyenService.findAllViewByTruyenId(pageNo, pageSize, id, sortBy);
        return new ResponseEntity<>(viewMess, new HttpHeaders(), HttpStatus.OK);
    }
    /**
     * 
     * @param id
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @return
     */
    @GetMapping("/truyens/{id}/feedbacks")
    public ResponseEntity<FeedbackMess> getAllFeedbackByTruyen(
    		@PathVariable Long id,
    		@RequestParam(defaultValue = "1") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, 
			@RequestParam(defaultValue = "name") String sortBy) {
        log.debug("REST request to get Truyen : {}", id);
       FeedbackMess feedbackMess = truyenService.findAllFeedbackByTruyenId(pageNo, pageSize, id, sortBy);
        return new ResponseEntity<>(feedbackMess, new HttpHeaders(), HttpStatus.OK);
    }
    /**
     * {@code DELETE  /truyens/:id} : delete the "id" truyen.
     *
     * @param id the id of the truyenDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/truyens/{id}")
    public ResponseEntity<Void> deleteTruyen(@PathVariable Long id) {
        log.debug("REST request to delete Truyen : {}", id);
        truyenService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
