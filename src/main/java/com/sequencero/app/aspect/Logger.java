package com.sequencero.app.aspect;

import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Log
@Component
@Aspect
public class Logger {

    @Before("execution(* com.sequencero.app.controller..*.*(..)) || execution(* com.sequencero.app.repository..*.* (..))")
    public void printBefore(JoinPoint joinPoint) {
        log.info(joinPoint.getTarget().getClass().getSimpleName()
                + " -> " + joinPoint.getSignature().getName() + " with arguments "
                + Arrays.toString(joinPoint.getArgs()));
    }
}
