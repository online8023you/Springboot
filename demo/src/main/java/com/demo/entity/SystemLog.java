package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Dawn
 * @ClassName com.demo.entity.SystemLog
 * @Description
 * @date 2019/9/17 16:03
 */

@Data
@Entity
@Table(name = "t_systemlog")
@AllArgsConstructor
@NoArgsConstructor
public class SystemLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;//用户名
    private String operation;//操作
    private String method;//方法名
    private String params;//参数
    private String ip;//ip地址
    private Date create_time;//操作时间
}
