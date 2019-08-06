package com.demo.controller;

import com.demo.entity.User;
import com.demo.service.AuthorityService;
import com.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "用户管理")
@RestController
public class UserController {
    /*@ApiParam(required = true,name = "",value = "")*/
    @Autowired
    UserService userService;

    @Autowired
    AuthorityService authorityService;

    @ApiOperation("用户登录")
    @ResponseBody
    @GetMapping(value = "/login")
    public Map<String, Object> userLogin(HttpServletRequest request,
                             @ApiParam(required = true,name = "account",value = "账户")@RequestParam("account")String account,
                             @ApiParam(required = true,name = "password",value = "密码") @RequestParam("password")String password){
        String msg="";
        Map<String, Object> map = new HashMap<>();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(account,password);
            char[] thePasswordChar = token.getPassword();
            String thePassword = thePasswordChar.toString();
            SecurityUtils.getSubject().login(token);
            token.setRememberMe(true);
            map.put("status",200);
            request.getSession().setAttribute("account",token.getUsername());
        }catch (UnknownAccountException e){
            msg = "UnknownAccountException -- > 账号不存在：";
            map.put("status","异常：账号不存在");
        }catch (IncorrectCredentialsException e){
            msg = "IncorrectCredentialsException -- > 密码不正确：";
            map.put("status","异常：密码不正确");
        }catch (LockedAccountException e){
            msg = "LockedAccountException -- > 账号被锁定：";
            map.put("status","异常：账号被锁定");
        }catch (Exception exception){
            msg = "else >> "+exception;
            System.out.println("else -- >" + exception);
        }

        return map;
    }

    @ApiOperation("用户信息")
    @ResponseBody
    @GetMapping(value = "/user/get")
    public Map<String,Object> userGet(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();

        map.put("account",request.getSession().getAttribute("account"));
        return map;
    }

    @ApiOperation("用户登出")
    @ResponseBody
    @GetMapping(value = "/logout")
    public String userLogout(HttpServletRequest request){

        return "0";
    }

    @RequiresPermissions(value = {"/menu_user","/user/add","/menu"},logical = Logical.OR)
    @ApiOperation("新增一个用户")
    @PostMapping(value = "user")
    public User insertNewUser(@ApiParam(required = true,name = "user_name",value = "用户名")@RequestParam("user_name") String user_name,
                              @ApiParam(required = true,name = "account",value = "帐号")@RequestParam("account") String account,
                              @ApiParam(required = true,name = "password",value = "密码")@RequestParam("password")String password,
                              @ApiParam(required = true,name = "phone",value = "电话")@RequestParam("phone") String phone,
                              @ApiParam(required = true,name = "age",value = "年龄")@RequestParam("age") Integer age,
                              @ApiParam(required = true,name = "role_id",value = "角色id")@RequestParam("role_id") List<Integer> role_ids,
                                          @ApiParam(required = true,name = "status",value = "角色状态")@RequestParam("status")Integer status  ) {

        return userService.insertNewUser(user_name, account,password, phone, age, role_ids,status);
    }

    @RequiresPermissions(value = {"/menu_user","/user/delete","/menu"},logical = Logical.OR)
    @ApiOperation("根据id删除一个用户")
    @DeleteMapping(value = "user/{id}")
    public void deleteUserById(@ApiParam(required = true,name = "id",value = "用户id")@PathVariable("id") Integer id) {
        userService.deleteUserById(id);
    }

    @RequiresPermissions(value = {"/menu_user","/user/update","/menu"},logical = Logical.OR)
    @ApiOperation("根据id修改一个用户")
    @PutMapping(value = "user/{id}")
    public User updateUserById(@ApiParam(required = true,name = "id",value = "id")@PathVariable("id") Integer id,
                               @ApiParam(required = true,name = "user_name",value = "用户名")@RequestParam("user_name") String user_name,
                               @ApiParam(required = true,name = "account",value = "帐号")@RequestParam("account") String account,
                               @ApiParam(required = true,name = "password",value = "密码")@RequestParam("password")String password,
                               @ApiParam(required = true,name = "phone",value = "电话")@RequestParam("phone") String phone,
                               @ApiParam(required = true,name = "age",value = "年龄")@RequestParam("age") Integer age,
                               @ApiParam(required = true,name = "role_id",value = "角色id")@RequestParam("role_id") List<Integer> role_ids) {
        return userService.updateUserById(id, user_name, account,password, phone, age, role_ids);
    }

    @RequiresPermissions(value = {"/menu_user","/user/find","/menu"},logical = Logical.OR)
    @ApiOperation("查找所有用户")
    @GetMapping(value = "users")
    public List<User> findAllUser() {
        return userService.findAllUser();
    }

    @RequiresPermissions(value = {"/menu_user","/user/find","/menu"},logical = Logical.OR)
    @ApiOperation("根据id查找用户")
    @GetMapping(value = "/user/{id}")
    public User findUserById(@ApiParam(required = true,name = "id",value = "id")@PathVariable("id") Integer id) {
        return userService.findUserById(id);
    }

    @RequiresPermissions(value = {"/menu_user","/user/find","/menu"},logical = Logical.OR)
    @ApiOperation("根据帐号查找用户")
    @GetMapping(value = "/user/account")
    public User findUserByAccount(@ApiParam(required = true,name = "account",value = "帐号")@RequestParam("account") String account){
        return userService.findUserByAccount(account);
    }


    @RequiresPermissions(value = {"/menu_user","/user/find","/menu"},logical = Logical.OR)
    @ApiOperation("根据关键词查找角色")
    @GetMapping(value = "/user/keyword")
    public User findUserByKeyword(@ApiParam(required = true,name = "account",value = "帐号")@RequestParam("account")String account,
                                  @ApiParam(required = true,name = "phone",value = "电话")@RequestParam("phone")String phone,
                                  @ApiParam(required = true,name = "user_name",value = "用户名")@RequestParam("user_name")String user_name){
        return userService.findUserByKeyword(account, phone, user_name);
    }

}
