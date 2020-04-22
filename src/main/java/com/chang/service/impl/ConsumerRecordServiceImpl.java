package com.chang.service.impl;

import com.chang.dao.ConsumerInfoDao;
import com.chang.dao.ConsumerRecordDao;
import com.chang.entity.ConsumerInfoEntity;
import com.chang.entity.ConsumerRecordEntity;
import com.chang.model.ConsumerRecordExcelModel;
import com.chang.param.BasePageParamConsumer;
import com.chang.service.IConsumerRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

/**
 * @ClassName ConsumerRecordServiceImpl
 * @Description
 * @Author yj.c
 * @Date 2019/10/14 15:08
 * @Version 1.0
 **/
@Service
public class ConsumerRecordServiceImpl implements IConsumerRecordService {

    @Resource
    private ConsumerRecordDao consumerRecordDao;
    @Resource
    private ConsumerInfoDao consumerInfoDao;

    @Override
    public PageInfo<ConsumerRecordEntity> list(BasePageParamConsumer entity) {
        PageHelper.startPage(entity.getPage(), entity.getSize());
        String sort = entity.getSort();
        //默认按照操作时间倒序
        if (StringUtils.isNotBlank(sort)) {
            PageHelper.orderBy(entity.getSortCondition());
        } else {
            PageHelper.orderBy("update_date Desc");
        }
        List<ConsumerRecordEntity> list = consumerRecordDao.list(entity);
        PageInfo<ConsumerRecordEntity> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        List<ConsumerRecordEntity> result = list.stream().map(r -> {
            ConsumerRecordEntity c = new ConsumerRecordEntity();
            BeanUtils.copyProperties(r, c);
            BigDecimal incomeAllMoney = r.getMoneyOne().add(r.getMoneyTwo()).add(r.getMoneyThree());
            c.setIncomeAllMoney(incomeAllMoney);
            BigDecimal payAllMoney = r.getDisburseMoneyOne()
                    .add(r.getFactoryPaymentMoney())
                    .add(r.getGiftMoney())
                    .add(r.getServiceChargeMoney())
                    .add(r.getKickLineMoney())
                    .add(r.getAccessoriesMoney())
                    .add(r.getHardwareLockMoney())
                    .add(r.getSalesCommissionMoney())
                    .add(r.getChannelCommissionMoney())
                    .add(r.getFreightMoney())
                    .add(r.getUpstairsFeeMoney())
                    .add(r.getInstallFeeMoney());
            c.setPayAllMoney(payAllMoney);
            c.setProfitMoney(incomeAllMoney.subtract(payAllMoney));
            return c;
        }).collect(toList());
        PageInfo<ConsumerRecordEntity> consumerRecordEntityPageInfo = new PageInfo<>(result);
        consumerRecordEntityPageInfo.setTotal(total);
        return consumerRecordEntityPageInfo;
    }

    @Override
    public void save(ConsumerRecordEntity entity) {
        if (entity.getConsumerId() != null) {
            ConsumerInfoEntity consumerInfoEntity = consumerInfoDao.getById(entity.getConsumerId());
            entity.setConsumerName(consumerInfoEntity.getConsumerName());
            entity.setTel(consumerInfoEntity.getTel());
            if (entity.getId() != null) {
                //修改
                consumerRecordDao.updateConsumerRecord(entity);
            } else {
                //新增
                consumerRecordDao.insertConsumerRecord(entity);
            }
        }
    }


    @Override
    public ConsumerRecordEntity getById(Long id) {
        ConsumerRecordEntity entity = consumerRecordDao.getById(id);
        BigDecimal incomeAllMoney = entity.getMoneyOne().add(entity.getMoneyTwo()).add(entity.getMoneyThree());
        entity.setIncomeAllMoney(incomeAllMoney);
        BigDecimal payAllMoney = entity.getDisburseMoneyOne()
                .add(entity.getFactoryPaymentMoney())
                .add(entity.getGiftMoney())
                .add(entity.getServiceChargeMoney())
                .add(entity.getKickLineMoney())
                .add(entity.getAccessoriesMoney())
                .add(entity.getHardwareLockMoney())
                .add(entity.getSalesCommissionMoney())
                .add(entity.getChannelCommissionMoney())
                .add(entity.getFreightMoney())
                .add(entity.getUpstairsFeeMoney())
                .add(entity.getInstallFeeMoney());
        entity.setPayAllMoney(payAllMoney);
        entity.setProfitMoney(incomeAllMoney.subtract(payAllMoney));
        return entity;
    }

    @Override
    public void deleteById(Long id) {
        consumerRecordDao.deleteById(id);
    }

    @Override
    public List<ConsumerRecordExcelModel> getAllByTerm(BasePageParamConsumer entity) {
        PageHelper.orderBy("access_time_date Desc");
        List<ConsumerRecordEntity> list = consumerRecordDao.list(entity);
        List<ConsumerRecordExcelModel> result = list.stream().map(r -> {
            ConsumerRecordExcelModel c = new ConsumerRecordExcelModel();
            BeanUtils.copyProperties(r, c);
            BigDecimal incomeAllMoney = r.getMoneyOne().add(r.getMoneyTwo()).add(r.getMoneyThree());
            c.setIncomeAllMoney(incomeAllMoney);
            BigDecimal payAllMoney = r.getDisburseMoneyOne()
                    .add(r.getFactoryPaymentMoney())
                    .add(r.getGiftMoney())
                    .add(r.getServiceChargeMoney())
                    .add(r.getKickLineMoney())
                    .add(r.getAccessoriesMoney())
                    .add(r.getHardwareLockMoney())
                    .add(r.getSalesCommissionMoney())
                    .add(r.getChannelCommissionMoney())
                    .add(r.getFreightMoney())
                    .add(r.getUpstairsFeeMoney())
                    .add(r.getInstallFeeMoney());
            c.setPayAllMoney(payAllMoney);
            c.setProfitMoney(incomeAllMoney.subtract(payAllMoney));
            return c;
        }).collect(toList());
        //计算合计
        ConsumerRecordExcelModel all = new ConsumerRecordExcelModel();
        all.setConsumerName("合计");
        all.setIncomeAllMoney(result.stream().map(ConsumerRecordExcelModel::getIncomeAllMoney).reduce(BigDecimal.ZERO, BigDecimal::add));
        all.setPayAllMoney(result.stream().map(ConsumerRecordExcelModel::getPayAllMoney).reduce(BigDecimal.ZERO, BigDecimal::add));
        all.setProfitMoney(result.stream().map(ConsumerRecordExcelModel::getProfitMoney).reduce(BigDecimal.ZERO, BigDecimal::add));
        result.add(all);
        return result;
    }

    @Override
    public Boolean checkData(BasePageParamConsumer entity) {
        List<ConsumerRecordEntity> list = consumerRecordDao.list(entity);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }
}
