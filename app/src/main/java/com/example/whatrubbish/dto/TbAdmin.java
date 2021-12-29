package com.example.whatrubbish.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.io.Serializable;
//import javax.persistence.*;
//
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
/**
 * @description tb_admin
 * @author mqp
 * @date 2021-08-26
 */
@Data
@ToString
public class TbAdmin implements Serializable {

    private static final long serialVersionUID = 1L;


//    @ApiModelProperty("主键id primary key")
//    @Column(name="id")
//    private String id;
//    "主键id primary key"
    private Integer id;

    /**
     * 账户/手机号
     */

    private String userAccount;

    /**
     * 用户名
     */

    private String userName;

    /**
     * 用户签名图片
     */

    private String userSign;

    /**
     * 密码
     */

    private String userPass;

    /**
     * 密码盐
     */

    private String userSalt;

    /**
     * 当前身份：0-超级管理员 1-企业管理员
     */

    private String privilege;

    /**
     * 单位id
     */

    private String departId;

    /**
     * 单位名称
     */

    private String departName;

    /**
     * 创建时间
     */

    private Date createTime;

    /**
     * 更新时间
     */

    private Date updateTime;

    /**
     * 创建者用户id
     */

    private String createUserId;

    /**
     * 更新者用户id
     */

    private String updateUserId;

    /**
     * 是否删除：0-否；1-是
     */

    private Integer isDeleted;

    /**
     * 是否允许：0-否；1-是
     */

    private Integer enableMark;

    public TbAdmin() {
    }

}