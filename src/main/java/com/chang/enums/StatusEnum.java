package com.chang.enums;

import java.util.stream.Stream;

/**
 * @ClassName StatusEnum
 * @Description
 * @Author yj.c
 * @Date 2019/1/7 10:21
 * @Version 1.0
 **/

public enum StatusEnum {

    SUCCESS(200, "成功"),
    BAD_REQUEST(400, "发出的请求参数有错误"),
    NOT_FOUND(404, "发出的请求路径找不到"),
    INTERNAL_SERVER_ERROR(500, "服务器发生错误"),
    UNAUTHORIZED(401, "未经授权");

    private Integer code;
    private String msg;

    StatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;

    }

    public Integer getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }



    public static String getValueByCode(Integer code) {
        if(code != null){
            return Stream.of(values()).filter(r -> r.getCode() .equals(code)).map(StatusEnum::getMsg).reduce("", String::concat);
        }
        return null;
    }

}
