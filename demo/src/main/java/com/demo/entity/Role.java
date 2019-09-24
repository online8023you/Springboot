package com.demo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_role")
@JsonIgnoreProperties({"parent_role","parent_authority","authorityList","roles","authority_id"})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ExcelProperty("id")
    private Integer id;
    @ExcelProperty("角色名")
    private String role_name;
    @ExcelProperty("创建时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date creat_time;
    @ExcelProperty("更新时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date update_time;
    @ExcelProperty("状态")
    private Integer status;


    @ExcelProperty("父级角色id")
    @Transient
    private Integer parent_roleid;

    @ExcelProperty("权限id")
    @Transient
    private String authority_ids;



    @JsonIgnoreProperties({"roles"})
    @ManyToMany(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinTable(name = "t_user_role",joinColumns = {
            @JoinColumn(name = "role_id")},inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> users;

    /*存数据库*/
    @ManyToMany(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinTable(name = "t_role_authority",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id")})
    private List<Authority> authorityList;

    /*返回json*/
    @Transient
    private List<Authority> authorities;


    @OneToOne
    @JsonIgnoreProperties({"parent_authority","parent_role"})
    @ExcelProperty("")
    private Role parent_role;

    @Transient
    private List<Role> child_roles;





}
