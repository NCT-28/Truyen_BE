package vn.com.truyen.repository;

import vn.com.truyen.domain.Author;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Author entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {
	
	@Query(" SELECT au FROM Author au WHERE au.name LIKE %:name% ")
	Page<Author> findAllAuthors(Pageable pageable, @Param("name") String name);
	
}
