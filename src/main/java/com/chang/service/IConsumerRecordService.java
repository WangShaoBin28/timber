package com.chang.service;

import com.chang.entity.ConsumerRecordEntity;
import com.chang.model.ConsumerRecordExcelModel;
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
public interface IConsumerRecordService {


    PageInfo<ConsumerRecordEntity> list(BasePageParamConsumer entity);

    void save(ConsumerRecordEntity entity);

    //根据客户ID 查询客户信息
    ConsumerRecordEntity getById(Long id);

    void deleteById(Long id);

    List<ConsumerRecordExcelModel> getAllByTerm(BasePageParamConsumer entity);

    Boolean checkData(BasePageParamConsumer entity);
}
