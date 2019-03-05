package org.code4everything.springbee.exception;

import cn.hutool.http.HttpStatus;
import org.code4everything.boot.exception.BootException;

/**
 * @author pantao
 * @since 2019/3/1
 **/
public class JobNotFoundException extends BootException {

    public JobNotFoundException() {
        super(HttpStatus.HTTP_NOT_FOUND, "指定的工作日志不存在", true);
    }
}
