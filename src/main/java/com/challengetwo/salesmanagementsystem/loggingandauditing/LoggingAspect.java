package com.challengetwo.salesmanagementsystem.loggingandauditing;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

//    private final ObjectMapper objectMapper;
//
//    @Autowired
//    public LoggingAspect(ObjectMapper objectMapper) {
//        this.objectMapper = objectMapper;
//    }

    @Pointcut("execution(* com.challengetwo.salesmanagementsystem.salesmanagement.service.SalesService.*(..))")
    public void salesServicePointCut() {}

    @Pointcut("execution(* com.challengetwo.salesmanagementsystem.clientmanagement.service.UserService.*(..))")
    public void clientServicePointCut() {}

    @Pointcut("execution(* com.challengetwo.salesmanagementsystem.productmanagement.service.ProductService.*(..))")
    public void productServicePointCut() {}

    @Pointcut("execution(* com.challengetwo.salesmanagementsystem.reporting.service.ReportService.*(..))")
    public void reportServicePointCut() {}

    @Around("salesServicePointCut() || clientServicePointCut() || productServicePointCut() || reportServicePointCut()")
    public Object applicationLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().toString();
        Object [] methodArguments = joinPoint.getArgs();
        logger.info("Method invoked : ClassName = " + className + ": MethodName = " + methodName + ": methodArguments = "
                + Arrays.toString(methodArguments));
        Object responseObject = joinPoint.proceed();
        logger.info("Method invoked : ClassName = " + className + ": MethodName = " + methodName + ": methodResponse = "
                + responseObject);
        return responseObject;
    }



//    private final LoggingService loggingService;
//
//    public LoggingAspect(LoggingService loggingService) {
//        this.loggingService = loggingService;
//    }
//
////    @Before("execution(* com.challengetwo.salesmanagementsystem.*.*.*(..))")
////    public void logBefore(JoinPoint joinPoint) {
////        loggingService.logActivity(joinPoint.getSignature().getName(), "Before");
////    }
//
//    @After("execution(* com.challengetwo.salesmanagementsystem.*.*.*(..))")
//    public void logAfter(JoinPoint joinPoint) {
//        loggingService.logActivity(joinPoint.getSignature().getName(), "After");
//    }
//
//    @AfterReturning(pointcut = "execution(* com.challengetwo.salesmanagementsystem.*.*.*(..))", returning = "result")
//    public void logAfterReturning(JoinPoint joinPoint, Object result) {
//        loggingService.logActivity(joinPoint.getSignature().getName(), "AfterReturning with result: " + result);
//    }
//
//    @AfterThrowing(pointcut = "execution(* com.challengetwo.salesmanagementsystem.*.*.*(..))", throwing = "exception")
//    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
//        loggingService.logActivity(joinPoint.getSignature().getName(), "AfterThrowing with exception: " + exception.getMessage());
//    }

}
