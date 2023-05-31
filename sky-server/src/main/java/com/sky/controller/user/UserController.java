package com.sky.controller.user;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.sky.constant.JwtClaimsConstant.USER_ID;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/31 15:29
 **/



@RestController
@RequestMapping("/user/user")
@Api("用户接口")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    JwtProperties jwtProperties;




    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("开始小程序端登录");

        User user = userService.login(userLoginDTO);

        System.out.println(user.getId()+"=================》");
        Map<String, Object> claims=new HashMap<>();

        claims.put(USER_ID,user.getId());

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims)).build();

        return Result.success(userLoginVO);
    }


}
