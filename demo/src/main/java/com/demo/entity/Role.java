package com.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_role")
@JsonIgnoreProperties({"parent_role","parent_authority","authorityList","roles"})
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String role_name;
    private Date creat_time;
    private Date update_time;
    private Integer status;

    @JsonIgnoreProperties({"roles"})
    @ManyToMany(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinTable(name = "t_user_role",joinColumns = {
            @JoinColumn(name = "role_id")},inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> users;

    @ManyToMany(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinTable(name = "t_role_authority",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id")})
    private List<Authority> authorityList;

    @Transient
    private List<Authority> authorities;


    @OneToOne
    @JsonIgnoreProperties({"parent_authority","parent_role"})
    private Role parent_role;

    @Transient
    private List<Role> child_roles;





}
