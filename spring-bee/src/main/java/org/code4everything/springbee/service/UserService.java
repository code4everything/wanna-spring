package org.code4everything.springbee.service;

import org.code4everything.springbee.model.RegisterDTO;

/**
 * @author pantao
 * @since 2018/9/15
 */
public interface UserService {

    void register(RegisterDTO registerDTO);
}
