package com.chang.service.impl;

import com.chang.dao.SelfRecordDao;
import com.chang.entity.SelfRecordEntity;
import com.chang.model.SelfRecordExcelModel;
import com.chang.param.BasePageParamSelf;
import com.chang.service.ISelfRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;

/**
 * @ClassName SelfRecordServiceImpl
 * @Description
 * @Author yj.c
 * @Date 2019/10/14 15:08
 * @Version 1.0
 **/
@Service
public class SelfRecordServiceImpl implements ISelfRecordService {

    @Resource
    private SelfRecordDao selfRecordDao;

    @Override
    public PageInfo<SelfRecordEntity> list(BasePageParamSelf entity) {
        PageHelper.startPage(entity.getPage(), entity.getSize());
        String sort = entity.getSort();
        //默认按照操作时间倒序
        if (StringUtils.isNotBlank(sort)) {
            PageHelper.orderBy(entity.getSortCondition());
        } else {
            PageHelper.orderBy("update_date Desc");
        }
        List<SelfRecordEntity> list = selfRecordDao.list(entity);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional
    public void save(SelfRecordEntity entity) {
        if (entity.getId() != null) {
            //修改
            selfRecordDao.updateSelfRecord(entity);
        } else {
            //新增
            selfRecordDao.insertSelfRecord(entity);
        }
    }


    @Override
    public SelfRecordEntity getById(Long id) {
        SelfRecordEntity entity = selfRecordDao.getById(id);
        return entity;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        selfRecordDao.deleteById(id);
    }

    @Override
    public Boolean checkData(BasePageParamSelf entity) {
        List<SelfRecordEntity> list = selfRecordDao.list(entity);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<SelfRecordExcelModel> getAllByTerm(BasePageParamSelf entity) {
        PageHelper.orderBy("access_time_date Desc");
        List<SelfRecordEntity> list = selfRecordDao.list(entity);
        List<SelfRecordExcelModel> result2 = list.stream().map(r -> {
            SelfRecordExcelModel c = new SelfRecordExcelModel();
            BeanUtils.copyProperties(r, c);
            c.setType(r.getType() == 1 ? "收入" : "支出");
            return c;
        }).collect(toList());
//        按照收入支出分组 收入放到上面然后合计，支出放在下面，然后空一行
        Map<Boolean, List<SelfRecordExcelModel>> collect = result2.stream().collect(partitioningBy(r -> r.getType().equals("收入")));
        List<SelfRecordExcelModel> result = new ArrayList<>();
        List<SelfRecordExcelModel> incomeList = collect.get(true);
        List<SelfRecordExcelModel> payList = collect.get(false);
        SelfRecordExcelModel incomeAll = new SelfRecordExcelModel();
        if (incomeList != null && incomeList.size() > 0) {
            result.addAll(incomeList);
            incomeAll.setType("收入合计");
            incomeAll.setMoney(incomeList.stream().map(SelfRecordExcelModel::getMoney).reduce(BigDecimal.ZERO, BigDecimal::add));
            result.add(incomeAll);
            result.add(new SelfRecordExcelModel());
        }
        SelfRecordExcelModel payAll = new SelfRecordExcelModel();
        if (payList != null && payList.size() > 0) {
            result.addAll(payList);
            payAll.setType("支出合计");
            payAll.setMoney(payList.stream().map(SelfRecordExcelModel::getMoney).reduce(BigDecimal.ZERO, BigDecimal::add));
            result.add(payAll);
        }
        SelfRecordExcelModel all = new SelfRecordExcelModel();
        all.setType("总利润");
        BigDecimal bigDecimal = payAll.getMoney() == null ? BigDecimal.ZERO : payAll.getMoney();
        BigDecimal bigDecimal2 = incomeAll.getMoney() == null ? BigDecimal.ZERO : incomeAll.getMoney();
        all.setMoney(bigDecimal2.subtract(bigDecimal));
        result.add(all);
        return result;
    }

}
