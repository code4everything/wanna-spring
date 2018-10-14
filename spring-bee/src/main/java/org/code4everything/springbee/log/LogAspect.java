package org.code4everything.springbee.log;

import cn.hutool.core.util.RandomUtil;
import com.zhazhapan.util.AopLogUtils;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.DateUtils;
import com.zhazhapan.util.LoggerUtils;
import com.zhazhapan.util.interfaces.SimpleLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.code4everything.springbee.domain.Log;
import org.code4everything.springbee.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author pantao
 * @since 2018/10/13
 **/
@Aspect
@Component
public class LogAspect {

    private final HttpServletRequest request;

    private final SimpleLogService<Log> logService;

    private final RedisTemplate<String, Log> logRedisTemplate;

    private final RedisTemplate<String, User> userRedisTemplate;

    @Autowired
    public LogAspect(HttpServletRequest request, SimpleLogService<Log> logService,
                     RedisTemplate<String, Log> logRedisTemplate, RedisTemplate<String, User> userRedisTemplate) {
        this.request = request;
        this.logService = logService;
        this.logRedisTemplate = logRedisTemplate;
        this.userRedisTemplate = userRedisTemplate;
    }

    @Pointcut("@annotation(com.zhazhapan.util.annotation.AopLog)")
    public void serviceAspect() {}

    /**
     * 记录用户操作
     *
     * @param joinPoint {@link JoinPoint}
     */
    @Before("serviceAspect()")
    public void doBefore(JoinPoint joinPoint) {
        doAfterThrowing(joinPoint, null);
    }

    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String description = AopLogUtils.getDescriptionNoThrow(className, methodName, args);
        User user = userRedisTemplate.opsForValue().get(String.valueOf(request.getAttribute("token")));
        String uid = Checker.isNull(user) ? "anonymous" : user.getId();
        String ip = request.getRemoteAddr();
        Log log;
        String key = "log:" + uid;
        if (Checker.isNull(e)) {
            log = logService.save(getLog(uid, ip, className, methodName, description, args));
        } else {
            log = logRedisTemplate.opsForValue().get(key);
            if (Checker.isNull(log)) {
                log = getLog(uid, ip, className, methodName, description, args);
            }
            log = logService.saveException(log, e.getClass().getName(), e.getMessage());
        }
        if (Checker.isNotNull(log)) {
            logRedisTemplate.opsForValue().set(key, log, 1, TimeUnit.MINUTES);
            LoggerUtils.info(log.toString());
        }
    }

    private Log getLog(String userId, String ip, String className, String methodName, String description,
                       Object[] args) {
        Log log = new Log();
        log.setId(RandomUtil.simpleUUID());
        log.setArgs(Arrays.toString(args));
        log.setClassName(className);
        log.setMethodName(methodName);
        log.setCreateTime(DateUtils.getCurrentTimestamp());
        log.setIp(ip);
        log.setDescription(description);
        log.setUserId(userId);
        return log;
    }
}
