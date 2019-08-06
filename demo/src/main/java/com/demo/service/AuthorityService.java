package com.demo.service;

import com.demo.entity.Authority;

import java.util.List;

public interface AuthorityService {

   Authority insertNewAuthority(String authority_name,String url, String code, Integer status, Integer parent_authority_id);

   void deleteAuthorityById(Integer id);

   Authority updateAuthorityById(Integer id,String authority_name,String url, String code, Integer status, Integer parent_authority_id);

   List<Authority> findAllAuthority();

   Authority findAuthorityById(Integer id);

   /*List<Authority> findParentAuthorityByRoleId(Integer id);*/

   /*List<Authority> findChildAuthorityByAuthorityId(Integer authority_id);*/

   List<Authority> findChildAuthoritiesById(Integer authority_id);

   void findChildAuthority(List<Authority> childAuthorities,List<Authority> authorities);

}
