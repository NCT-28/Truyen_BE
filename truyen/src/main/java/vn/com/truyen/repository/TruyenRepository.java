package vn.com.truyen.repository;

import vn.com.truyen.domain.Truyen;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Truyen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TruyenRepository extends JpaRepository<Truyen, Long>, JpaSpecificationExecutor<Truyen> {
}
