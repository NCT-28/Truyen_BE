package vn.com.truyen.service.impl;

import vn.com.truyen.service.TruyenService;
import vn.com.truyen.domain.Truyen;
import vn.com.truyen.repository.TruyenRepository;
import vn.com.truyen.service.dto.TruyenDTO;
import vn.com.truyen.service.mapper.TruyenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public TruyenServiceImpl(TruyenRepository truyenRepository, TruyenMapper truyenMapper) {
        this.truyenRepository = truyenRepository;
        this.truyenMapper = truyenMapper;
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
    @Override
    @Transactional(readOnly = true)
    public Page<TruyenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Truyens");
        return truyenRepository.findAll(pageable)
            .map(truyenMapper::toDto);
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
