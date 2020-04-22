package com.chang.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ConsumerSelectModel
 * @Description
 * @Author yj.c
 * @Date 2019/10/15 16:47
 * @Version 1.0
 **/
@Data
public class ConsumerSelectModel implements Serializable {

    private static final long serialVersionUID = -2125086899834251728L;

    private Long key;

    private String value;

}
