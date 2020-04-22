package com.chang.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName ConsumerRecordEntity
 * @Description
 * @Author yj.c
 * @Date 2019/10/8 14:32
 * @Version 1.0
 **/
@Data
public class ConsumerRecordEntity implements Serializable {

    private static final long serialVersionUID = -5719476785275960754L;

    //主键
    private Long id;
    //客户id
    private Long consumerId;
    //客户名称
    private String consumerName;
    //客户名称
    private String consumerAddress;
    //客户电话
    private String tel;

    //记录日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date accessTimeDate;

    //收入
    //客户安装时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date installDate;

    //客户合同金额
    private BigDecimal contractMoney;

    //客户一期款
    private BigDecimal moneyOne;

    //客户二期款
    private BigDecimal moneyTwo;

    //客户三期款
    private BigDecimal moneyThree;



    //支出
    //支出一期款
    private BigDecimal disburseMoneyOne;

    //厂家打款
    private BigDecimal factoryPaymentMoney;

    //赠品
    private String gift;

    //赠品金额
    private BigDecimal giftMoney;

    //手续费
    private BigDecimal serviceChargeMoney;

    //踢脚线
    private BigDecimal kickLineMoney;

    //辅料
    private BigDecimal accessoriesMoney;

    //五金锁具
    private BigDecimal hardwareLockMoney;

    //销售提成
    private BigDecimal salesCommissionMoney;

    //渠道提成
    private BigDecimal channelCommissionMoney;

    //运费
    private BigDecimal freightMoney;

    //上楼费
    private BigDecimal upstairsFeeMoney;

    //安装费
    private BigDecimal installFeeMoney;

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

    /*********************************************************************************************/

    //收入总金额
    private BigDecimal incomeAllMoney;

    //支出总金额
    private BigDecimal payAllMoney;

    //利润
    private BigDecimal profitMoney;

}
