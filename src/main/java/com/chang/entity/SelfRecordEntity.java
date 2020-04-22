package com.chang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName SelfRecordEntity
 * @Description
 * @Author yj.c
 * @Date 2019/10/23 9:31
 * @Version 1.0
 **/
@Data
public class SelfRecordEntity implements Serializable {

    private static final long serialVersionUID = -7764952315869041231L;

    /****/
    private Long  id;

    /**记录时间**/
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date accessTimeDate;

    /**摘要**/
    private String  descs;

    /**金额**/
    private BigDecimal money;

    /**类型(1.收入，2.支出)**/
    private Integer type;

    /****/
    private Long  createUserId;

    /****/
    private Date  createDate;

    /****/
    private Long  updateUserId;

    /****/
    private Date  updateDate;
}
