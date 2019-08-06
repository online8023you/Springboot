package com.demo.service;

import com.demo.dao.RoleRepository;
import com.demo.dao.UserRepository;
import com.demo.entity.Authority;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.ArrayList;
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


}
