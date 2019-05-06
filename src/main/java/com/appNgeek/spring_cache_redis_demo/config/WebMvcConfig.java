package com.appNgeek.spring_cache_redis_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

/**
 * 
 * @author Thanneer
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//	/*
//	 * Here we register the Hibernate4Module into an ObjectMapper, then set this custom-configured ObjectMapper to the
//	 * MessageConverter and return it to be added to the HttpMessageConverters of our application
//	 */
//	public MappingJackson2HttpMessageConverter jacksonMessageConverter() {
//		MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
//
//		messageConverter.setObjectMapper(getMapper());
//		return messageConverter;
//	}
	
	@Bean
	@Primary
	public ObjectMapper getHibernateAwareObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
		mapper.enable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		// Registering Hibernate5Module to support lazy objects
		mapper.registerModule(new Hibernate5Module());
		return mapper;
	}
	
	
//	@Bean
//	public Module datatypeHibernateModule() {
//		return new Hibernate5Module();
//	}

}