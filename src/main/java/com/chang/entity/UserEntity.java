package com.chang.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName UserEntity
 * @Description
 * @Author yj.c
 * @Date 2019/10/18 11:38
 * @Version 1.0
 **/
@Data
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1328472637620679192L;

    /**用户ID**/
    private Long  id;

    /**用户名称**/
    private String  name;

    /**账号**/
    private String  accountNumber;

    /**密码**/
    private String  password;

    /****/
    private String  createUserId;

    /****/
    private Date createDate;

    /****/
    private String  updateUserId;

    /****/
    private Date  updateDate;
}
