package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    Setmeal selectByidOrname(Setmeal setmeal);
    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);

    void InsertSetmealDishes(@Param("setmealDishes") List<SetmealDish> setmealDishes);

    Page<Setmeal> page(Setmeal setmeal);

    @AutoFill(OperationType.UPDATE)
    void changeStatus(Integer status, Long id);


    List<SetmealDish> selectSetmealDishByDishId(@Param("dishId") Long dishId);

    void deleteBatch(@Param("ids") List<Long> ids);

    void deleteBatchBySetMealId(@Param("ids") List<Long> ids);

    Integer selectStatus(@Param("ids") List<Long> ids);
    /**
     * 动态条件查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据套餐id查询菜品选项
     * @param setmealId
     * @return
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);

}
