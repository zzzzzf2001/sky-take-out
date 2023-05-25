package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/25 17:10
 **/


@RestController
@Slf4j
@Api("套餐管理")
@RequestMapping("/admin/setmeal")
public class SetmealController {

    @Resource
    private SetmealService setmealService;

    @PostMapping
    @ApiOperation("新增套餐")
    public Result insert(@RequestBody SetmealDTO setmealDTO){
       return setmealService.insert(setmealDTO);
    }

    @GetMapping("page")
    @ApiOperation("分页条件查询套餐")
    public Result page(SetmealPageQueryDTO setmealPageQueryDTO){
           return setmealService.page(setmealPageQueryDTO);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("改变起售、禁售状态")
    public Result page(@PathVariable("status") Integer status,@RequestParam("id") Long id){
        return setmealService.changeStatus(status,id);
    }
}
