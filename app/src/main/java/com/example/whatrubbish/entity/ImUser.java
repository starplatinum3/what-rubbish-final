package com.example.whatrubbish.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ImUser  extends BaseEntity<ImUser> implements Serializable {

    private static final long serialVersionUID = 1L;

    //@TableId
    private String id;

    private String avatar;

    private String name;

    private String sign;

    private String mobile;

    private String email;

    private String password;


    //@TableField("login_name")
    private String loginName;

    //@TableField("dept_id")
    private String deptId;

    //@TableField(exist = false)
    private String depts;


    private Integer rubUserId;

}