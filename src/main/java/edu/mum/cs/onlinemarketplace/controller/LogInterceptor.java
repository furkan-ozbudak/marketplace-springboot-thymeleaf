package edu.mum.cs.onlinemarketplace.controller;

import edu.mum.cs.onlinemarketplace.domain.Log;
import edu.mum.cs.onlinemarketplace.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
public class LogInterceptor {
    @Autowired
    LogService logService;

    @Pointcut("@annotation(edu.mum.cs.onlinemarketplace.controller.LogAnnotation)")
    public void annotation() {
    }

    @Before("annotation()")
    public void log(JoinPoint joinPoint) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        Log log = new Log();
        log.setDateTime(formattedDateTime);
        log.setAction(joinPoint.getSignature().toShortString());
        logService.saveLog(log);
    }
}
