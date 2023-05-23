package com.sky.annotation;


import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//注解适用范围
@Target(ElementType.METHOD)
//注解保留到何时
@Retention(RetentionPolicy.RUNTIME)

public @interface AutoFill {
    //属性 用来记录要操作的类型
      OperationType value();


}

