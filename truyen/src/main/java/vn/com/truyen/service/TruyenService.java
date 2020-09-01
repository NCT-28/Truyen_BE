package vn.com.truyen.service;

import vn.com.truyen.domain.Feedback;
import vn.com.truyen.service.dto.TruyenDTO;

import vn.com.truyen.service.mess.ChuongMess;
import vn.com.truyen.service.mess.FeedbackMess;
import vn.com.truyen.service.mess.TruyenMess;
import vn.com.truyen.service.mess.ViewMess;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link vn.com.truyen.domain.Truyen}.
 */
public interface TruyenService {

	/**
	 * Save a truyen.
	 *
	 * @param truyenDTO the entity to save.
	 * @return the persisted entity.
	 */
	TruyenDTO save(TruyenDTO truyenDTO);

	/**
	 * Get all the truyens.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	// Page<TruyenDTO> findAll(Pageable pageable);

	TruyenMess findAllTruyens(Integer pageNo, Integer pageSize, String name, String sortType, String sortBy);

	ChuongMess findAllChuongByTruyenId(Integer pageNo, Integer pageSize,Long id, String sortBy);
	
	ViewMess findAllViewByTruyenId(Integer pageNo, Integer pageSize,Long id, String sortBy);
	
	FeedbackMess findAllFeedbackByTruyenId(Integer pageNo, Integer pageSize,Long id, String sortBy);
	
	/**
	 * Get the "id" truyen.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<TruyenDTO> findOne(Long id);

	/**
	 * Delete the "id" truyen.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);
}
