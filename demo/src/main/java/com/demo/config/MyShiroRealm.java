package com.demo.config;

import com.demo.dao.AuthorityRepository;
import com.demo.dao.RoleRepository;
import com.demo.dao.UserRepository;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

/**
 * @author Dawn
 * @ClassName com.demo.config.MyShrioRealm
 * @Description
 * @date 2019/7/11 16:03
 */


public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthorityRepository authorityRepository;




    /**
     * 获取身份验证信息
     * Shiro中，最终是通过Realm来获取应用程序中的用户、角色及权限信息的
     * @param authenticationToken
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        System.out.println("-----------------身份认证方法--------------");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userRepository.findUserByAccount(token.getUsername());
        if (user==null){
            throw new UnknownAccountException("用户名不正确");
        }else if (!user.getPassword().equals(new String((char[])token.getCredentials()))){
            throw new IncorrectCredentialsException("密码不正确");
        }

        return new SimpleAuthenticationInfo(token.getPrincipal(),user.getPassword(),getName());
    }

    /**
     * 获取授权信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("-------权限认证---------");
        String account = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();


        /*获得该用户角色*/
        List<String> role_names = userRepository.findRoleNameByAccount(account);

        Set<String> roleSet = new HashSet<>();
        /*需要将 role 封装到 Set 作为 info.setRoles() 的参数*/
        roleSet.addAll(role_names);
        /*设置该用户拥有的角色*/
        info.setRoles(roleSet);

        /*设置权限*/
        List<Role> roles = userRepository.findUserByAccount(account).getRoles();
        Set<String> authoritySet = new HashSet<>();
        for (Role role:roles
             ) {
            List<String> authorities = authorityRepository.findAuthoritiesUrlByRoleId(role.getId());
            authoritySet.addAll(authorities);
        }



        info.setStringPermissions(authoritySet);
        return info;
    }
}
