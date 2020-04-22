package com.chang.controller;


import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.chang.entity.SelfRecordEntity;
import com.chang.entity.UserEntity;
import com.chang.handler.StyleExcelSelfRecordHandler;
import com.chang.model.SelfRecordExcelModel;
import com.chang.param.BasePageParamSelf;
import com.chang.service.ISelfRecordService;
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
import java.util.List;

/**
 * @ClassName SelfRecordController
 * @Description
 * @Author yj.c
 * @Date 2019/10/12 10:23
 * @Version 1.0
 **/
@Controller
@RequestMapping("/self-record")
public class SelfRecordController {

    @Resource
    private UserUtils userUtils;

    @Resource
    private ISelfRecordService selfRecordService;



    @RequestMapping("index")
    public String getIndex() {
        return "s-index";
    }

    @RequestMapping("detail/{id}")
    public String detail(@PathVariable Long id, Model m) {
        SelfRecordEntity entity = selfRecordService.getById(id);
        m.addAttribute("e", entity);
        return "self-record-detail";
    }

    @RequestMapping("to-excel")
    public String toExcel() {
        return "self-record-excel";
    }

    @RequestMapping("to-update/{id}")
    public String toUpdate(@PathVariable Long id, Model m) {
        SelfRecordEntity entity = selfRecordService.getById(id);
        m.addAttribute("e", entity);
        return "self-record-update";
    }


    @RequestMapping("to-insert")
    public String toInsert(Model m) {
        m.addAttribute("e", new SelfRecordEntity());
        return "self-record-update";
    }

    /**
     * 获取客户信息列表
     *
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public ResponseTable list(BasePageParamSelf entity) {
        if (StringUtils.isNotBlank(entity.getAccessTimeDate())) {
            entity.setAccessTimeDates(entity.getAccessTimeDate().split(" ~ "));
        }
        PageInfo<SelfRecordEntity> p = selfRecordService.list(entity);
        return ResponseTable.ofSuccess(p.getTotal(), p.getList());
    }


    /**
     * 保存客户信息
     *
     * @return
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public boolean save(SelfRecordEntity entity, HttpServletRequest request) {
        try {
            UserEntity userSession = userUtils.getUserSession(request.getSession());
            entity.setCreateUserId(userSession.getId());
            entity.setUpdateUserId(userSession.getId());
            selfRecordService.save(entity);
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
            selfRecordService.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除记录(批量)
     *
     * @return
     */
    @RequestMapping("deleteBatch")
    @ResponseBody
    public Boolean deleteBatch(HttpServletRequest request) {
        try {
            String[] parameterValues = request.getParameterValues("ids");
            Arrays.stream(parameterValues).map(Long::valueOf).forEach(selfRecordService::deleteById);
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
            BasePageParamSelf entity = new BasePageParamSelf();
            entity.setAccessTimeDates(accessTimeDates.split(" ~ "));
            //按照条件获取
            return selfRecordService.checkData(entity);
        }
        return false;
    }

    @RequestMapping("export-excel/{accessTimeDates}")
    public void exportExcel(HttpServletResponse response, @PathVariable String accessTimeDates) {
        if (StringUtils.isNotBlank(accessTimeDates)) {
            BasePageParamSelf entity = new BasePageParamSelf();
            entity.setAccessTimeDates(accessTimeDates.split(" ~ "));
            //按照条件获取
            List<SelfRecordExcelModel> list = selfRecordService.getAllByTerm(entity);
            try {
                this.createTable(list, response, accessTimeDates.replaceAll(" ~ ", "到"));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private void createTable(List<SelfRecordExcelModel> list, HttpServletResponse response, String accessTimeDates) {
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(null, out, ExcelTypeEnum.XLSX, true, new StyleExcelSelfRecordHandler());
            // 设置EXCEL名称
            String fileName = new String((accessTimeDates + "个人登记表").getBytes("gb2312"), "iso8859-1");
            // 设置SHEET名称
            Sheet sheet = new Sheet(1, 0, SelfRecordExcelModel.class);
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
