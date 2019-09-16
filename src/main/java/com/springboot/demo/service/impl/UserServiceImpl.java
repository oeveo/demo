package com.springboot.demo.service.impl;

import com.springboot.demo.entity.User;
import com.springboot.demo.entity.UserExample;
import com.springboot.demo.mapper.UserMapper;
import com.springboot.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    // 1.添加用户
    @Override
    public void insert(User account) {
        userMapper.insert(account);
    }

    // 2.激活用户
    @Override
    public void activeUser(String email) {
        userMapper.activeUser(email);
    }

    // 3.用户登录
    @Override
    public User login(String email) {
        return userMapper.userLogin(email);
    }

    @Override
    public void updateUser(User user) {
        UserExample userExample=new UserExample();
        UserExample.Criteria criteria=userExample.createCriteria();
        criteria.andEmailEqualTo(user.getEmail());
        userMapper.updateByExampleSelective(user,userExample);
    }

}
