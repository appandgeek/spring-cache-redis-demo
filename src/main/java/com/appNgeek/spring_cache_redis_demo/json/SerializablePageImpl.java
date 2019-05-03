package com.appNgeek.spring_cache_redis_demo.json;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class SerializablePageImpl<T> extends PageImpl<T> {

	private static final long serialVersionUID = 3248189030448292002L;

	public SerializablePageImpl(List<T> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public SerializablePageImpl(List<T> content) {
		super(content);
	}

	public SerializablePageImpl() {
		super(new ArrayList<T>());
	}

}