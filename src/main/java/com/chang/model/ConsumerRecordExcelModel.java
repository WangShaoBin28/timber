package com.chang.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

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
public class ConsumerRecordExcelModel extends BaseRowModel {

    @ExcelProperty(value = {"客户明细记录", "收入", "日期"}, index = 0, format = "yyyy-MM-dd")
    private Date accessTimeDate;

    //客户名称
    @ExcelProperty(value = {"客户明细记录", "收入", "姓名"}, index = 1)
    private String consumerName;
    //地址
    @ExcelProperty(value = {"客户明细记录", "收入", "地址"}, index = 2)
    private String consumerAddress;
    //客户电话
    @ExcelProperty(value = {"客户明细记录", "收入", "电话"}, index = 3)
    private String tel;
    //收入
    //客户安装时间
    @ExcelProperty(value = {"客户明细记录", "收入", "安装时间"}, index = 4, format = "yyyy-MM-dd")
    private Date installDate;

    //客户合同金额
    @ExcelProperty(value = {"客户明细记录", "收入", "合同金额"}, index = 5)
    private BigDecimal contractMoney;

    //客户一期款
    @ExcelProperty(value = {"客户明细记录", "收入", "一期款"}, index = 6)
    private BigDecimal moneyOne;

    //客户二期款
    @ExcelProperty(value = {"客户明细记录", "收入", "二期款"}, index = 7)
    private BigDecimal moneyTwo;

    //客户三期款
    @ExcelProperty(value = {"客户明细记录", "收入", "三期款"}, index = 8)
    private BigDecimal moneyThree;

    //收入总金额
    @ExcelProperty(value = {"客户明细记录", "收入", "收入总金额"}, index = 9)
    private BigDecimal incomeAllMoney;


    //支出
    //支出一期款
    @ExcelProperty(value = {"客户明细记录", "支出", "支出一期款"}, index = 10)
    private BigDecimal disburseMoneyOne;

    //厂家打款
    @ExcelProperty(value = {"客户明细记录", "支出", "厂家打款"}, index = 11)
    private BigDecimal factoryPaymentMoney;

    //赠品
    @ExcelProperty(value = {"客户明细记录", "支出", "赠品"}, index = 12)
    private String gift;

    //赠品金额
    @ExcelProperty(value = {"客户明细记录", "支出", "赠品金额"}, index = 13)
    private BigDecimal giftMoney;

    //手续费
    @ExcelProperty(value = {"客户明细记录", "支出", "手续费"}, index = 14)
    private BigDecimal serviceChargeMoney;

    //踢脚线
    @ExcelProperty(value = {"客户明细记录", "支出", "踢脚线"}, index = 15)
    private BigDecimal kickLineMoney;

    //辅料
    @ExcelProperty(value = {"客户明细记录", "支出", "辅料"}, index = 16)
    private BigDecimal accessoriesMoney;

    //五金锁具
    @ExcelProperty(value = {"客户明细记录", "支出", "五金锁具"}, index = 17)
    private BigDecimal hardwareLockMoney;

    //销售提成
    @ExcelProperty(value = {"客户明细记录", "支出", "销售提成"}, index = 18)
    private BigDecimal salesCommissionMoney;

    //渠道提成
    @ExcelProperty(value = {"客户明细记录", "支出", "渠道提成"}, index = 19)
    private BigDecimal channelCommissionMoney;

    //运费
    @ExcelProperty(value = {"客户明细记录", "支出", "运费"}, index = 20)
    private BigDecimal freightMoney;

    //上楼费
    @ExcelProperty(value = {"客户明细记录", "支出", "上楼费"}, index = 21)
    private BigDecimal upstairsFeeMoney;

    //支出总金额
    @ExcelProperty(value = {"客户明细记录", "支出", "支出总金额"}, index = 22)
    private BigDecimal payAllMoney;

    //利润
    @ExcelProperty(value = {"客户明细记录", "利润", "利润金额"}, index = 23)
    private BigDecimal profitMoney;


}
