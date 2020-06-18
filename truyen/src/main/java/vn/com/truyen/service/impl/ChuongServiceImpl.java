package vn.com.truyen.service.impl;

import vn.com.truyen.service.ChuongService;
import vn.com.truyen.domain.Chuong;
import vn.com.truyen.repository.ChuongRepository;
import vn.com.truyen.service.dto.ChuongDTO;
import vn.com.truyen.service.mapper.ChuongMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Chuong}.
 */
@Service
@Transactional
public class ChuongServiceImpl implements ChuongService {

    private final Logger log = LoggerFactory.getLogger(ChuongServiceImpl.class);

    private final ChuongRepository chuongRepository;

    private final ChuongMapper chuongMapper;

    public ChuongServiceImpl(ChuongRepository chuongRepository, ChuongMapper chuongMapper) {
        this.chuongRepository = chuongRepository;
        this.chuongMapper = chuongMapper;
    }

    /**
     * Save a chuong.
     *
     * @param chuongDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ChuongDTO save(ChuongDTO chuongDTO) {
        log.debug("Request to save Chuong : {}", chuongDTO);
        Chuong chuong = chuongMapper.toEntity(chuongDTO);
        chuong = chuongRepository.save(chuong);
        return chuongMapper.toDto(chuong);
    }

    /**
     * Get all the chuongs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ChuongDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Chuongs");
        return chuongRepository.findAll(pageable)
            .map(chuongMapper::toDto);
    }


    /**
     * Get one chuong by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ChuongDTO> findOne(Long id) {
        log.debug("Request to get Chuong : {}", id);
        return chuongRepository.findById(id)
            .map(chuongMapper::toDto);
    }

    /**
     * Delete the chuong by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Chuong : {}", id);
        chuongRepository.deleteById(id);
    }
}
