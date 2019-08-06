package com.demo.dao;

import com.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(value = "SELECT * from t_user a,t_user_role b,t_role c WHERE a.account = :account AND a.id = b.user_id AND b.role_id =c.id",nativeQuery = true)
    User findUserByAccount(@Param("account") String account);

    @Query(value = "SELECT * from t_user a,t_user_role b,t_role c WHERE (a.account = :account OR a.phone = :phone OR  a.user_name = :user_name) AND a.id = b.user_id AND b.role_id =c.id",nativeQuery = true)
    User findUserByKeyWord(@Param("account") String account,@Param("phone") String phone,@Param("user_name") String user_name);

    @Query(value = "SELECT c.role_name from t_user a,t_user_role b,t_role c WHERE a.account = :account AND a.id = b.user_id AND b.role_id =c.id",nativeQuery = true)
    List<String> findRoleNameByAccount(@Param("account") String account);

}
