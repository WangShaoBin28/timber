package com.chang.utils;

import lombok.Data;

/**
 * @ClassName ResponseTable
 * @Author yj.c
 * @Date 2019/1/3 18:42
 * @Version 1.0
 **/
@Data
public class ResponseTable {
    private Integer code;
    private Long count;
    private String msg;
    private Object data;

    public ResponseTable(int code, Long count, String msg, Object content) {
        this.count = count;
        this.code = code;
        this.msg = msg;
        this.data = content;
    }

    public static ResponseTable ofSuccess(Long count, Object content) {
        return new ResponseTable(0,count,"",content);
    }
}
