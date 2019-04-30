package org.code4everything.springbee.exception;

import org.code4everything.boot.exception.BootException;
import org.springframework.http.HttpStatus;

/**
 * @author pantao
 * @since 2019/3/5
 **/
public class JobExistsException extends BootException {

    public JobExistsException() {
        super(HttpStatus.BAD_REQUEST.value(), "工作记录已存在", true);
    }
}
