package com.chang.dao;

import com.chang.entity.ConsumerInfoEntity;
import com.chang.param.BasePageParamConsumer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName consumerInfoDao
 * @Description
 * @Author yj.c
 * @Date 2019/10/9 14:05
 * @Version 1.0
 **/
@Mapper
public interface ConsumerInfoDao {

    List<ConsumerInfoEntity> list(BasePageParamConsumer entity);

    void insertConsumer(ConsumerInfoEntity entity);

    void updateConsumer(ConsumerInfoEntity entity);

    ConsumerInfoEntity getById(Long id);

    List<ConsumerInfoEntity> getAll();

    void deleteById(Long id);
}
