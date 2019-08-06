package com.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_authority")
@JsonIgnoreProperties({"parent_authority","parent_role","roles"})
@Data
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String authority_name;
    private String url;
    private String code;
    private Integer status;
    private Date creat_time;
    private Date update_time;
    @Transient
    private List<Authority> child_authorities;

    @OneToOne
    @JsonIgnoreProperties({"parent_authority","parent_role"})
    private Authority parent_authority;


    @JsonIgnoreProperties({"roles"})
    @ManyToMany(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinTable(name = "t_role_authority",
            joinColumns = {@JoinColumn(name = "authority_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles;


}
