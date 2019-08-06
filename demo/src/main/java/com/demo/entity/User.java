package com.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_user")
/*@JsonIgnoreProperties({"child_roles"})*/
@JsonIgnoreProperties({"users"})
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String user_name;
    private String account;
    private String password;
    private String phone;
    private Integer age;
    private Date creat_time;
    private Date update_time;
    private Integer status;

    @JsonIgnoreProperties({"users"})
    @ManyToMany(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
    @JoinTable(name = "t_user_role",joinColumns = {
            @JoinColumn(name = "user_id")},inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles;

   
}
