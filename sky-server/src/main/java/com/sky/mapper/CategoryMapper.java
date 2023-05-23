package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/23 09:22
 **/


@Mapper
public interface CategoryMapper {

     @AutoFill(OperationType.INSERT)
     void save(Category category);



     Integer getByName(String name);

     Page<Category> page(@Param("name") String name, @Param("type") Integer type);

     @Delete("delete  from  sky_take_out.category where id=#{id} and status!=1")
     boolean delete(Integer id);
     @AutoFill(OperationType.UPDATE)
     void modify(Category category);
}
