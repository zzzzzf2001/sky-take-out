package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/24 15:10
 **/


@Mapper
public interface DishFlavorMapper {
    void insertBatch(List<DishFlavor> flavors);

    @Select("select * from sky_take_out.dish_flavor where dish_id=#{id}")
    List<DishFlavor> getByDishId(@Param("id") Long id);
}
