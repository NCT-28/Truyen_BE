package vn.com.truyen.usermanagent.repository;

import vn.com.truyen.usermanagent.domain.GroupTranslate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the GroupTranslate entity.
 */
@Repository
public interface GroupTranslateRepository extends JpaRepository<GroupTranslate, Long>, JpaSpecificationExecutor<GroupTranslate> {

    @Query(value = "select distinct groupTranslate from GroupTranslate groupTranslate left join fetch groupTranslate.users left join fetch groupTranslate.roles",
        countQuery = "select count(distinct groupTranslate) from GroupTranslate groupTranslate")
    Page<GroupTranslate> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct groupTranslate from GroupTranslate groupTranslate left join fetch groupTranslate.users left join fetch groupTranslate.roles")
    List<GroupTranslate> findAllWithEagerRelationships();

    @Query("select groupTranslate from GroupTranslate groupTranslate left join fetch groupTranslate.users left join fetch groupTranslate.roles where groupTranslate.id =:id")
    Optional<GroupTranslate> findOneWithEagerRelationships(@Param("id") Long id);
}
