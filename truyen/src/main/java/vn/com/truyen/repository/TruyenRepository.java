package vn.com.truyen.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.com.truyen.domain.Category;
import vn.com.truyen.domain.Truyen;



/**
 * Spring Data  repository for the Truyen entity.
 */
@Repository
public interface TruyenRepository extends JpaRepository<Truyen, Long>, JpaSpecificationExecutor<Truyen> {
	
	@Query(value = " select tr from Truyen tr Where tr.name LIKE %:name%")
	Page<Truyen> findAlltruyens(Pageable pageable, @Param("name") String name);
	
	@Query(value = " Select tr.* FROM (author au INNER JOIN truyen tr ON au.id = tr.author_id)"
			+ " WHERE au.id= :id "
			+ " and tr.name LIKE %:name% "
			+ " ORDER By :sortBy ", nativeQuery = true)
	
	Page<Truyen> findAllTruyenByAuthorId(Pageable pageable, @Param("id") Long id, @Param("name") String name, @Param("sortBy") String sortBy);
	
	
	@Query(value = "Select tr.* from ((category ca inner join category_truyen catr on ca.id = catr.category_id) " 
			+ " inner join truyen tr ON catr.truyen_id = tr.id) " 
			+ " where ca.id = :id "
			+ " and tr.name LIKE %:name% "
			+ " ORDER By :sortBy ", nativeQuery = true)
    Page<Truyen> findAlltruyenByCategoryId(Pageable pageable, @Param("id") Long id, @Param("name") String name, @Param("sortBy") String sortBy);

}
