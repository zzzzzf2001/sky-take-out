package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/23 11:40
 **/

@Mapper
public interface DishMapper {

    Integer countCategoryId(@Param("categoryId") int categoryId);

    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);

    Dish selectDish(Dish dish);

    Page<DishVO> page(DishPageQueryDTO dishPageQueryDTO);

    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    Integer deleteBatch(@Param("ids") List<Integer> ids);
}
