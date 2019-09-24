package com.demo.service;

import com.alibaba.excel.EasyExcel;
import com.demo.dao.AuthorityRepository;
import com.demo.entity.Authority;
import com.demo.excelListener.AuthorityExcelListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Autowired
    public AuthorityRepository authorityRepository;

    @Override
    public Authority insertNewAuthority(String authority_name, String url, String code, Integer status, Integer parent_authority_id) {

        Authority authority = new Authority();
        if (!parent_authority_id.equals(0)) {
            Authority parent_authority = authorityRepository.findById(parent_authority_id).orElse(null);
            authority.setParent_authority(parent_authority);
        }
        authority.setAuthority_name(authority_name);
        authority.setUrl(url);
        authority.setCode(code);
        authority.setStatus(status);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        authority.setCreat_time(timestamp);
        authority.setUpdate_time(timestamp);
        return authorityRepository.save(authority);
    }

    @Override
    public void deleteAuthorityById(Integer id) {
        List<Authority> authorities = authorityRepository.findAuthoritiesByParentId(id);
        if (authorities == null || authorities.isEmpty()) {
            authorityRepository.deleteById(id);
        } else {

            for (Authority authority : authorities
            ) {
                deleteAuthorityById(authority.getId());
            }
            authorityRepository.deleteById(id);
        }
    }

    @Override
    public Authority updateAuthorityById(Integer id, String authority_name, String url, String code, Integer status, Integer parent_authority_id) {
        Authority authority = findAuthorityById(id);
        if (!parent_authority_id.equals(0)) {
            Authority parent_authority = authorityRepository.findById(parent_authority_id).orElse(null);
            authority.setParent_authority(parent_authority);
        }

        authority.setAuthority_name(authority_name);
        authority.setUrl(url);
        authority.setCode(code);
        authority.setStatus(status);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        authority.setUpdate_time(timestamp);
        return authorityRepository.save(authority);
    }

    @Override
    public List<Authority> findAllAuthorities() {
        List<Authority> authorities = authorityRepository.findAllAuthorities();
        for (Authority authority : authorities
        ) {
            findAuthorityById(authority.getId());
        }
        return authorities;
    }

    @Override
    public Authority findAuthorityById(Integer id) {
       /* Authority authority = authorityRepository.findById(id).orElse(null);
        List<Authority> childAuthorities = authorityRepository.findAuthoritiesByParentId(authority.getId());
        if (childAuthorities==null||childAuthorities.isEmpty()){

        }else {
            for (Authority childAuthority: childAuthorities
                 ) {
                System.out.println(childAuthority.getId());
                findAuthorityById(childAuthority.getId());

            }
            authority.setChild_authorities(childAuthorities);
        }
        return authority;*/
        Authority authority = authorityRepository.findById(id).orElse(null);
        List<Authority> childAuthorities = authorityRepository.findChildAuthoritiesById(id);
        if (childAuthorities == null || childAuthorities.isEmpty()) {

        } else {
            List<Authority> authorities = new LinkedList<>();
            for (int i = 0; i < childAuthorities.size(); i++) {
                if (childAuthorities.get(i).getParent_authority().getId() == authority.getId()) {
                    authorities.add(childAuthorities.get(i));
                }
            }
            findChildAuthority(childAuthorities,authorities);

            authority.setChild_authorities(authorities);

        }
        return authority;
    }


    @Override
    public void findChildAuthority(List<Authority> childAuthorities, List<Authority> authorities) {
        if (authorities.size() == 0) {

        } else {
            for (int j = 0; j < authorities.size(); j++) {
                List<Authority> authorityList = new LinkedList<>();
                for (int i = 0; i < childAuthorities.size(); i++) {
                    if (childAuthorities.get(i).getParent_authority().getId() == authorities.get(j).getId()) {
                        authorityList.add(childAuthorities.get(i));
                    }
                }
                findChildAuthority(childAuthorities, authorityList);
                authorities.get(j).setChild_authorities(authorityList);
            }

        }
    }

    @Override
    public Authority insertAuthorityExcel(Integer id, String authority_name, String url, String code, Integer status, Date create_time, Date update_time, Integer parent_authority_id) {
        Authority authority = new Authority();
        if (!parent_authority_id.equals(0)) {
            Authority parent_authority = authorityRepository.findById(parent_authority_id).orElse(null);
            authority.setParent_authority(parent_authority);
        }
        if (id!=null){
            authority.setId(id);
        }
        authority.setAuthority_name(authority_name);
        authority.setUrl(url);
        authority.setCode(code);
        authority.setStatus(status);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        authority.setUpdate_time(timestamp);
        if (create_time == null) {
            authority.setCreat_time(timestamp);
        }else {
            authority.setCreat_time(create_time);
        }
        if (update_time ==null) {
            authority.setUpdate_time(timestamp);
        }else {
            authority.setUpdate_time(update_time);
        }
        return authorityRepository.save(authority);
    }

    @Override
    public List<Authority> readAuthorityExcel(MultipartFile filePath) throws IOException {

        AuthorityExcelListener authorityExcelListener = new AuthorityExcelListener();
        EasyExcel.read(filePath.getInputStream(), Authority.class, authorityExcelListener).sheet().doRead();
        List<Authority> authorities = authorityExcelListener.getRows();

        for (Authority authority :authorities) {
            if (authority.getParent_authorityid()==null){
                insertAuthorityExcel(authority.getId(),authority.getAuthority_name(),
                        authority.getUrl(),authority.getCode(),authority.getStatus(),
                        authority.getCreat_time(),authority.getUpdate_time(),0);
            }else {
                insertAuthorityExcel(authority.getId(),authority.getAuthority_name(),
                        authority.getUrl(),authority.getCode(),authority.getStatus(),
                        authority.getCreat_time(),authority.getUpdate_time(),
                        authority.getParent_authorityid());
            }

        }
        return authorities;
    }

    @Override
    public void writeAuthoritiesExcel(HttpServletResponse response) throws IOException {

        List<Authority> authorities = authorityRepository.findAll();
        List<Authority> authorityList = new ArrayList<>();

        for (Authority authority :authorities) {
            Authority authorityExcel = new Authority();
            if (authority.getParent_authority()==null){
                authorityExcel.setParent_authorityid(null);
            }else {
                authorityExcel.setParent_authorityid(authority.getParent_authority().getId());
            }
            authorityExcel.setId(authority.getId());
            authorityExcel.setAuthority_name(authority.getAuthority_name());
            authorityExcel.setCode(authority.getCode());
            authorityExcel.setCreat_time(authority.getCreat_time());
            authorityExcel.setStatus(authority.getStatus());
            authorityExcel.setUpdate_time(authority.getUpdate_time());
            authorityExcel.setUrl(authority.getUrl());
            authorityList.add(authorityExcel);

        }
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=authority.xlsx");
        EasyExcel.write(response.getOutputStream(), Authority.class).sheet("authority").doWrite(authorityList);
    }
    /*@Override
    public List<Authority> findParentAuthorityByRoleId(Integer id) {
        return authorityRepository.findParentAuthorityByRoleId(id);
    }*/

    @Override
    public List<Authority> findChildAuthoritiesById(Integer authority_id) {
        return authorityRepository.findChildAuthoritiesById(authority_id);
    }



    /*@Override
    public List<Authority> findChildAuthorityByAuthorityId(Integer authority_id) {
        return authorityRepository.findChildAuthorityByAuthorityId(authority_id);
    }*/
}
