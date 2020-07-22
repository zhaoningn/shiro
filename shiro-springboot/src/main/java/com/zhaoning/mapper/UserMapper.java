package com.zhaoning.mapper;

import com.zhaoning.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author zhaoning
 * @date 2020/7/21 - 16:04
 */

@Repository
@Mapper
public interface UserMapper {

    public User queryUserByName(String name);
}
