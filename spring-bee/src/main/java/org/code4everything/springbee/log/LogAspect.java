package org.code4everything.springbee.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.code4everything.boot.log.AopLogUtils;
import org.code4everything.boot.service.LogService;
import org.code4everything.springbee.SpringBeeApplication;
import org.code4everything.springbee.domain.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pantao
 * @since 2018/10/13
 **/
@Aspect
@Component
public class LogAspect {

    private final LogService<Log> logService;

    private final HttpServletRequest request;

    @Autowired
    public LogAspect(LogService<Log> logLogService, HttpServletRequest request) {
        this.logService = logLogService;
        this.request = request;
    }

    @Pointcut("@annotation(org.code4everything.boot.annotations.AopLog)")
    public void serviceAspect() {
        // do nothing
    }

    @Around("serviceAspect()")
    public Object doAround(ProceedingJoinPoint point) {
        boolean shouldSaveLog = Boolean.TRUE.equals(SpringBeeApplication.getBeeConfigBean().getShouldSaveLog());
        return AopLogUtils.saveLog(logService, point, shouldSaveLog).getResult();
    }
}
