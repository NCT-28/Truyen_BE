package vn.com.truyen.repository;

import vn.com.truyen.domain.View;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the View entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ViewRepository extends JpaRepository<View, Long>, JpaSpecificationExecutor<View> {
}
