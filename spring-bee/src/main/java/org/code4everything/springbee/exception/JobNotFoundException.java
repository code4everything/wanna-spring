package org.code4everything.springbee.exception;

import org.code4everything.boot.web.mvc.exception.HttpException;
import org.springframework.http.HttpStatus;

/**
 * @author pantao
 * @since 2019/3/1
 **/
public class JobNotFoundException extends HttpException {

    public JobNotFoundException() {
        super(HttpStatus.NOT_FOUND.value(), "指定的工作日志不存在", true);
    }
}
