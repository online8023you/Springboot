package com.demo.service;

import com.demo.entity.Authority;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface AuthorityService {

   Authority insertNewAuthority(String authority_name,String url, String code, Integer status, Integer parent_authority_id);

   void deleteAuthorityById(Integer id);

   Authority updateAuthorityById(Integer id,String authority_name,String url, String code, Integer status, Integer parent_authority_id);

   List<Authority> findAllAuthorities();

   Authority findAuthorityById(Integer id);

   /*List<Authority> findParentAuthorityByRoleId(Integer id);*/

   /*List<Authority> findChildAuthorityByAuthorityId(Integer authority_id);*/

   List<Authority> findChildAuthoritiesById(Integer authority_id);

   void findChildAuthority(List<Authority> childAuthorities,List<Authority> authorities);

   Authority insertAuthorityExcel(Integer id, String authority_name, String url, String code, Integer status, Date create_time, Date update_time,Integer parent_authority_id);

   List<Authority> readAuthorityExcel(MultipartFile filePath) throws IOException;

   void writeAuthoritiesExcel(HttpServletResponse response) throws IOException;
}
