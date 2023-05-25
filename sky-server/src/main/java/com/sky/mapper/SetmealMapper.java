package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/24 22:05
 **/


@Mapper
public interface SetmealMapper {


    List<Integer> getIdByDishId(@Param("ids") List<Integer> ids);

    void deleteBatchById(@Param("setmealDishIds") List<Integer> setmealDishIds);

    Setmeal selectByidOrname(Setmeal setmeal);
    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);

    void InsertSetmealDishes(@Param("setmealDishes") List<SetmealDish> setmealDishes);

    Page<Setmeal> page(Setmeal setmeal);

    @AutoFill(OperationType.UPDATE)
    void changeStatus(Integer status, Long id);


    List<SetmealDish> selectSetmealDishByDishId(@Param("dishId") Long dishId);
}
