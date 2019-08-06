package com.demo.service;

import com.demo.dao.AuthorityRepository;
import com.demo.dao.RoleRepository;
import com.demo.entity.Authority;
import com.demo.entity.Role;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    public RoleRepository roleRepository;

    @Autowired
    public AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityService authorityService;

    @Override
    public Role insertNewRole(String role_name, Integer parent_role_id, List<Integer> authority_ids, Integer status) {
        Role role = new Role();

        role.setRole_name(role_name);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        role.setUpdate_time(timestamp);
        role.setCreat_time(timestamp);
        role.setStatus(status);
        List<Authority> authorities = new ArrayList<>();
        for (Integer authority_id : authority_ids
        ) {
            Authority authority = authorityRepository.findById(authority_id).orElse(null);
            authorities.add(authority);
        }
        role.setAuthorityList(authorities);
        if (!parent_role_id.equals(0)) {
            Role parent_role = roleRepository.findById(parent_role_id).orElse(null);
            role.setParent_role(parent_role);
        }

        return roleRepository.save(role);
    }

    @Override
    public void deleteRoleById(Integer id) {
        List<Role> roles = roleRepository.findRolesByParentId(id);
        System.out.println(roles);
        if (roles == null || roles.isEmpty()) {
            roleRepository.deleteById(id);
        } else {

            for (Role role : roles
            ) {
                deleteRoleById(role.getId());
            }
            roleRepository.deleteById(id);
        }
    }

    @Override
    public Role updateRoleById(Integer id, String role_name, Integer parent_role_id, List<Integer> authority_ids) {
        Role role = roleRepository.findById(id).orElse(null);
        role.setRole_name(role_name);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        role.setUpdate_time(timestamp);
        List<Authority> authorities = new ArrayList<>();
        for (Integer authority_id : authority_ids
        ) {
            Authority authority = authorityRepository.findById(authority_id).orElse(null);
            authorities.add(authority);
        }
        role.setAuthorities(authorities);
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findAllRole() {
        List<Role> roles = roleRepository.findAllRole();
        for (Role role : roles
        ) {
            List<Role> childRoles = roleRepository.findChildRolesById(role.getId());
            findChildRoles(childRoles, roles);
        }

        return roles;
    }

    //ok
    @Override
    public Role findRoleById(Integer id) {
        Role role = roleRepository.findById(id).orElse(null);


        List<Integer> ids = new ArrayList<>();
        List<Integer> parent_ids = new ArrayList<>();
        for (int i = 0; i < role.getAuthorityList().size(); i++) {
            if (role.getAuthorityList().get(i).getParent_authority() == null) {
                ids.add(role.getAuthorityList().get(i).getId());
            } else {
                ids.add(role.getAuthorityList().get(i).getId());
                parent_ids.add(role.getAuthorityList().get(i).getParent_authority().getId());
            }

        }
        List<Authority> authorities = new ArrayList<>();
        for (Authority authority : role.getAuthorityList()
        ) {
            if (authority.getParent_authority() == null) {
                authorities.add(authority);
            } else if (!ids.contains(authority.getParent_authority().getId())) {
                authorities.add(authority);
            }
            List<Authority> authorityList = new ArrayList<>();
            for (Authority childAuthority : role.getAuthorityList()
            ) {
                if (childAuthority.getParent_authority() == null) {

                } else if (childAuthority.getParent_authority().getId() == authority.getId()) {
                    authorityList.add(childAuthority);
                }
                if (authorityList.size() == 0) {
                } else {
                    findChildAuthority(childAuthority, role);
                }

            }

            authority.setChild_authorities(authorityList);

        }

        role.setAuthorities(authorities);
/*

        List<Role> roles = roleRepository.findRolesByParentId(id);
        List<Integer> childRole_ids = new ArrayList<>();
        List<Role> roleList = new ArrayList<>();
        for (Role childRole:roles
             ) {
            childRole_ids.add(childRole.getId());
        }
        for (Integer childRole_id:childRole_ids
             ) {
            Role childRole = findRoleById(childRole_id);
            roleList.add(childRole);
        }
        role.setChild_roles(roleList);
*/

        return role;
    }

    //ok
    @Override
    public void findChildAuthority(Authority authority, Role role) {
        List<Authority> authorityList = new ArrayList<>();
        for (Authority childAuthority : role.getAuthorityList()
        ) {
            if (childAuthority.getParent_authority() == null) {

            } else if (childAuthority.getParent_authority().getId() == authority.getId()) {
                authorityList.add(childAuthority);
            }
            if (authorityList.size() == 0) {
                return;
            } else {
                findChildAuthority(childAuthority, role);
            }
        }
        authority.setChild_authorities(authorityList);

    }

    @Override
    public void findChildRoles(List<Role> childRoles, List<Role> roles) {
        if (roles.size() == 0) {

        } else {
            for (int j = 0; j < roles.size(); j++) {
                List<Role> roleList = new LinkedList<>();
                for (int i = 0; i < childRoles.size(); i++) {
                    if (childRoles.get(i).getParent_role().getId() == roles.get(j).getId()) {
                        roleList.add(childRoles.get(i));
                    }
                }
                findChildRoles(childRoles, roleList);
                roles.get(j).setChild_roles(roleList);
            }

        }
    }


    //ok
    @Override
    public List<Role> findRolesByParentId(Integer parent_role_id) {
        return roleRepository.findRolesByParentId(parent_role_id);
    }


}
