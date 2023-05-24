package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/23 09:18
 **/


@RestController
@RequestMapping("/admin")
@Slf4j
@Api("菜品接口")
public class CategoryController {

    @Resource
    private CategoryService categoryService;



    @PostMapping("/category")
    @ApiOperation("新增分类")
    public Result saveCategory(@RequestBody CategoryDTO categoryDTO){
        return     categoryService.save(categoryDTO);
    }

    @GetMapping("/category/page")
    @ApiOperation("分类查询")
    public Result page(CategoryPageQueryDTO categoryPageQueryDTO){
        return categoryService.page(categoryPageQueryDTO);
    }

    @DeleteMapping
    @ApiOperation("删除分类")
    public Result delete(@RequestParam("id") Integer id){
       return categoryService.delete(id);
    }

    @PutMapping("/category")
    @ApiOperation("修改分类")
    public Result modify(@RequestBody CategoryDTO categoryDTO){
        return categoryService.modify(categoryDTO);
    }

    @PostMapping("/category/status/{status}")
    @ApiOperation("修改状态")
    public Result modifyStatus(@PathVariable("status") int status,@RequestParam("id") Long id){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(id);
        categoryDTO.setStatus(status);
        return categoryService.modify(categoryDTO);
    }
    @GetMapping("/category/list")
    @ApiOperation("根据类型查询分类")
    public Result showContentByType(@RequestParam("type") int type){
        return categoryService.showCategory(type);
    }




}
