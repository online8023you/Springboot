package com.demo.controller;

import com.demo.entity.Role;
import com.demo.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色管理")
@RestController
public class RoleController {
    /*@ApiParam(required = true,name = "",value = "")*/
    @Autowired
    RoleService roleService;

    @RequiresPermissions(value = {"/menu_role","/role/add","/menu"},logical = Logical.OR)
    @ApiOperation("新增一个角色")
    @PostMapping(value = "/role/insert")
    public Role insertNewRole(@ApiParam(required = true,name = "role_name",value = "角色名")@RequestParam("role_name") String role_name,
                              @ApiParam(required = true,name = "parent_role_id",value = "父级角色id，无父级则为0")@RequestParam("parent_role_id") Integer parent_role_id,
                              @ApiParam(required = true,name = "authority_ids",value = "拥有的权限") @RequestParam("authority_ids") List<Integer> authority_ids,
                              @ApiParam(required = true,name = "status",value = "角色状态") @RequestParam("status") Integer status) {
        return roleService.insertNewRole(role_name, parent_role_id,authority_ids,status);
    }


    @RequiresPermissions(value = {"/menu_role","/role/delete","/menu"},logical = Logical.OR)
    @ApiOperation("根据id删除角色")
    @DeleteMapping(value = "/role/{id}")
    public void deleteRoleById(@ApiParam(required = true,name = "id",value = "id")@PathVariable("id") Integer id) {
        roleService.deleteRoleById(id);
    }

    @RequiresPermissions(value = {"/menu_role","/role/update","/menu"},logical = Logical.OR)
    @ApiOperation("根据id修改角色")
    @PutMapping(value = "/role/{id}")
    public Role updateRoleById(@ApiParam(required = true,name = "id",value = "id")@PathVariable("id") Integer id,
                               @ApiParam(required = true,name = "role_name",value = "角色名")@RequestParam("role_name") String role_name,
                               @ApiParam(required = true,name = "parent_role_id",value = "父级角色id，无父级则为0")@RequestParam("parent_role_id") Integer parent_role_id,
                               @ApiParam(required = true,name = "authority_ids",value = "拥有的权限id")@RequestParam("authority_ids")List<Integer> authority_ids) {
        return roleService.updateRoleById(id, role_name, parent_role_id,authority_ids);
    }

   /* @RequiresPermissions("/role/find")*/
   @RequiresPermissions(value = {"/menu_role","/role/find","/menu"},logical = Logical.OR)
   @ApiOperation("查找所有角色")
    @GetMapping(value = "/role")
    public List<Role> findAllRole() {
        return roleService.findAllRole();
    }

    /*@RequiresPermissions("/role/update")*/
    @RequiresPermissions(value = {"/menu_role","/role/find","/menu"},logical = Logical.OR)
    @ApiOperation("根据id查找角色")
    @GetMapping(value = "/role/{id}")
    public Role findRoleById(@ApiParam(required = true,name = "id",value = "id")@PathVariable("id") Integer id) {
        return roleService.findRoleById(id);
    }

   /* @PostMapping(value = "/role/insertRole")
    public Role insert(@RequestParam("role_name") String role_name, @RequestParam("parent_role_id") Integer parent_role_id, @RequestParam("id1") Integer id1, @RequestParam("id2") Integer id2) {
        return roleService.insert(role_name, parent_role_id, id1, id2);
    }

    @PostMapping(value = "/role/insertRoleInsert")
    public Role insertInsert(@RequestParam("role_name") String role_name, @RequestParam("parent_role_id") Integer parent_role_id, @RequestParam("id1") Integer id1) {
        return roleService.insertInsert(role_name, parent_role_id, id1);
    }*/

    /*@RequiresPermissions(value = {"/menu_role","/role/delete","/menu"},logical = Logical.OR)
    @ApiOperation("根据父级id查找角色")
    @GetMapping(value = "/role/parent_id/{id}")
    public List<Role> findRolesByParentId(@ApiParam(required = true,name = "id",value = "id")@PathVariable("id") Integer id) {
        return roleService.findRolesByParentId(id);
    }*/

}
