package com.sky.mapper;

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
}
