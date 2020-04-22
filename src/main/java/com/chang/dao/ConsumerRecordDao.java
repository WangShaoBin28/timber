package com.chang.dao;

import com.chang.entity.ConsumerRecordEntity;
import com.chang.param.BasePageParamConsumer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName ConsumerRecordDao
 * @Description
 * @Author yj.c
 * @Date 2019/10/9 14:05
 * @Version 1.0
 **/
@Mapper
public interface ConsumerRecordDao {

    List<ConsumerRecordEntity> list(BasePageParamConsumer entity);

    void insertConsumerRecord(ConsumerRecordEntity entity);

    void updateConsumerRecord(ConsumerRecordEntity entity);

    ConsumerRecordEntity getById(Long id);

    void deleteById(Long id);

    List<ConsumerRecordEntity> getByConsumerId(Long id);

    void updateConsumerRecordByConsumerId(ConsumerRecordEntity e);
}
