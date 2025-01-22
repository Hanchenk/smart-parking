package com.hanchenk.love.framework.idempotent.config;

import com.hanchenk.love.framework.idempotent.core.aop.IdempotentAspect;
import com.hanchenk.love.framework.idempotent.core.keyresolver.impl.DefaultIdempotentKeyResolver;
import com.hanchenk.love.framework.idempotent.core.keyresolver.impl.ExpressionIdempotentKeyResolver;
import com.hanchenk.love.framework.idempotent.core.keyresolver.IdempotentKeyResolver;
import com.hanchenk.love.framework.idempotent.core.keyresolver.impl.UserIdempotentKeyResolver;
import com.hanchenk.love.framework.idempotent.core.redis.IdempotentRedisDAO;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import com.hanchenk.love.framework.redis.config.HanchenkRedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@AutoConfiguration(after = HanchenkRedisAutoConfiguration.class)
public class HanchenkIdempotentConfiguration {

    @Bean
    public IdempotentAspect idempotentAspect(List<IdempotentKeyResolver> keyResolvers, IdempotentRedisDAO idempotentRedisDAO) {
        return new IdempotentAspect(keyResolvers, idempotentRedisDAO);
    }

    @Bean
    public IdempotentRedisDAO idempotentRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new IdempotentRedisDAO(stringRedisTemplate);
    }

    // ========== 各种 IdempotentKeyResolver Bean ==========

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver() {
        return new DefaultIdempotentKeyResolver();
    }

    @Bean
    public UserIdempotentKeyResolver userIdempotentKeyResolver() {
        return new UserIdempotentKeyResolver();
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver() {
        return new ExpressionIdempotentKeyResolver();
    }

}
