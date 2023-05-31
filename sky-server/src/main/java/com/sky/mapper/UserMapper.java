package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/31 15:52
 **/

@Mapper
public interface UserMapper {
    User getOneByOpenid(String openid);

    void register(User user);
}
