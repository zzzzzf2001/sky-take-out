package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.Result;

import java.util.List;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/24 22:03
 **/


public interface SetmealService {
    Result insert(SetmealDTO setmealDTO);

    Result page(SetmealPageQueryDTO setmealPageQueryDTO);

    Result changeStatus(Integer status, Long id);

    Result selectInfo(Long id);

    Result delete(List<Long> ids);
}
