package com.appNgeek.spring_cache_redis_demo.json;

import java.util.List;

import org.springframework.data.domain.Sort;

public class SerializablePageSort extends Sort {

	private static final long serialVersionUID = 3248189030448292002L;

	public SerializablePageSort(Order... orders) {
		super(orders);
	}

	public SerializablePageSort(List<Order> orders) {
		super(orders);

	}

	public SerializablePageSort(String... properties) {
		super(properties);
	}

	public SerializablePageSort(Direction direction, String... properties) {
		super(direction, properties);
	}

	public SerializablePageSort(Direction direction, List<String> properties) {
		super(direction, properties);
	}
	
	
	public SerializablePageSort() {
		super(Direction.DESC);
	}

}