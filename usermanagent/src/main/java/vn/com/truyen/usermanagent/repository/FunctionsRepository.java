package vn.com.truyen.usermanagent.repository;

import vn.com.truyen.usermanagent.domain.Functions;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Functions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FunctionsRepository extends JpaRepository<Functions, Long>, JpaSpecificationExecutor<Functions> {
}
