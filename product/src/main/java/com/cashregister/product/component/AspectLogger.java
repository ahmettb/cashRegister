package com.cashregister.product.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AspectLogger {



private final Logger logger= LogManager.getLogger();

@Before("execution(* com.cashregister.product.service..*(..))")
public  void beforeMethodCall(JoinPoint joinPoint)
{
    logger.trace("{} - {} start...",joinPoint.toShortString(), Arrays.toString(joinPoint.getArgs()));
}



@Before("execution(* com.cashregister.product.service..*(..))")
public void afterMethodCall(JoinPoint joinPoint)
{
    logger.trace("{} - {} end...",joinPoint.toShortString(),Arrays.toString(joinPoint.getArgs()));
}



}
