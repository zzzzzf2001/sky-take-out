package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DishExistException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static com.sky.constant.MessageConstant.DISH_ALREADY_EXISTS;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/24 14:43
 **/

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Resource
    private DishMapper dishMapper;
    @Resource
    private DishFlavorMapper dishFlavorMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    @Transactional
    public Result insert(DishDTO dishDTO) {
        Dish dish1 = new Dish();
        dish1.setId(dishDTO.getId());
        //首先要校验菜品名
       if (Objects.nonNull(dishMapper.selectDish(dish1))){
           throw new DishExistException(DISH_ALREADY_EXISTS);
       }

        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.insert(dish);

        Long dishId = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();
        flavors.forEach(dishFlavor ->{
            dishFlavor.setDishId(dishId);
        } );

        dishFlavorMapper.insertBatch(flavors);

        return Result.success();
    }

    @Override
    public PageResult selectpage(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.page(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public Result selectOneById(Long id) {
        Dish dish=new Dish();

        dish.setId(id);

        dish = dishMapper.selectDish(dish);
        Long categoryId = dish.getCategoryId();
        DishVO dishVO = new DishVO();

        BeanUtils.copyProperties(dish,dishVO);

        dishVO.setFlavors(dishFlavorMapper.getByDishId(id));
        dishVO.setCategoryName(categoryMapper.selectOne(categoryId));
        return Result.success(dishVO);
    }

    @Override
    public Result update(DishVO dishVO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishVO);


        return null;
    }
}
