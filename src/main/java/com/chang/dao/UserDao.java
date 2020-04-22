package com.chang.dao;

import com.chang.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName UserDao
 * @Description
 * @Author yj.c
 * @Date 2019/10/18 14:05
 * @Version 1.0
 **/
@Mapper
public interface UserDao {

    UserEntity loadUserByUsername(String accountNumber);

    UserEntity getByUsernameAndPwd(@Param("username") String username, @Param("password") String password);
}
