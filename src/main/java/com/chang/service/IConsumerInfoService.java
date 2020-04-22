package com.chang.service;

import com.chang.entity.ConsumerInfoEntity;
import com.chang.param.BasePageParamConsumer;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @ClassName IConsumerInfo
 * @Description
 * @Author yj.c
 * @Date 2019/10/9 14:02
 * @Version 1.0
 **/
public interface IConsumerInfoService {


    PageInfo<ConsumerInfoEntity> list(BasePageParamConsumer entity);

    void save(ConsumerInfoEntity entity);

    Boolean checkConsumerInfo(Long id);

    //根据客户ID 查询客户信息
    ConsumerInfoEntity getById(Long id);

    List<ConsumerInfoEntity> getAll();

    void deleteById(Long id);
}
