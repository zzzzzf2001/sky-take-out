package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.CategoryHasException;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.sky.constant.MessageConstant.CATEGORY_ALREADY_EXISTS;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/23 09:17
 **/


@Service
@Slf4j

public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private DishMapper dishMapper;

    @Override
    @Transactional
    public Result save(CategoryDTO categoryDTO) {

        String name = categoryDTO.getName();
        if (StringUtils.isBlank(name)){
            throw new CategoryHasException(CATEGORY_ALREADY_EXISTS);
        }

        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        category.setStatus(1);

        categoryMapper.save(category);

        return Result.success("插入分类成功");
    }

    @Override
    public Result page(CategoryPageQueryDTO categoryPageQueryDTO) {

        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());

        Page<Category> page = categoryMapper.page(categoryPageQueryDTO.getName(), categoryPageQueryDTO.getType());
        PageResult result=new PageResult();
        result.setTotal(page.getTotal());
        result.setRecords(page.getResult());

        return Result.success(result);
    }

    @Override
    @Transactional
    public Result delete(Integer id) {
        if(dishMapper.countCategoryId(id)>0){
            throw new DeletionNotAllowedException("该分类的菜品已存在不可删除");
        }
        if (!categoryMapper.delete(id)){
            throw new DeletionNotAllowedException("分类未被禁用或数据有误");
        }
        return Result.success("删除成功");
    }

    @Override
    public Result modify(CategoryDTO categoryDTO) {


        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
//        category.setUpdateUser(BaseContext.getCurrentId());
//        category.setUpdateTime(LocalDateTime.now());
//        System.out.println(category.getId());
        categoryMapper.modify(category);

        return Result.success();
    }

    @Override
    public Result showCategory(int type) {
        return Result.success( categoryMapper.showCategory(type));
    }
}
