package com.chang.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * 403处理器
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request,
			HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		String i = "{\"code\": 403,\"message\": \"You have no right to visit!\",\"data\": \"\"}";
		response.getWriter().println(i);
		response.getWriter().flush();
	}


}


