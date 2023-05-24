package com.sky.service.impl;

import com.sky.mapper.DishFlavorMapper;
import com.sky.service.DishFlavorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/24 15:09
 **/

@Service
public class DishFlavorServiceImpl implements DishFlavorService {
    @Resource
    private DishFlavorMapper dishFlavorMapper;
}
