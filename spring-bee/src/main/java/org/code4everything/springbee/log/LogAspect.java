package org.code4everything.springbee.log;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.code4everything.boot.constant.StringConsts;
import org.code4everything.boot.log.AopLogUtils;
import org.code4everything.boot.service.LogService;
import org.code4everything.springbee.domain.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author pantao
 * @since 2018/10/13
 **/
@Aspect
@Component
public class LogAspect {

    private final LogService<Log> logLogService;

    private final HttpServletRequest request;

    @Autowired
    public LogAspect(LogService<Log> logLogService, HttpServletRequest request) {
        this.logLogService = logLogService;
        this.request = request;
    }

    @Pointcut("@annotation(org.code4everything.boot.annotations.AopLog)")
    public void serviceAspect() {
        // do nothing
    }

    /**
     * 记录用户操作
     *
     * @param joinPoint {@link JoinPoint}
     */
    @Before("serviceAspect()")
    public void doBefore(JoinPoint joinPoint) {
        doAfterThrowing(joinPoint, null);
    }

    @AfterThrowing(pointcut = "serviceAspect()", throwing = "throwable")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        // 定义日志缓存
        Cache<String, Log> cache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.SECONDS).build();
        // 定义缓存键
        String key = request.getHeader(StringConsts.TOKEN) + Thread.currentThread().getId();
        AopLogUtils.saveLog(logLogService, cache, key, joinPoint, throwable);
    }
}
