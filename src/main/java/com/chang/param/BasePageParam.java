package com.chang.param;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 定义接收查询参数
 *
 * @ClassName BasePageParam
 * @Author zhangzhijun
 * @Date 2018/8/22 11:31
 * @Version 1.0
 **/

@Data
public class BasePageParam implements Serializable {

    private static final long serialVersionUID = -5553934943307468719L;
    private Integer page = 1;
    private Integer size = 10;

    private String queryKey;

    //排序字段，接收数据库字段名
    private String sort;


    //将关键词查询存入Map，xml中使用queryKey
    public Map<String, String> getQueryParam() {
        Map<String, String> map = new HashMap<>();
        String queryKey = getQueryKey();
        if (StringUtils.isNotBlank(queryKey)) {
            map.put("queryKey", queryKey);
        }
        return map;
    }

    //拼接排序条件
    public String getSortCondition() {
        String condition = "";
        String fieldName = getSort();
        if (StringUtils.isNotBlank(fieldName)) {
            String[] split = fieldName.split(",");
            String sortName = "";
            String s = split[0];
            switch (s){
                case "consumerName": sortName = "consumer_name"; break;
                case "accessTimeDate": sortName = "access_time_date"; break;
            }
            if(StringUtils.isNotBlank(sortName)){
                condition = sortName + " " + split[1];
            }
        }
        return condition;
    }


}
