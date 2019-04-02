package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import org.code4everything.boot.bean.LogBean;
import org.code4everything.boot.service.BootLogService;
import org.code4everything.springbee.dao.LogDAO;
import org.code4everything.springbee.domain.Log;
import org.code4everything.springbee.domain.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pantao
 * @since 2018/10/14
 **/
@Service
public class LogServiceImpl implements BootLogService<Log> {

    private final LogDAO logDAO;

    private final HttpServletRequest request;

    private final RedisTemplate<String, User> userRedisTemplate;

    @Autowired
    public LogServiceImpl(LogDAO logDAO, HttpServletRequest request, RedisTemplate<String, User> userRedisTemplate) {
        this.logDAO = logDAO;
        this.request = request;
        this.userRedisTemplate = userRedisTemplate;
    }

    @Override
    public Log save(Log log) {
        return logDAO.save(log);
    }

    @Override
    public Log saveException(Log log, Throwable throwable) {
        if (ObjectUtil.isNotNull(log) && ObjectUtil.isNotNull(throwable)) {
            log.setExceptionClass(throwable.getClass().getName());
            log.setExceptionDetail(throwable.getMessage());
            logDAO.save(log);
        }
        return log;
    }

    @Override
    public Log getLog(LogBean logBean) {
        Log log = new Log();
        BeanUtils.copyProperties(logBean, log);
        log.setId(IdUtil.simpleUUID());
        log.setCreateTime(System.currentTimeMillis());
        log.setIp(request.getRemoteAddr());
        User user = userRedisTemplate.opsForValue().get(String.valueOf(request.getAttribute("token")));
        log.setUserId(ObjectUtil.isNull(user) ? "anonymous" : user.getId());
        return log;
    }
}
