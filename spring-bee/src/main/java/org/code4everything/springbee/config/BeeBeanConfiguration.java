package org.code4everything.springbee.config;

import com.zhazhapan.util.FileExecutor;
import org.code4everything.springbee.constant.BeeConfigConsts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author pantao
 * @since 2018/9/15
 */
@Configuration
public class BeeBeanConfiguration {

    @Bean
    public String privateKey() throws IOException {
        return FileExecutor.readFile(BeeConfigConsts.PRIVATE_RSA_KEY_PATH);
    }
}
