package com.chang.service.impl;

import com.chang.dao.ConsumerInfoDao;
import com.chang.dao.ConsumerRecordDao;
import com.chang.entity.ConsumerInfoEntity;
import com.chang.entity.ConsumerRecordEntity;
import com.chang.param.BasePageParamConsumer;
import com.chang.service.IConsumerInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName ConsumerInfoServiceImpl
 * @Description
 * @Author yj.c
 * @Date 2019/10/9 14:04
 * @Version 1.0
 **/
@Service
public class ConsumerInfoServiceImpl implements IConsumerInfoService {

    @Resource
    private ConsumerInfoDao consumerInfoDao;
    @Resource
    private ConsumerRecordDao consumerRecordDao;

    @Override
    public PageInfo<ConsumerInfoEntity> list(BasePageParamConsumer entity) {
        PageHelper.startPage(entity.getPage(), entity.getSize());
        String sort = entity.getSort();
        //默认按照操作时间倒序
        if (StringUtils.isNotBlank(sort)) {
            PageHelper.orderBy(entity.getSortCondition());
        } else {
            PageHelper.orderBy("update_date Desc");
        }

        List<ConsumerInfoEntity> list = consumerInfoDao.list(entity);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional
    public void save(ConsumerInfoEntity entity) {
        if (entity.getId() != null) {
            //修改
            consumerInfoDao.updateConsumer(entity);
            //由于客户收支表中有两个字段是info表的，所以要同时更新
            ConsumerRecordEntity e = new ConsumerRecordEntity();
            e.setConsumerId(entity.getId());
            e.setConsumerName(entity.getConsumerName());
            e.setTel(entity.getTel());
            consumerRecordDao.updateConsumerRecordByConsumerId(e);
        } else {
            //新增
            consumerInfoDao.insertConsumer(entity);
        }
    }

    @Override
    public Boolean checkConsumerInfo(Long id) {
        List<ConsumerRecordEntity> list = consumerRecordDao.getByConsumerId(id);
        if (list != null && list.size() > 0) {
            return false;
        }
        return true;
    }

    @Override
    public ConsumerInfoEntity getById(Long id) {
        return consumerInfoDao.getById(id);
    }

    @Override
    public List<ConsumerInfoEntity> getAll() {
        return consumerInfoDao.getAll();
    }

    @Override
    public void deleteById(Long id) {
        consumerInfoDao.deleteById(id);
    }
}
