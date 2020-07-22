package com.zhaoning.service;

import com.zhaoning.mapper.UserMapper;
import com.zhaoning.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhaoning
 * @date 2020/7/21 - 16:12
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public User queryUserByName(String name) {
        return userMapper.queryUserByName(name);
    }
}
