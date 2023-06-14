package com.cms.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

        private final Logger log = LoggerFactory.getLogger(this.getClass());

    	@Before("execution(* com.cms.service.CourseServiceImpl.*(..))")
	public void logBefore(JoinPoint joinPoint) {
    		String methodName = joinPoint.getSignature().getName();
            log.info("Executing method: {}", methodName);
	}

    @AfterReturning("execution(* com.cms.service.CourseServiceImpl.*(..))")
	public void logAfterReturning(JoinPoint joinPoint) {
            String methodName = joinPoint.getSignature().getName();
            log.info("Method {} executed successfully", methodName);
    }
//    @AfterThrowing(pointcut = "execution(* com.cms.service.CourseServiceImpl.*(..))", throwing = "exception")
//    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
//            String methodName = joinPoint.getSignature().getName();
//           log.error("Exception in method {}: {}",methodName,exception.getMessage());
//
//    }
}