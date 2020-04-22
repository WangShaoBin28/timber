package com.chang.service;

import com.chang.entity.SelfRecordEntity;
import com.chang.model.SelfRecordExcelModel;
import com.chang.param.BasePageParamSelf;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @ClassName ISelfInfo
 * @Description
 * @Author yj.c
 * @Date 2019/10/9 14:02
 * @Version 1.0
 **/
public interface ISelfRecordService {


    PageInfo<SelfRecordEntity> list(BasePageParamSelf entity);

    void save(SelfRecordEntity entity);

    SelfRecordEntity getById(Long id);

    void deleteById(Long id);

    Boolean checkData(BasePageParamSelf entity);

    List<SelfRecordExcelModel> getAllByTerm(BasePageParamSelf entity);

}
