package com.demo.controller;

import com.demo.entity.Authority;
import com.demo.service.AuthorityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "权限管理")
@RestController
public class AuthorityController {

    /*@ApiParam(required = true,name = "",value = "")*/
    @Autowired
    AuthorityService authorityService;

    /*@RequiresPermissions("/authority/add")*/
    /*@ApiOperation("权限新增")
    @PostMapping(value = "/authority")
    public Authority insertNewAuthority(@ApiParam(required = true, name = "authority_name", value = "权限名") @RequestParam("authority_name") String authority_name,
                                        @ApiParam(required = true, name = "url", value = "请求路径") @RequestParam("url") String url,
                                        @ApiParam(required = true, name = "code", value = "编码") @RequestParam("code") String code,
                                        @ApiParam(required = true, name = "status", value = "状态") @RequestParam("status") Integer status,
                                        @ApiParam(required = true, name = "parent_authority_id", value = "父级权限id，无父级则为0") @RequestParam("parent_authority_id") Integer parent_authority_id) {
        return authorityService.insertNewAuthority(authority_name, url, code, status, parent_authority_id);

    }*/

   /* @RequiresPermissions("/authority/delete")*/
    /*@ApiOperation("根据id删除权限")
    @DeleteMapping(value = "/authority/{id}")
    public void deleteAuthorityById(@ApiParam(required = true,name = "id",value = "要删除的权限id")@PathVariable("id") Integer id) {
        authorityService.deleteAuthorityById(id);
    }*/

    @RequiresPermissions(value = {"/authority/update","/menu_authority","/menu"},logical = Logical.OR)
    @ApiOperation("根据id修改权限")
    @PutMapping(value = "/authority/{id}")
    public Authority updateAuthorityById(@ApiParam(required = true,name = "id",value = "要修改的权限id")@PathVariable("id") Integer id,
                                         @ApiParam(required = true,name = "authority_name",value = "权限名")@RequestParam("authority_name") String authority_name,
                                         @ApiParam(required = true,name = "url",value = "请求路径")@RequestParam("url") String url,
                                         @ApiParam(required = true,name = "code",value = "编码")@RequestParam("code") String code,
                                         @ApiParam(required = true,name = "status",value = "状态") @RequestParam("status") Integer status,
                                         @ApiParam(required = true,name = "parent_authority_id",value = "父级权限id，无父级则为0")@RequestParam("parent_authority_id") Integer parent_authority_id) {
        return authorityService.updateAuthorityById(id, authority_name, url, code, status, parent_authority_id);

    }

    /*@RequiresPermissions("/authority/find")*/
    @RequiresPermissions(value = {"/authority/find","/menu_authority","/menu"},logical = Logical.OR)
    @ApiOperation("查询所有权限")
    @GetMapping(value = "/authority")
    public List<Authority> findAllAuthority() {
        return authorityService.findAllAuthority();
    }

    /*@RequiresPermissions("/authority/find")*/
    @RequiresPermissions(value = {"/authority/find","/menu_authority","/menu"},logical = Logical.OR)
    @ApiOperation("根据id查询权限")
    @GetMapping(value = "/authority/{id}")
    public Authority findAuthorityById(@ApiParam(required = true,name = "id",value = "权限id")@PathVariable("id") Integer id) {
        return authorityService.findAuthorityById(id);
    }

   /* @ApiOperation("根据角色id查找权限")
    @GetMapping(value = "/authority/find/parent")
    public List<Authority> findParentAuthorityByRoleId(@RequestParam("user_id")Integer user_id){
        return authorityService.findParentAuthorityByRoleId(user_id);
    }*/


   /* @GetMapping(value = "/authority/find/child")
    public List<Authority> findChildAuthorityByAuthorityId(@RequestParam("authority_id")Integer authority_id){
        return authorityService.findChildAuthorityByAuthorityId(authority_id);
    }*/

}
