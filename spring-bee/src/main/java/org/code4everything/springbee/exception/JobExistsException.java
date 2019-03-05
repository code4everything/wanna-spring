package org.code4everything.springbee.exception;

import cn.hutool.http.HttpStatus;
import org.code4everything.boot.exception.BootException;

/**
 * @author pantao
 * @since 2019/3/5
 **/
public class JobExistsException extends BootException {

    public JobExistsException() {
        super(HttpStatus.HTTP_BAD_REQUEST, org.springframework.http.HttpStatus.BAD_REQUEST, "工作记录已存在");
    }
}
