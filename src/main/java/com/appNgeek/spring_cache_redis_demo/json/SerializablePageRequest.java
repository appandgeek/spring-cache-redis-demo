package com.appNgeek.spring_cache_redis_demo.json;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class SerializablePageRequest extends PageRequest {

	private static final long serialVersionUID = 3248189030448292002L;

	public SerializablePageRequest(int page, int size) {
		super(page, size);
	}

	public SerializablePageRequest(int page, int size, Direction direction, String... properties) {
		super(page, size, SerializablePageSort.by(direction, properties));
	}

	public SerializablePageRequest(int page, int size, Sort sort) {
		super(page, size, sort);
	}
	
	public SerializablePageRequest() {
		super(0, 25);
	}
	

}