package org.code4everything.springbee.config;

import org.code4everything.springbee.domain.Log;
import org.code4everything.springbee.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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
}
