package vn.com.truyen.repository;

import vn.com.truyen.domain.View;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the View entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ViewRepository extends JpaRepository<View, Long>, JpaSpecificationExecutor<View> {
	
	@Query(value = "select vie.* from View vie inner join Truyen tr ON vie.truyen_id = tr.id where tr.id = :id order by :sortBy", nativeQuery = true )
	Page<View> findAllViewByTruyenId(Pageable pageable, @Param("id") Long id, @Param("sortBy") String sortBy);
}
