package com.sky.Aspect;

import com.sky.annotation.AutoFill;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import static com.sky.constant.AutoFillConstant.*;
import static com.sky.enumeration.OperationType.UPDATE;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/23 16:35
 **/


@Component
@Aspect
@Slf4j
public class AutoFillAspect {
    //切面=切点+通知

    //切入点
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {}


    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint){
        log.info("公共字段填充");

        //获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取方法
        Method method = signature.getMethod();
        //获取方法上的注解
        AutoFill annotation = method.getAnnotation(AutoFill.class);
        //获取注解上的值
        OperationType operationType = annotation.value();
        //获取方法上的参数
        Object[] args = joinPoint.getArgs();
        //约定第一个参数就是Employee

        if(args==null||args.length==0){
            return;
        }
        Object entity=args[0];
        LocalDateTime time=LocalDateTime.now();
        Long  id= BaseContext.getCurrentId();

        if(operationType==UPDATE){
            try {
                Method setCreateTime = entity.getClass().getDeclaredMethod(SET_CREATE_TIME, LocalDateTime.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(SET_UPDATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(SET_CREATE_USER, Long.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(SET_UPDATE_USER, Long.class);
                setCreateTime.invoke(entity,time);
                setUpdateTime.invoke(entity,time);
                setCreateUser.invoke(entity,id);
                setUpdateUser.invoke(entity,id);
            } catch (Exception e) {
                log.info("公共字段填充失败:{}",e.getMessage());
            }
        }

        else {
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(SET_UPDATE_USER, Long.class);
                setUpdateTime.invoke(entity,time);
                setUpdateUser.invoke(entity,id);
            } catch (Exception e) {
                log.info("公共字段填充失败:{}",e.getMessage());
            }
        }
    }


}
