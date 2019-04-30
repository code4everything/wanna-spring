package org.code4everything.springbee.exception;

import org.code4everything.boot.exception.BootException;
import org.springframework.http.HttpStatus;

/**
 * @author pantao
 * @since 2019/3/1
 **/
public class JobNotFoundException extends BootException {

    public JobNotFoundException() {
        super(HttpStatus.NOT_FOUND.value(), "指定的工作日志不存在", true);
    }
}
