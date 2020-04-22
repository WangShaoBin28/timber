package com.chang.utils;

import com.chang.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * @ClassName UserUtils
 * @Description
 * @Author yj.c
 * @Date 2019/10/21 13:33
 * @Version 1.0
 **/
@Component
public class UserUtils {

    @Value("${userInfoSessionKey}")
    private String userInfoSessionKey;

    //获取session用户
    public UserEntity getUserSession(HttpSession session){
        return  (UserEntity)session.getAttribute(userInfoSessionKey);
    }

    public void deleteUserSession(HttpSession session){
        session.removeAttribute(userInfoSessionKey);
    }

}
