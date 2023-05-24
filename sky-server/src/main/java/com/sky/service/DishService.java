package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.vo.DishVO;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/24 14:43
 **/


public interface DishService {
    Result insert(DishDTO dishDTO);

    PageResult selectpage(DishPageQueryDTO dishPageQueryDTO);

    Result selectOneById(Long id);

    Result update(DishVO dishVO);
}
