package com.chang.param;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 定义接收查询参数
 * @ClassName BasePageParam
 * @Author zhangzhijun
 * @Date 2018/8/22 11:31
 * @Version 1.0
 **/

@Data
public class BasePageParamConsumer extends BasePageParam{

    private static final long serialVersionUID = -7340359980217861490L;

    //客户名称
    private String consumerName;

    //客户地址
    private String consumerAddress;

    //客户电话
    private String tel;

    private String accessTimeDate;

    private String[] accessTimeDates;



}
