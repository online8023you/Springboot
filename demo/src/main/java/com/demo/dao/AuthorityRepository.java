package com.demo.dao;

import com.demo.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    /*@Query(value = "select c.* FROM t_role a,t_role_authority b, t_authority c WHERE a.id = :id AND a.id = b.role_id AND b.authority_id = c.id AND (c.status between 100 and 1000)", nativeQuery = true)
    List<Authority> findParentAuthorityByRoleId(@Param("id") Integer id);*/

    @Query(value = "SELECT * FROM(SELECT * FROM t_authority WHERE parent_authority_id > 0 ORDER BY parent_authority_id, id DESC) realname_sorted,(SELECT @pv \\:= :authority_id) initialisation WHERE (FIND_IN_SET(parent_authority_id,@pv)>0 AND @pv \\:= CONCAT(@pv, ',', id)) ORDER BY id ",nativeQuery = true)
    List<Authority> findChildAuthoritiesById(@Param("authority_id")Integer authority_id);

    @Query(value = "SELECT * FROM t_authority WHERE parent_authority_id = :parent_authority_id",nativeQuery = true)
    List<Authority> findAuthoritiesByParentId(@Param("parent_authority_id")Integer parent_authority_id);

    @Query(value = "SELECT * FROM t_authority WHERE parent_authority_id IS NULL",nativeQuery = true)
    List<Authority> findAllAuthority();

    @Query(value = "SELECT b.url FROM t_role a,t_authority b,t_role_authority c WHERE a.id = c.role_id AND c.authority_id = b.id AND a.id= :role_id ",nativeQuery = true)
    List<String> findAuthoritiesUrlByRoleId(@Param("role_id")Integer role_id);
}
