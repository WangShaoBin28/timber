package com.chang.controller;


import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.chang.entity.ConsumerInfoEntity;
import com.chang.entity.ConsumerRecordEntity;
import com.chang.entity.UserEntity;
import com.chang.handler.StyleExcelConsumerRecordHandler;
import com.chang.model.ConsumerRecordExcelModel;
import com.chang.model.ConsumerSelectModel;
import com.chang.param.BasePageParamConsumer;
import com.chang.service.IConsumerInfoService;
import com.chang.service.IConsumerRecordService;
import com.chang.utils.RandomUtil;
import com.chang.utils.ResponseTable;
import com.chang.utils.UserUtils;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @ClassName ConsumerRecordController
 * @Description
 * @Author yj.c
 * @Date 2019/10/12 10:23
 * @Version 1.0
 **/
@Controller
@RequestMapping("/consumer-record")
public class ConsumerRecordController {

    @Resource
    private UserUtils userUtils;

    @Resource
    private IConsumerRecordService consumerRecordService;

    @Resource
    private IConsumerInfoService consumerInfoService;


    @RequestMapping("index")
    public String getIndex() {
        return "r-index";
    }

    @RequestMapping("detail/{id}")
    public String detail(@PathVariable Long id, Model m) {
        ConsumerRecordEntity entity = consumerRecordService.getById(id);
        m.addAttribute("e", entity);
        return "consumer-record-detail";
    }

    @RequestMapping("to-excel")
    public String toExcel() {
        return "consumer-record-excel";
    }

    @RequestMapping("to-update/{id}")
    public String toUpdate(@PathVariable Long id, Model m) {
        ConsumerRecordEntity entity = consumerRecordService.getById(id);
        List<ConsumerInfoEntity> list = consumerInfoService.getAll();
        List<ConsumerSelectModel> selectModels = list.stream().sorted(Comparator.comparing(r -> RandomUtil.getPingYin(r.getConsumerName()).toLowerCase())).map(r -> {
            ConsumerSelectModel o = new ConsumerSelectModel();
            o.setKey(r.getId());
            String s = r.getTel() != null ? r.getTel() : "";
            o.setValue(r.getConsumerName() + "(" + s + ")");
            return o;
        }).collect(toList());
        m.addAttribute("e", entity);
        m.addAttribute("selectModels", selectModels);
        return "consumer-record-update";
    }


    @RequestMapping("to-insert")
    public String toInsert(Model m) {
        List<ConsumerInfoEntity> list = consumerInfoService.getAll();
        List<ConsumerSelectModel> selectModels = list.stream().sorted(Comparator.comparing(r -> RandomUtil.getPingYin(r.getConsumerName()).toLowerCase())).map(r -> {
            ConsumerSelectModel o = new ConsumerSelectModel();
            o.setKey(r.getId());
            String s = r.getTel() != null ? r.getTel() : "";
            o.setValue(r.getConsumerName() + "(" + s + ")");
            return o;
        }).collect(toList());
        m.addAttribute("selectModels", selectModels);
        m.addAttribute("e", new ConsumerRecordEntity());
        return "consumer-record-update";
    }

    /**
     * 获取客户信息列表
     *
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public ResponseTable list(BasePageParamConsumer entity) {
        if (StringUtils.isNotBlank(entity.getAccessTimeDate())) {
            entity.setAccessTimeDates(entity.getAccessTimeDate().split(" ~ "));
        }
        PageInfo<ConsumerRecordEntity> p = consumerRecordService.list(entity);
        return ResponseTable.ofSuccess(p.getTotal(), p.getList());
    }


    /**
     * 保存客户信息
     *
     * @return
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public boolean save(ConsumerRecordEntity entity, HttpServletRequest request) {
        try {
            UserEntity userSession = userUtils.getUserSession(request.getSession());
            entity.setCreateUserId(userSession.getId());
            entity.setUpdateUserId(userSession.getId());
            consumerRecordService.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 删除记录
     *
     * @return
     */
    @RequestMapping("delete/{id}")
    @ResponseBody
    public Boolean delete(@PathVariable Long id) {
        try {
            consumerRecordService.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除记录（批量）
     *
     * @return
     */
    @RequestMapping("deleteBatch")
    @ResponseBody
    public Boolean deleteBatch(HttpServletRequest request) {
        try {
            String[] parameterValues = request.getParameterValues("ids");
            Arrays.stream(parameterValues).map(Long::valueOf).forEach(consumerRecordService::deleteById);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("check-data/{accessTimeDates}")
    @ResponseBody
    public Boolean checkData(@PathVariable String accessTimeDates) {
        if (StringUtils.isNotBlank(accessTimeDates)) {
            BasePageParamConsumer entity = new BasePageParamConsumer();
            entity.setAccessTimeDates(accessTimeDates.split(" ~ "));
            //按照条件获取
            return consumerRecordService.checkData(entity);
        }
        return false;
    }

    @RequestMapping("export-excel/{accessTimeDates}")
    public void exportExcel(HttpServletResponse response, @PathVariable String accessTimeDates) {
        if (StringUtils.isNotBlank(accessTimeDates)) {
            BasePageParamConsumer entity = new BasePageParamConsumer();
            entity.setAccessTimeDates(accessTimeDates.split(" ~ "));
            //按照条件获取
            List<ConsumerRecordExcelModel> list = consumerRecordService.getAllByTerm(entity);
            try {
                this.createTable(list, response, accessTimeDates.replaceAll(" ~ ", "到"));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private void createTable(List<ConsumerRecordExcelModel> list, HttpServletResponse response, String accessTimeDates) {
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(null, out, ExcelTypeEnum.XLSX, true, new StyleExcelConsumerRecordHandler());
            // 设置EXCEL名称
            String fileName = new String((accessTimeDates + "客户销售登记表").getBytes("gb2312"), "iso8859-1");
            // 设置SHEET名称
            Sheet sheet = new Sheet(1, 0, ConsumerRecordExcelModel.class);
            sheet.setSheetName("客户销售登记表");
            writer.write(list, sheet);
            response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + ".xlsx\"");
            response.setContentType("application/ms-excel");
            response.setCharacterEncoding("utf-8");
            writer.finish();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
