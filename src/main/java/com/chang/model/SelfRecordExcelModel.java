package com.chang.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
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
public class SelfRecordExcelModel extends BaseRowModel {

    /**记录时间**/
    @ExcelProperty(value = {"个人登记", "日期"}, index = 0, format = "yyyy-MM-dd")
    private Date accessTimeDate;

    /**摘要**/
    @ExcelProperty(value = {"个人登记", "摘要"}, index = 1)
    private String  descs;

    /**金额**/
    @ExcelProperty(value = {"个人登记", "金额"}, index = 3)
    private BigDecimal money;

    /**类型(1.收入，2.支出)**/
    @ExcelProperty(value = {"个人登记", "类型"}, index = 2)
    private String type;
}
