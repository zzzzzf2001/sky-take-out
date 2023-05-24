package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/24 14:15
 **/


@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api("菜品相关操作")
public class DishController {

    @Resource
    private DishService dishService;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result SaveWithFlavor(@RequestBody DishDTO dishDTO){
        log.info("新增菜品:{}",dishDTO);
        return   dishService.insert(dishDTO);
    }
    @GetMapping("page")
    @ApiOperation("菜品分页查询")
    public Result selectPage(DishPageQueryDTO dishPageQueryDTO){
        return Result.success(dishService.selectpage(dishPageQueryDTO));
    }
    @GetMapping("{id}")
    @ApiOperation("根据id查询菜品")
    public Result selectDishById(@PathVariable("id") Long id){
       return dishService.selectOneById(id);
    }

    @PutMapping
    @ApiOperation("修改菜品")
    public Result updateDish(@RequestBody DishDTO dishVO){
        return dishService.update(dishVO);
    }

    @DeleteMapping
    @ApiOperation("批量删除菜品")

    public Result deleteDish(@RequestParam("id") List<Integer> ids){

        return  dishService.deleteBatch(ids);
    }

}
