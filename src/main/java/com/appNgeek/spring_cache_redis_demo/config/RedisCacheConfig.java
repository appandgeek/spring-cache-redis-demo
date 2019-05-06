package com.appNgeek.spring_cache_redis_demo.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;

@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport implements CachingConfigurer {

	@Value("${redis.hostname:localhost}")
	private String redisHost;

	@Value("${redis.port:6379}")
	private int redisPort;

	@Value("${redis.timeout.secs:1}")
	private int redisTimeoutInSecs;

	@Value("${redis.socket.timeout.secs:1}")
	private int redisSocketTimeoutInSecs;

	@Value("${redis.ttl.hours:1}")
	private int redisDataTTL;

	// @Autowired
	// private ObjectMapper objectMapper;

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		// LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
		// .commandTimeout(Duration.ofSeconds(redisConnectionTimeoutInSecs)).shutdownTimeout(Duration.ZERO).build();
		//
		// return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost, redisPort), clientConfig);

		final SocketOptions socketOptions = SocketOptions.builder().connectTimeout(Duration.ofSeconds(redisSocketTimeoutInSecs)).build();
		
		final ClientOptions clientOptions = ClientOptions.builder().socketOptions(socketOptions).build();

		LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
				.commandTimeout(Duration.ofSeconds(redisTimeoutInSecs)).clientOptions(clientOptions).build();
		RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration(redisHost, redisPort);

		final LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(serverConfig, clientConfig);
		lettuceConnectionFactory.setValidateConnection(true);
		return lettuceConnectionFactory;

	}

	@Bean
	public RedisTemplate<Object, Object> redisTemplate() {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		return redisTemplate;
	}

	@Bean
	public RedisCacheManager redisCacheManager(LettuceConnectionFactory lettuceConnectionFactory) {

		/**
		 * If we want to use JSON Serialized with own object mapper then use the below config snippet
		 */
		// RedisCacheConfiguration redisCacheConfiguration =
		// RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues()
		// .entryTtl(Duration.ofHours(redisDataTTL)).serializeValuesWith(RedisSerializationContext.SerializationPair
		// .fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper)));

		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues()
				.entryTtl(Duration.ofHours(redisDataTTL))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.java()));

		redisCacheConfiguration.usePrefix();

		RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory)
				.cacheDefaults(redisCacheConfiguration).build();

		redisCacheManager.setTransactionAware(true);
		return redisCacheManager;
	}


	@Override
	public CacheErrorHandler errorHandler() {
		return new RedisCacheErrorHandler();
	}
}
