package com.chang.controller;

import com.chang.entity.ConsumerInfoEntity;
import com.chang.entity.UserEntity;
import com.chang.param.BasePageParamConsumer;
import com.chang.service.IConsumerInfoService;
import com.chang.utils.ResponseTable;
import com.chang.utils.UserUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ClassName ConsumerInfoController
 * @Description
 * @Author yj.c
 * @Date 2019/10/9 13:59
 * @Version 1.0
 **/
@Controller
@RequestMapping("/consumer-info")
public class ConsumerInfoController {

    @Resource
    private IConsumerInfoService consumerInfoService;

    @Resource
    private UserUtils userUtils;


    @RequestMapping("index")
    public String getIndex(){
        return "index";
    }


    @RequestMapping("detail/{id}")
    public String detail(@PathVariable Long id, Model m){
        ConsumerInfoEntity entity = consumerInfoService.getById(id);
        m.addAttribute("e",entity);
        return "consumer-detail";
    }

    @RequestMapping("to-update/{id}")
    public String toUpdate(@PathVariable Long id, Model m){
        ConsumerInfoEntity entity = consumerInfoService.getById(id);
        m.addAttribute("e",entity);
        return "consumer-update";
    }


    @RequestMapping("to-insert")
    public String toInsert(Model m){
        m.addAttribute("e",new ConsumerInfoEntity());
        return "consumer-update";
    }



    /**
     * 获取客户信息列表
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public ResponseTable list(BasePageParamConsumer entity){
        PageInfo<ConsumerInfoEntity> p = consumerInfoService.list(entity);
        return ResponseTable.ofSuccess(p.getTotal(),p.getList());
    }



    /**
     * 保存客户信息
     * @return
     */
    @RequestMapping(value="save")
    @ResponseBody
    public boolean save(ConsumerInfoEntity entity,HttpServletRequest request){
        try {
            UserEntity userSession = userUtils.getUserSession(request.getSession());
            entity.setCreateUserId(userSession.getId());
            entity.setUpdateUserId(userSession.getId());
            consumerInfoService.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    /**
     * 判断客户是否可以删除
     * 如果有消费记录不能删除
     * @return
     */
    @RequestMapping("checkConsumerInfo/{id}")
    @ResponseBody
    public Boolean checkConsumerInfo(@PathVariable Long id){
        return consumerInfoService.checkConsumerInfo(id);
    }

    /**
     * 删除记录
     * @return
     */
    @RequestMapping("delete/{id}")
    @ResponseBody
    public Boolean delete(@PathVariable Long id){
        try {
            consumerInfoService.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }





}
