package com.cms.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingAspect {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Around("execution(* com.yourpackage.AssociateService.*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        
        

        try {
            Object result = joinPoint.proceed();
            log.info("The method {} has completed successfully", methodName);
            return result;
        } catch (Exception e) {
            log.error("Exception occurred in method {}: {}", methodName, e.getMessage());
            throw e;
        }
    }
}