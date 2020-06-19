package vn.com.truyen.service.impl;

import vn.com.truyen.service.AuthorService;
import vn.com.truyen.domain.Author;
import vn.com.truyen.domain.Truyen;
import vn.com.truyen.repository.AuthorRepository;
import vn.com.truyen.repository.TruyenRepository;
import vn.com.truyen.service.dto.AuthorDTO;
import vn.com.truyen.service.mapper.AuthorMapper;
import vn.com.truyen.service.mess.AuthorMess;
import vn.com.truyen.service.mess.TruyenMess;

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
 * Service Implementation for managing {@link Author}.
 */
@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

	private final Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);

	private final AuthorRepository authorRepository;

	private final AuthorMapper authorMapper;

	private final TruyenRepository truyenRepository;

	public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper,
			TruyenRepository truyenRepository) {
		this.authorRepository = authorRepository;
		this.authorMapper = authorMapper;
		this.truyenRepository = truyenRepository;
	}

	/**
	 * Save a author.
	 *
	 * @param authorDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public AuthorDTO save(AuthorDTO authorDTO) {
		log.debug("Request to save Author : {}", authorDTO);
		Author author = authorMapper.toEntity(authorDTO);
		author = authorRepository.save(author);
		return authorMapper.toDto(author);
	}

	/**
	 * Get all the authors.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
//    @Override
//    @Transactional(readOnly = true)
//    public Page<AuthorDTO> findAll(Pageable pageable) {
//        log.debug("Request to get all Authors");
//        return authorRepository.findAll(pageable)
//            .map(authorMapper::toDto);
//    }
	@Override
	public AuthorMess getAllAuthors(Integer pageNo, Integer pageSize, String name, String sortType, String sortBy) {
		Pageable pageable = null;
		if (sortType.equals("ASC")) {
			pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Direction.ASC, sortBy));
		} else if (sortType.equals("DESC")) {
			pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Direction.DESC, sortBy));
		}
		Page<Author> enties = authorRepository.findAllAuthors(pageable, name);
		AuthorMess authorMess = new AuthorMess();
		authorMess.setListAuthors(enties.getContent());
		authorMess.setMessage("Get all authors success!");
		authorMess.setTotalAuthors(enties.getTotalElements());
		return authorMess;
	}

	/**
	 * 
	 * 
	 */

	@Override
	public TruyenMess getAllTruyenOfAuthorId(Integer pageNo, Integer pageSize, Long id, String name, String sortBy) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		Page<Truyen> pageTruyen = truyenRepository.findAllTruyenByAuthorId(pageable, id, name, "tr." + sortBy);

		TruyenMess truyenMess = new TruyenMess();
		truyenMess.setMessage("get all truyen by author id " + id + " success!");
		truyenMess.setListTruyens(pageTruyen.getContent());
		truyenMess.setTotalTruyens(pageTruyen.getTotalElements());
		return truyenMess;
	}

	/**
	 * Get one author by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<AuthorDTO> findOne(Long id) {
		log.debug("Request to get Author : {}", id);
		return authorRepository.findById(id).map(authorMapper::toDto);
	}

	/**
	 * Delete the author by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Author : {}", id);
		authorRepository.deleteById(id);
	}

}
