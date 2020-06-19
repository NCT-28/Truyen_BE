package vn.com.truyen.service.impl;

import vn.com.truyen.service.CategoryService;
import vn.com.truyen.domain.Author;
import vn.com.truyen.domain.Category;
import vn.com.truyen.domain.Truyen;
import vn.com.truyen.repository.CategoryRepository;
import vn.com.truyen.repository.TruyenRepository;
import vn.com.truyen.service.dto.CategoryDTO;
import vn.com.truyen.service.mapper.CategoryMapper;
import vn.com.truyen.service.mess.AuthorMess;
import vn.com.truyen.service.mess.CategoryMess;
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

import liquibase.pro.packaged.ca;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Category}.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;
    private final TruyenRepository truyenRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, TruyenRepository truyenRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.truyenRepository=truyenRepository;
    }

    /**
     * Save a category.
     *
     * @param categoryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        log.debug("Request to save Category : {}", categoryDTO);
        Category category = categoryMapper.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    /**
     * Get all the categories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Categories");
        return categoryRepository.findAll(pageable)
            .map(categoryMapper::toDto);
    }

    /**
     * 
     */
    @Override
	public CategoryMess findAllCategorys(Integer pageNo, Integer pageSize, String name, String sortType,
			String sortBy) {
    	Pageable pageable = null;
		if (sortType.equals("ASC")) {
			pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Direction.ASC, sortBy));
		} else if (sortType.equals("DESC")) {
			pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Direction.DESC, sortBy));
		}
		Page<Category> pageCategorys= categoryRepository.findAllCategorys(pageable, name);
		CategoryMess categoryMess= new CategoryMess();
		categoryMess.setMessage("get all category sussess!");
		categoryMess.setTotalCategorys(pageCategorys.getTotalElements());
		categoryMess.setListCategorys(pageCategorys.getContent());
		return categoryMess;
	}
    
    /**
     * 
     */
    @Override
	public TruyenMess findAllTruyenbyCategoryId(Integer pageNo, Integer pageSize,Long id, String name, String sortBy) {
    	Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		Page<Truyen> pageTruyen = truyenRepository.findAlltruyenByCategoryId(pageable, id, name, "tr."+sortBy);

		TruyenMess truyenMess = new TruyenMess();
		truyenMess.setMessage("get all truyen by author id " + id + " success!");
		truyenMess.setListTruyens(pageTruyen.getContent());
		truyenMess.setTotalTruyens(pageTruyen.getTotalElements());
		return truyenMess;
	}
//    /**
//     * Get all the categories with eager load of many-to-many relationships.
//     *
//     * @return the list of entities.
//     */
//    public Page<CategoryDTO> findAllWithEagerRelationships(Pageable pageable) {
//        return categoryRepository.findAllWithEagerRelationships(pageable).map(categoryMapper::toDto);
//    }

    /**
     * Get one category by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
//    @Override
//    @Transactional(readOnly = true)
//    public Optional<CategoryDTO> findOne(Long id) {
//        log.debug("Request to get Category : {}", id);
//        return categoryRepository.findOneWithEagerRelationships(id)
//            .map(categoryMapper::toDto);
//    }

    /**
     * Delete the category by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Category : {}", id);
        categoryRepository.deleteById(id);
    }

	

	
}
