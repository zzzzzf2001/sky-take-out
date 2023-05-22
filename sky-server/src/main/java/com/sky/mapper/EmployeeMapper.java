package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    void insert(@Param("employee") Employee employee);

    Page<Employee> getPageByName(String name);

    void changeEmployee( Employee employee);

    Employee getEmplById(@Param("id") Long id);
}
