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
@Table(name = "t_user")
/*@JsonIgnoreProperties({"child_roles"})*/
@JsonIgnoreProperties({"users"})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ExcelProperty("id")
    private Integer id;
    @ExcelProperty("用户名")
    private String user_name;
    @ExcelProperty("账户")
    private String account;
    @ExcelProperty(value = "密码")
    private String password;
    @ExcelProperty("电话")
    private String phone;
    @ExcelProperty("年龄")
    private Integer age;
    @ExcelProperty("创建时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date creat_time;
    @ExcelProperty("修改时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date update_time;
    @ExcelProperty("状态")
    private Integer status;

    @JsonIgnoreProperties({"users"})
    @ManyToMany(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
    @JoinTable(name = "t_user_role",joinColumns = {
            @JoinColumn(name = "user_id")},inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles;

    @ExcelProperty("角色id")
    @Transient
    private String role_ids;
   
}
