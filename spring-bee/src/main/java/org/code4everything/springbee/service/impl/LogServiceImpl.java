package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import org.code4everything.boot.log.MethodLog;
import org.code4everything.boot.service.BootLogService;
import org.code4everything.boot.web.http.HttpUtils;
import org.code4everything.springbee.dao.LogDAO;
import org.code4everything.springbee.domain.Log;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pantao
 * @since 2018/10/14
 */
@Service
public class LogServiceImpl implements BootLogService<Log> {

    private final LogDAO logDAO;

    private final HttpServletRequest request;

    private final UserService userService;

    @Autowired
    public LogServiceImpl(LogDAO logDAO, HttpServletRequest request, UserService userService) {
        this.logDAO = logDAO;
        this.request = request;
        this.userService = userService;
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
    public Log getLog(MethodLog methodLog) {
        Log log = methodLog.copyInto(new Log());
        log.setId(IdUtil.simpleUUID());
        log.setCreateTime(System.currentTimeMillis());
        log.setIp(request.getRemoteAddr());
        User user = userService.getUserByToken(HttpUtils.getToken(request));
        log.setUserId(ObjectUtil.isNull(user) ? "anonymous" : user.getId());
        return log;
    }
}
