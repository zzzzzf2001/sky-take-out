package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.SetmealDeleteException;
import com.sky.exception.SetmealInsertException;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static com.sky.constant.MessageConstant.SETMEAL_ALREADY_EXISTS;
import static com.sky.constant.MessageConstant.SETMEAL_ON_SALE;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/24 22:03
 **/

@Service
public class SetmealServiceImpl implements SetmealService {

    @Resource
    private SetmealMapper setmealMapper;


    @Override
    @Transactional
    public Result insert(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        //查重
        if (Objects.nonNull(setmealMapper.selectByidOrname(setmeal))){
            throw new SetmealInsertException(SETMEAL_ALREADY_EXISTS);
        }

        setmealMapper.insert(setmeal);

        Long id = setmeal.getId();

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(id);
        });

        setmealMapper.InsertSetmealDishes(setmealDishes);

        return Result.success();
    }

    @Override
    public Result page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealPageQueryDTO,setmeal);
        Page<Setmeal> page =setmealMapper.page(setmeal);
        PageResult result=new PageResult();
        result.setTotal(page.getTotal());
        result.setRecords(page.getResult());
        return Result.success(result);
    }

    @Override
    public Result changeStatus(Integer status, Long id) {
        setmealMapper.changeStatus(status,id);
        return Result.success();
    }

    @Override
    public Result selectInfo(Long id) {
        Setmeal setmeal = new Setmeal();
        setmeal.setId(id);

         setmeal = setmealMapper.selectByidOrname(setmeal);

        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal,setmealVO);

        setmealVO.setSetmealDishes(setmealMapper.selectSetmealDishByDishId(id));

        return  Result.success(setmealVO);
    }

    @Override
    @Transactional
    public Result delete(List<Long> ids) throws SetmealDeleteException {

            if (setmealMapper.selectStatus(ids)!=0) {
                throw new SetmealDeleteException(SETMEAL_ON_SALE);
            }

        setmealMapper.deleteBatch(ids);

        setmealMapper.deleteBatchBySetMealId(ids);

        return Result.success();
    }
}
