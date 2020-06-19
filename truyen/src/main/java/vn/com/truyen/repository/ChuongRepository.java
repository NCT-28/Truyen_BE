package vn.com.truyen.repository;

import vn.com.truyen.domain.Chuong;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Chuong entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChuongRepository extends JpaRepository<Chuong, Long>, JpaSpecificationExecutor<Chuong> {
	
	@Query(value = "Select ch.* from Chuong ch inner join Truyen tr on ch.truyen_id =tr.id where tr.id = :id order by :sortBy", nativeQuery = true)
	Page<Chuong> findAllChuongByTruyenId(Pageable pageable, @Param("id") Long id, @Param("sortBy") String sortBy);
}
