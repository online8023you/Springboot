package com.demo.service;

import com.demo.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface UserService {

    User insertNewUser(String user_name, String account, String password, String phone, Integer age, List<Integer> role_ids, Integer status);

    void deleteUserById(Integer id);

    User updateUserById(Integer id, String user_name, String account, String password, String phone, Integer age, List<Integer> role_ids);

    List<User> findAllUser();

    User findUserById(Integer id);

    Boolean userLogin(String account, String password);

    User findUserByAccount(String account);

    User findUserByKeyword(String account, String phone, String user_name);

    List<String> findRoleNameByAccount(String account);

    List<User> readUserExcel(MultipartFile filePath) throws IOException;

    void writeUserExcel(HttpServletResponse response) throws IOException;

    User insertUserExcel(Integer id, String user_name, String account, String password, String phone, Integer age, List<Integer> role_ids, Integer status, Date create_time, Date update_time);

}
