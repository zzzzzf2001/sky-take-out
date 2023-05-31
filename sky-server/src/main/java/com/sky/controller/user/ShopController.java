package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.sky.constant.RedisConstant.SHOP_STATUS;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/30 23:50
 **/


@RestController("userController")
@RequestMapping("user/shop")
@Api("用户端接口")
@Slf4j
public class ShopController {
    @Resource
    private RedisTemplate redisTemplate;



    @GetMapping("/status")
    @ApiOperation("获取营业状态")
    public Result getShopStatus(){
        return Result.success(redisTemplate.opsForValue().get(SHOP_STATUS));
    }




}
