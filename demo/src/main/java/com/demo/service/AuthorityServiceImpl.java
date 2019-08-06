package com.demo.service;

import com.demo.dao.AuthorityRepository;
import com.demo.entity.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    public List<Authority> findAllAuthority() {
        List<Authority> authorities = authorityRepository.findAllAuthority();
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
