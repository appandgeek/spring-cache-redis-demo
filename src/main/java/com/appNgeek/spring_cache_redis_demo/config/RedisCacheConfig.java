package com.appNgeek.spring_cache_redis_demo.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.appNgeek.spring_cache_redis_demo.json.CustomGenericJackson2JsonRedisSerializer;

@Configuration
@EnableCaching
public class RedisCacheConfig {

	@Value("${redis.hostname:localhost}")
	private String redisHostName;

	@Value("${redis.port:6379}")
	private int redisPort;

	@Value("${redis.ttl.hours:1}")
	private int redisDataTTL;

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHostName, redisPort));
	}

	@Bean
	public RedisTemplate<Object, Object> redisTemplate() {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		return redisTemplate;
	}

	@Bean
	public RedisCacheManager redisCacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues()
				.entryTtl(Duration.ofHours(redisDataTTL)).serializeValuesWith(
						RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));

		redisCacheConfiguration.usePrefix();

		return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory)
				.cacheDefaults(redisCacheConfiguration).build();

	}
}
