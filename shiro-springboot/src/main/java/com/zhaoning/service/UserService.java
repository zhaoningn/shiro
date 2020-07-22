package com.zhaoning.service;

import com.zhaoning.pojo.User;

/**
 * @author zhaoning
 * @date 2020/7/21 - 16:11
 */
public interface UserService {
    public User queryUserByName(String name);
}
