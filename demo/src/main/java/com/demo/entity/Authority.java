package com.demo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_authority")
@JsonIgnoreProperties({"parent_authority", "parent_role", "roles","parent_authorityid"})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ExcelProperty("id")
    private Integer id;
    @ExcelProperty("权限名称")
    private String authority_name;
    @ExcelProperty("url")
    private String url;
    @ExcelProperty("编码")
    private String code;
    @ExcelProperty("状态")
    private Integer status;
    @ExcelProperty("创建时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date creat_time;
    @ExcelProperty("更新时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date update_time;

    @Transient
    private List<Authority> child_authorities;

    @OneToOne
    @JSONField(serialize = false)
    @JsonIgnoreProperties({"parent_authority", "parent_role"})
    private Authority parent_authority;

    @Transient
    @ExcelProperty("父级权限id")
    private Integer parent_authorityid;


    @JsonIgnoreProperties({"roles"})
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(name = "t_role_authority",
            joinColumns = {@JoinColumn(name = "authority_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles;

    public Authority(Integer id, String authority_name, String url) {
        this.id = id;
        this.authority_name = authority_name;
        this.url = url;

    }

    public Authority(Integer id, String authority_name, String url, String code, Integer status, Date creat_time, Date update_time, Integer parent_authorityid) {
        this.id = id;
        this.authority_name = authority_name;
        this.url = url;
        this.code = code;
        this.status = status;
        this.creat_time = creat_time;
        this.update_time = update_time;
        this.parent_authorityid = parent_authorityid;
    }
}
