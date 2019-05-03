package com.appNgeek.spring_cache_redis_demo.exception;

public class BlogAppException extends Exception {

	private static final long serialVersionUID = -6561747157347815458L;

	public BlogAppException(String message) {
		super(message);
	}

	public BlogAppException(String message, Throwable ex) {
		super(message, ex);
	}

}
