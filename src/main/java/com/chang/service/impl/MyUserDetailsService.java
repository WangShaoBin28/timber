package com.chang.service.impl;

import com.chang.dao.UserDao;
import com.chang.entity.UserEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Optional;


@Service("myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService{

	@Resource
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String accountNumber)
			throws UsernameNotFoundException {
		UserEntity sysUser = userDao.loadUserByUsername(accountNumber);
		return Optional.ofNullable(sysUser).map(x -> new User(accountNumber, sysUser.getPassword(), new HashSet<SimpleGrantedAuthority>())).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
	}

	public UserEntity getByUsernameAndPwd(String username, String password) {
		return  userDao.getByUsernameAndPwd(username,password);


	}
}
