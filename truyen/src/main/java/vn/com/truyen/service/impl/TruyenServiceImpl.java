package vn.com.truyen.service.impl;

import vn.com.truyen.service.TruyenService;
import vn.com.truyen.domain.Author;
import vn.com.truyen.domain.Chuong;
import vn.com.truyen.domain.Feedback;
import vn.com.truyen.domain.Truyen;
import vn.com.truyen.domain.View;
import vn.com.truyen.repository.ChuongRepository;
import vn.com.truyen.repository.FeedbackRepository;
import vn.com.truyen.repository.TruyenRepository;
import vn.com.truyen.repository.ViewRepository;
import vn.com.truyen.service.dto.TruyenDTO;
import vn.com.truyen.service.mapper.TruyenMapper;
import vn.com.truyen.service.mess.AuthorMess;
import vn.com.truyen.service.mess.ChuongMess;
import vn.com.truyen.service.mess.FeedbackMess;
import vn.com.truyen.service.mess.TruyenMess;
import vn.com.truyen.service.mess.ViewMess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Truyen}.
 */
@Service
@Transactional
public class TruyenServiceImpl implements TruyenService {

    private final Logger log = LoggerFactory.getLogger(TruyenServiceImpl.class);

    private final TruyenRepository truyenRepository;

    private final TruyenMapper truyenMapper;
    
    private final ChuongRepository chuongRepository;
    
    private final ViewRepository viewRepository;
    
    private final FeedbackRepository feedbackRepository;
    
  

    public TruyenServiceImpl(TruyenRepository truyenRepository, TruyenMapper truyenMapper,
    		ChuongRepository chuongRepository, ViewRepository viewRepository, FeedbackRepository feedbackRepository) {
        this.truyenRepository = truyenRepository;
        this.truyenMapper = truyenMapper;
        this.chuongRepository=chuongRepository;
        this.viewRepository=viewRepository;
        this.feedbackRepository=feedbackRepository;
    }

    /**
     * Save a truyen.
     *
     * @param truyenDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TruyenDTO save(TruyenDTO truyenDTO) {
        log.debug("Request to save Truyen : {}", truyenDTO);
        Truyen truyen = truyenMapper.toEntity(truyenDTO);
        truyen = truyenRepository.save(truyen);
        return truyenMapper.toDto(truyen);
    }

    /**
     * Get all the truyens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
//    @Override
//    @Transactional(readOnly = true)
//    public Page<TruyenDTO> findAll(Pageable pageable) {
//        log.debug("Request to get all Truyens");
//        return truyenRepository.findAll(pageable)
//            .map(truyenMapper::toDto);
//    }

    /**
     * 
     */
    @Override
	public TruyenMess findAllTruyens(Integer pageNo, Integer pageSize, String name, String sortType, String sortBy) {
    	Pageable pageable = null;
		if (sortType.equals("ASC")) {
			pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Direction.ASC, sortBy));
		} else if (sortType.equals("DESC")) {
			pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Direction.DESC, sortBy));
		}
		Page<Truyen> enties = truyenRepository.findAlltruyens(pageable, name);
		TruyenMess truyenMess = new TruyenMess();
		truyenMess.setListTruyens(enties.getContent());
		truyenMess.setTotalTruyens(enties.getTotalElements());
		truyenMess.setMessage("get all truyen success!");
		return truyenMess;
	}
    
    
    @Override
	public ChuongMess findAllChuongByTruyenId(Integer pageNo, Integer pageSize,Long id, String sortBy) {
    	Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		Page<Chuong> pageTruyen = chuongRepository.findAllChuongByTruyenId(pageable, id, "ch."+sortBy);

		ChuongMess chuongMess = new ChuongMess();
		chuongMess.setMessage("get all chuong by truyen id " + id + " success!");
		chuongMess.setTotalChuongs(pageTruyen.getTotalElements());
		chuongMess.setListChuongs(pageTruyen.getContent());
		return chuongMess;
	}
    
    

	@Override
	public ViewMess findAllViewByTruyenId(Integer pageNo, Integer pageSize, Long id, String sortBy) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		Page<View> enties=viewRepository.findAllViewByTruyenId(pageable, id, "vie."+sortBy);
		
		ViewMess viewMess = new ViewMess();
		viewMess.setMessage("get all chuong by truyen id " + id + " success!");
		viewMess.setListViews(enties.getContent());
		viewMess.setTotalViews(enties.getTotalElements());
		return viewMess;
	}
	
	@Override
	public FeedbackMess findAllFeedbackByTruyenId(Integer pageNo, Integer pageSize, Long id, String sortBy) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		Page<Feedback> enties=feedbackRepository.findAllFeedbackByTruyenId(pageable, id, sortBy);
		
		FeedbackMess feedbackMess = new FeedbackMess();
		feedbackMess.setListFedbacks(enties.getContent());
		feedbackMess.setTotalFeedbacks(enties.getTotalElements());
		feedbackMess.setMessage("get all feedback by truyen "+ id+ " success!");
		return feedbackMess;
	}


    /**
     * Get one truyen by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TruyenDTO> findOne(Long id) {
        log.debug("Request to get Truyen : {}", id);
        return truyenRepository.findById(id)
            .map(truyenMapper::toDto);
    }

    /**
     * Delete the truyen by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Truyen : {}", id);
        truyenRepository.deleteById(id);
    }

	
	

	
}
