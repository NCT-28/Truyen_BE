package vn.com.truyen.repository;

import vn.com.truyen.domain.Chuong;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Chuong entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChuongRepository extends JpaRepository<Chuong, Long>, JpaSpecificationExecutor<Chuong> {
}
