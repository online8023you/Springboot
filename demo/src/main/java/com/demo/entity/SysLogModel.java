package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Dawn
 * @ClassName com.demo.entity.SysLogModel
 * @Description
 * @date 2019/9/18 10:56
 */

@Data
@Entity
@Table(name = "t_syslog")
@AllArgsConstructor
@NoArgsConstructor
public class SysLogModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;         //id

    private String username;    //用户名

    private String actionName;  //操作名

    private String target;      //目标类型：CONTROLLER、SERVICE、DAO、METHOD

    private String method;      //方法名

    private String params;      //参数

    private String input;       //旧值

    private String output;      //新值

    private String ip;          //ip

    private Date create_time;   //操作时间

    private String exMsg;       //异常信息
}
