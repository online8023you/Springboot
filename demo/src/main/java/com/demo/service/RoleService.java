package com.demo.service;

import com.demo.entity.Authority;
import com.demo.entity.Role;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface RoleService {
    Role insertNewRole(String role_name, Integer parent_role_id, List<Integer> authority_ids, Integer status);

    void deleteRoleById(Integer id);

    Role updateRoleById(Integer id, String role_name, Integer parent_role_id, List<Integer> authority_ids);

    List<Role> findAllRole();

    Role findRoleById(Integer id);

   /* Role insert(String role_name, Integer parent_role_id, Integer id1, Integer id2);

    Role insertInsert(String role_name, Integer parent_role_id, Integer id1);*/

    List<Role> findRolesByParentId(Integer parent_role_id);

    void findChildAuthority(Authority authority, Role role);

    void findChildRoles(List<Role> childRoles, List<Role> roles);

    List<Role> readRoleExcel(MultipartFile filePath) throws IOException;

    void writeRoleExcel(HttpServletResponse response) throws IOException;

    Role insertRoleExcel(Integer id, String role_name, Date create_time, Date update_time, Integer parent_role_id, List<Integer> authority_ids, Integer status);

}
