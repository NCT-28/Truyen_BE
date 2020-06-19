package vn.com.truyen.repository;

import vn.com.truyen.domain.Feedback;
import vn.com.truyen.domain.View;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Feedback entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
	
	@Query(value = "select fe.* from Feedback fe inner join Truyen tr ON fe.truyen_id = tr.id where tr.id = :id order by :sortBy", nativeQuery = true )
	Page<Feedback> findAllFeedbackByTruyenId(Pageable pageable, @Param("id") Long id, @Param("sortBy") String sortBy);
	
}
