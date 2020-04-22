package com.chang.dao;

import com.chang.entity.SelfRecordEntity;
import com.chang.param.BasePageParamSelf;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName SelfRecordDao
 * @Description
 * @Author yj.c
 * @Date 2019/10/9 14:05
 * @Version 1.0
 **/
@Mapper
public interface SelfRecordDao {

    List<SelfRecordEntity> list(BasePageParamSelf entity);

    void insertSelfRecord(SelfRecordEntity entity);

    void updateSelfRecord(SelfRecordEntity entity);

    SelfRecordEntity getById(Long id);

    void deleteById(Long id);


}
