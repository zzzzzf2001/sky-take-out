package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import com.sky.vo.UserLoginVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

import static com.sky.constant.WechatConstant.WX_GET_OPENID_URL;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/31 15:33
 **/

@Service
public class UserServiceImpl implements UserService {


    @Resource
    WeChatProperties weChatProperties;


    @Resource
    UserMapper userMapper;

    @Override
    public User login(UserLoginDTO userLoginDTO) {
        //获取授权码
        String code = userLoginDTO.getCode();

        //使用httpclient对象，想微信第三方接口发送请求，获取openid
        HashMap<String, String> param = new HashMap<>();

        param.put("appid", weChatProperties.getAppid());
        param.put("secret", weChatProperties.getSecret());
        param.put("js_code", code);
        param.put("grant_type", "authorization_code");
        String result = HttpClientUtil.doGet(WX_GET_OPENID_URL, param);
        //判断Openid是否拿到，如果没有拿到说明前端没有登录
        //获取响应中的openid
        JSONObject jsonObject = JSONObject.parseObject(result);
        String openid = (String) jsonObject.get("openid");
        if (StringUtils.isBlank(openid)) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED); //如果没有openid则说明有问踢
        }
        UserLoginVO userLoginVO = new UserLoginVO();
        User userDB = userMapper.getOneByOpenid(openid);
        //到数据库中判断openid是否存在
        //如果不存在则为第一次登录则要注册到User表中
        if (Objects.isNull(userDB)) {
            User user = User.builder().openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.register(user);
            return user;
        }
        return userDB;

    }

}

