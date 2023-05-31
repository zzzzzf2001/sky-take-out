package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.mapper.DishMapper;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

import static com.sky.constant.RedisConstant.DISH;

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
    @Resource
    private DishMapper dishMapper;
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RedisUtils redisUtils;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result SaveWithFlavor(@RequestBody DishDTO dishDTO){
        log.info("新增菜品:{}",dishDTO);
        redisUtils.del(DISH+dishDTO.getCategoryId());
        Result insert = dishService.insert(dishDTO);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        redisUtils.del(DISH+dishDTO.getCategoryId());
        return  insert ;
    }
    @GetMapping("page")
    @ApiOperation("菜品分页查询")
    public Result<com.sky.result.PageResult> selectPage(DishPageQueryDTO dishPageQueryDTO){
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
        redisUtils.del(DISH+dishVO.getCategoryId());
        return dishService.update(dishVO);
    }

    @DeleteMapping
    @ApiOperation("批量删除菜品")

    public Result deleteDish(@RequestParam("id") List<Integer> ids){
        cleanCache("dish*");
        return  dishService.deleteBatch(ids);
    }


    @PostMapping("/status/{status}")
    @ApiOperation("根据id修改菜品的起售禁售状态")
    public Result changeStatus(@PathVariable("status") Integer status,@RequestParam("id") long id){
        cleanCache("dish*");
        return dishService.changeStatus(status,id);
    }
    @GetMapping("/list")
    @ApiOperation("根据分类ID查询菜品")
    public Result<List<Dish>> selectListDish(@RequestParam("categoryId")  Long categoryId){
        Dish dish=new Dish();
        dish.setCategoryId(categoryId);
     return   Result.success(dishMapper.selectDishList(dish));
    }

    public void cleanCache(String pattern){
        Set<String> keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

}
