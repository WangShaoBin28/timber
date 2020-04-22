package com.chang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName ConsumerInfoEntity
 * @Description
 * @Author yj.c
 * @Date 2019/10/8 14:27
 * @Version 1.0
 **/
@Data
public class ConsumerInfoEntity implements Serializable {

    private static final long serialVersionUID = -1795086721908722837L;

    //客户ID
    private Long id;

    //客户名称
    private String consumerName;

    //客户地址
    private String consumerAddress;

    //客户电话
    private String tel;

    //客户备注
    private String remarks;

    /**创建者**/
    private Long  createUserId;

    /**创建时间**/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**修改者**/
    private Long  updateUserId;

    /**修改时间**/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date  updateDate;


}
