package com.sky.controller.admin;

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


@RestController
@RequestMapping("admin/shop")
@Api("店铺操作接口")
@Slf4j
public class ShopController {
    @Resource
    private RedisTemplate redisTemplate;



    @GetMapping("/status")
    @ApiOperation("获取营业状态")
    public Result getShopStatus(){
        return Result.success(redisTemplate.opsForValue().get(SHOP_STATUS));
    }



    @PutMapping("/{status}")
    @ApiOperation("设置营业状态")
    public Result settingStatus(@PathVariable("status") Integer status){

        redisTemplate.opsForValue().set(SHOP_STATUS,status);

        return  Result.success();
    }


}
