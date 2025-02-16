package com.yyq.aspect;


import com.yyq.annotation.AutoFill;
import com.yyq.common.constant.AutoFillConstant;
import com.yyq.common.context.BaseContext;
import com.yyq.common.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面类，用于实现公共字段自动填充的逻辑
 */
@Aspect
@Component
@Slf4j
public class AtuoFillAspect {
    /**
     * 切入点
     */
    @Pointcut("execution(* com.yyq.mapper.*.*(..)) && @annotation(com.yyq.annotation.AutoFill)")
    public void autoFillPonitCut() {}

    /**
     * 前置通知,在通知中为公共字段赋值
     */
    @Before("autoFillPonitCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始进行公共字段自动填充");
        // 获取当前被拦截的方法上的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature(); //获取方法签名
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class); //获取方法上的注解对象
        OperationType operationType = autoFill.value(); //获取数据库操作类型
        // 获取当前被拦截的方法的参数--实体对象
        Object[] args = joinPoint.getArgs();
        if (args== null || args.length == 0) {
            return;
        }
        Object entity = args[0];
        // 准备赋值数据
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        // 根据数据库操作类型，通过反射为实体对象对应的公共字段赋值
        if(operationType == OperationType.INSERT){
            try {
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                //通过反射为实体对象对应的公共字段赋值
                setCreateTime.invoke(entity, now);
                setCreateUser.invoke(entity, currentId);
                setUpdateUser.invoke(entity, currentId);
                setUpdateTime.invoke(entity, now);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (operationType == OperationType.UPDATE) {
            try {

                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                //通过反射为实体对象对应的公共字段赋值
                setUpdateUser.invoke(entity, currentId);
                setUpdateTime.invoke(entity, now);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
