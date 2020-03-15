package com.crazy.service.impl;

import com.crazy.mapper.UserMapper;
import com.crazy.pojo.User;
import com.crazy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User queryUserByName(String username) {
        return userMapper.queryUserByName(username);
    }
}
