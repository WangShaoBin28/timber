package com.chang.handler;

import com.chang.entity.UserEntity;
import com.chang.service.impl.MyUserDetailsService;
import com.chang.utils.HttpHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功处理器
 */
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Resource
	private MyUserDetailsService service;

	@Value("${userInfoSessionKey}")
	private String userInfoSessionKey;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		//根据账号密码获取当前登录用户
		UserEntity user = service.getByUsernameAndPwd(username,password);
		//将用户信息存在session中
		request.getSession().setAttribute(userInfoSessionKey,user);
		boolean ajaxRequest = HttpHelper.isAjaxRequest(request);
		if(ajaxRequest){
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.getWriter().println("{\"success\":true,\"message\":\"\"}");
			response.getWriter().flush();
		}else{
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}
	
}
