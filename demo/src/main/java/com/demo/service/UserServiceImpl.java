package com.demo.service;

import com.alibaba.excel.EasyExcel;
import com.demo.dao.RoleRepository;
import com.demo.dao.UserRepository;
import com.demo.entity.Authority;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.excelListener.UserExcelListener;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Override
    public User insertNewUser(String user_name, String account, String password, String phone, Integer age, List<Integer> role_ids, Integer status) {

        List<Role> roles = new ArrayList<>();
        for (Integer role_id:role_ids
             ) {
        Role role = roleRepository.findById(role_id).orElse(null);
        roles.add(role);
        }
        User user = new User();
        user.setUser_name(user_name);
        user.setAccount(account);
        user.setPassword(password);
        user.setPhone(phone);
        user.setAge(age);
        user.setRoles(roles);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user.setCreat_time(timestamp);
        user.setUpdate_time(timestamp);
        user.setStatus(status);
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUserById(Integer id, String user_name, String account, String password, String phone, Integer age, List<Integer> role_ids) {
        List<Role> roles = new ArrayList<>();
        for (Integer role_id:role_ids
        ) {
            Role role = roleRepository.findById(role_id).orElse(null);
            roles.add(role);
        }
        User user = userRepository.findById(id).orElse(null);
        user.setUser_name(user_name);
        user.setAccount(account);
        user.setPassword(password);
        user.setPhone(phone);
        user.setAge(age);
        user.setRoles(roles);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user.setUpdate_time(timestamp);
        return userRepository.save(user);
    }

    @Override
    public List<User> findAllUser() {
        List<User> users = userRepository.findAll();

        return users;
    }

    @Override
    public User findUserById(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        /*Role role = roleService.findRoleById(user.getRole().getId());
        user.setRole(role);*/
        /*List<Authority> authorities = new ArrayList<>();
        for (int i = 0; i < role.getAuthorityList().size(); i++) {
            List<Authority> childAuthorities = new ArrayList<>();
            for (int j = 0; j < role.getAuthorityList().size(); j++) {
                if (role.getAuthorityList().get(j).getParent_authority() != null) {
                    if (role.getAuthorityList().get(i).getId() == role.getAuthorityList().get(j).getParent_authority().getId()) {
                        childAuthorities.add(role.getAuthorityList().get(j));
                    }

                } else {
                    continue;
                }
            }
            role.getAuthorityList().get(i).setChild_authorities(childAuthorities);
            if (!role.getAuthorityList().get(i).getChild_authorities().isEmpty()) {

                authorities.add(role.getAuthorityList().get(i));
            }
        }


        for (int i = 1; i < authorities.size(); i++) {
            if (authorities.get(i - 1).getId() == authorities.get(i).getParent_authority().getId()) {
                authorities.remove(authorities.get(i));
            }
        }
        role.setAuthorities(authorities);*/
        return user;
    }

    @Override
    public Boolean userLogin(String account, String password) {
        Boolean flag = false;
        User user = userRepository.findUserByAccount(account);
        if (user == null) {
        } else {
            if (user.getPassword().equals(password)) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public User findUserByAccount(String account) {
        return userRepository.findUserByAccount(account);
    }

    @Override
    public User findUserByKeyword(String account, String phone, String user_name) {
        return userRepository.findUserByKeyWord(account, phone, user_name);
    }

    @Override
    public List<String> findRoleNameByAccount(String account) {
        return userRepository.findRoleNameByAccount(account);
    }

    @Override
    public List<User> readUserExcel(MultipartFile filePath) throws IOException {
        UserExcelListener userExcelListener = new UserExcelListener();
        EasyExcel.read(filePath.getInputStream(), User.class, userExcelListener).sheet().doRead();
        List<User> users = userExcelListener.getRows();
        for (User user:users
             ) {
            String[] role_idStrArr = user.getRole_ids().split(" ");
            List<String> role_idStrList = Arrays.asList(role_idStrArr);
            List<Integer> role_idList = new ArrayList<>();
            for (String role_id:role_idStrList
                 ) {
                role_idList.add(Integer.parseInt(role_id));
            }
            insertUserExcel(user.getId(),user.getUser_name(),user.getAccount(),user.getPassword(),user.getPhone(),user.getAge(),role_idList,user.getStatus(),user.getCreat_time(),user.getUpdate_time());
        }
        return users;
    }

    @Override
    public void writeUserExcel(HttpServletResponse response) throws IOException {
        List<User> users = userRepository.findAll();
        List<User> userList = new ArrayList<>();
        for (User user:users
             ) {
            String role_ids = "";
            User userExcel = new User();
            for (Role role: user.getRoles()
                 ) {
                role_ids = role_ids+" "+role.getId();

            }
            userExcel.setId(user.getId());
            userExcel.setAccount(user.getAccount());
            userExcel.setPassword(user.getPassword());
            userExcel.setUser_name(user.getUser_name());
            userExcel.setAge(user.getAge());
            userExcel.setPhone(user.getPhone());
            userExcel.setStatus(user.getStatus());
            userExcel.setCreat_time(user.getCreat_time());
            userExcel.setUpdate_time(user.getUpdate_time());
            userExcel.setRole_ids(role_ids);
            userList.add(userExcel);
        }
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=user.xlsx");
        EasyExcel.write(response.getOutputStream(), User.class).sheet("role").doWrite(userList);
    }

    @Override
    public User insertUserExcel(Integer id, String user_name, String account, String password, String phone, Integer age, List<Integer> role_ids, Integer status, Date create_time, Date update_time) {
        List<Role> roles = new ArrayList<>();
        for (Integer role_id:role_ids
        ) {
            Role role = roleRepository.findById(role_id).orElse(null);
            roles.add(role);
        }
        User user = new User();
        if (id!=null){

            user.setId(id);
        }
        user.setUser_name(user_name);
        user.setAccount(account);
        user.setPassword(password);
        user.setPhone(phone);
        user.setAge(age);
        user.setRoles(roles);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (create_time==null){
            user.setCreat_time(timestamp);
        }else {
            user.setCreat_time(create_time);
        }
        if(update_time==null){
            user.setUpdate_time(timestamp);
        }else {
            user.setUpdate_time(update_time);
        }

        user.setStatus(status);
        return userRepository.save(user);
    }


}
