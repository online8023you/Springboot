package com.demo.service;

import com.demo.entity.User;

import java.util.List;

public interface UserService {

    User insertNewUser(String user_name, String account, String password, String phone, Integer age,List<Integer> role_ids,Integer status);

    void deleteUserById(Integer id);

    User updateUserById(Integer id,String user_name, String account, String password, String phone, Integer age,List<Integer> role_ids);

    List<User> findAllUser();

    User findUserById(Integer id);

    Boolean userLogin(String account, String password);

    User findUserByAccount(String account);

    User findUserByKeyword(String account,String phone,String user_name);

    List<String> findRoleNameByAccount(String account);

}
