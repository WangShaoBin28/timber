package com.chang.param;

import lombok.Data;

/**
 * 定义接收查询参数
 * @ClassName BasePageParam
 * @Author zhangzhijun
 * @Date 2018/8/22 11:31
 * @Version 1.0
 **/

@Data
public class BasePageParamSelf extends BasePageParam{

    private static final long serialVersionUID = -7340359980217861490L;

    //类型
    private Integer type;

    //摘要
    private String descs;

    private String accessTimeDate;

    private String[] accessTimeDates;



}
