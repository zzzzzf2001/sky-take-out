package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountExistException;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.sky.constant.PasswordConstant.DEFAULT_PASSWORD;
import static com.sky.constant.StatusConstant.ENABLE;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        //加密
        password= DigestUtils.md5DigestAsHex(password.getBytes());
        //密码比对
        // TODO 后期需要进行md5加密，然后再进行比对
        if (!password.equals(employee.getPassword())) {

            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }
    /**
     * @param employeeDTO 854848
     * @return boolean
     * @Description save
     * @author 15754
     * @Date 2023/5/22
     */
    @Override
    @Transactional
    public boolean save(EmployeeDTO employeeDTO) {

        if (Objects.isNull(employeeMapper.getByUsername(employeeDTO.getUsername()))){
                 throw new AccountExistException("账户已存在");
        }


        Employee employee=new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        employee.setPassword(DigestUtils.md5DigestAsHex(DEFAULT_PASSWORD.getBytes()));
        employee.setStatus(ENABLE);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.insert(employee);
        return true;
    }

    @Override
    public PageResult queryPage(String name, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        Page<Employee> employeePage = employeeMapper.getPageByName(name);

        PageResult pageResult=new PageResult();
        pageResult.setTotal(employeePage.getTotal());
        pageResult.setRecords(employeePage.getResult());

        return pageResult;
    }

    @Override
    public void changeStatus(Integer status, Long id) {

        Employee employee = Employee.builder()
                .status(status)
                .id(id)
                .build();

        employeeMapper.changeEmployee(employee);
    }

}
