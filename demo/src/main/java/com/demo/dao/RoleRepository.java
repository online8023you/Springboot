package com.demo.dao;

import com.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    @Query(value = "SELECT * FROM t_role WHERE parent_role_id = :parent_role_id",nativeQuery = true)
    List<Role> findRolesByParentId(@Param("parent_role_id")Integer parent_role_id);

    @Query(value = "SELECT * FROM t_role WHERE parent_role_id IS NULL",nativeQuery = true)
    List<Role> findAllRole();

    @Query(value = "SELECT * FROM(SELECT * FROM t_role WHERE parent_role_id > 0 ORDER BY parent_role_id, id DESC) realname_sorted,(SELECT @pv \\:= :id) initialisation WHERE (FIND_IN_SET(parent_role_id,@pv)>0 AND @pv \\:= CONCAT(@pv, ',', id)) ORDER BY id ",nativeQuery = true)
    List<Role> findChildRolesById(@Param("id")Integer id);

}
