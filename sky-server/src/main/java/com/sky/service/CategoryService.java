package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.Result;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/23 09:16
 **/


public interface CategoryService {
    Result save(CategoryDTO categoryDTO);

    Result page(CategoryPageQueryDTO categoryPageQueryDTO);

    Result delete(Integer id);

    Result modify(CategoryDTO categoryDTO);
}
