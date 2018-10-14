package org.code4everything.springbee.config;

import com.zhazhapan.util.FileExecutor;
import org.code4everything.springbee.constant.BeeConfigConsts;
import org.code4everything.springbee.domain.Log;
import org.code4everything.springbee.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;

/**
 * @author pantao
 * @since 2018/9/15
 */
@Configuration
public class BeeBeanConfiguration {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Log> logRedisTemplate() {
        RedisTemplate<String, Log> logRedisTemplate = new RedisTemplate<>();
        logRedisTemplate.setKeySerializer(new StringRedisSerializer());
        logRedisTemplate.setConnectionFactory(jedisConnectionFactory());
        return logRedisTemplate;
    }

    @Bean
    public RedisTemplate<String, User> userRedisTemplate() {
        RedisTemplate<String, User> userRedisTemplate = new RedisTemplate<>();
        userRedisTemplate.setConnectionFactory(jedisConnectionFactory());
        userRedisTemplate.setKeySerializer(new StringRedisSerializer());
        return userRedisTemplate;
    }

    @Bean
    public String privateKey() throws IOException {
        return FileExecutor.readFile(BeeConfigConsts.PRIVATE_RSA_KEY_PATH);
    }
}
