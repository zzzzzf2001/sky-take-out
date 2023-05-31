package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/31 15:33
 **/


public interface UserService {
    public User login(UserLoginDTO userLoginDTO);
}
