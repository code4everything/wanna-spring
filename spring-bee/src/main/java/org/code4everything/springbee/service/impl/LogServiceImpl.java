package org.code4everything.springbee.service.impl;

import com.zhazhapan.util.Checker;
import com.zhazhapan.util.interfaces.SimpleLogService;
import org.code4everything.springbee.dao.LogDAO;
import org.code4everything.springbee.domain.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pantao
 * @since 2018/10/14
 **/
@Service
public class LogServiceImpl implements SimpleLogService<Log> {

    private final LogDAO logDAO;

    @Autowired
    public LogServiceImpl(LogDAO logDAO) {this.logDAO = logDAO;}

    @Override
    public Log save(Log log) {
        return logDAO.save(log);
    }

    @Override
    public Log saveException(Log log, String exceptionClass, String exceptionDetail) {
        if (Checker.isNotNull(log)) {
            log.setExceptionClass(exceptionClass);
            log.setExceptionDetail(exceptionDetail);
            logDAO.save(log);
        }
        return log;
    }
}
