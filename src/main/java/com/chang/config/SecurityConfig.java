package com.chang.config;


import com.chang.enums.UserAndSecurityConstants;
import com.chang.handler.LoginFailureHandler;
import com.chang.handler.LoginSuccessHandler;
import com.chang.handler.MyAccessDeniedHandler;
import com.chang.service.impl.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/layui/**", "/js/**","/images/**"); //过滤静态资源
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()    //禁用SpringSecurity自带的跨域处理
                .authorizeRequests()
                .anyRequest().authenticated();    //其他请求需要认证
        http
                .formLogin()
                .loginPage(UserAndSecurityConstants.LOGIN_PAGE)    //需要登录时跳转的方法，也就是跳转到自定义登录页面URL
                .loginProcessingUrl(UserAndSecurityConstants.LOGIN_PROCESSING_URL)    //登陆处理路径
//                .failureUrl(UserAndSecurityConstants.FAILURE_URL)    //登录失败跳转的方法
                .successHandler(loginSuccessHandler())    //登录成功处理器
                .failureHandler(loginFailureHandler())    //登录失败处理器
                .permitAll()
                .and()
                .logout()
                .deleteCookies(UserAndSecurityConstants.JSESSION_ID, UserAndSecurityConstants.AUTOMATIC_LOGIN)
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);    //添加无权限时的处理
        http    //解决在项目里引入Spring Security后iframe或者frame所引用的页无法显示的问题
                .headers().frameOptions().sameOrigin();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    //做一个不加密的密码
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


}