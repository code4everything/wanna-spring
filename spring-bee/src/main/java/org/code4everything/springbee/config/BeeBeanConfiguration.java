package org.code4everything.springbee.config;

import org.code4everything.boot.redis.RedisTemplateUtils;
import org.code4everything.springbee.domain.Asset;
import org.code4everything.springbee.domain.Log;
import org.code4everything.springbee.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author pantao
 * @since 2018/9/15
 */
@Configuration
public class BeeBeanConfiguration {

    @Bean
    public RedisTemplate<String, Asset> assetRedisTemplate() {
        return RedisTemplateUtils.newTemplate(Asset.class);
    }

    @Bean
    public RedisTemplate<String, Log> logRedisTemplate() {
        return RedisTemplateUtils.newTemplate(Log.class);
    }

    @Bean
    public RedisTemplate<String, User> userRedisTemplate() {
        return RedisTemplateUtils.newTemplate(User.class);
    }
}
