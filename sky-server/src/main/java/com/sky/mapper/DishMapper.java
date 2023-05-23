package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/23 11:40
 **/

@Mapper
public interface DishMapper {

    Integer countCategoryId(@Param("categoryId") int categoryId);
}
