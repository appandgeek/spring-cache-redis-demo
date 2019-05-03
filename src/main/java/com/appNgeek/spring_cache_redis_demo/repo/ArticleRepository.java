package com.appNgeek.spring_cache_redis_demo.repo;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.appNgeek.spring_cache_redis_demo.domain.Article;
import com.appNgeek.spring_cache_redis_demo.domain.User;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

	Article findByTitle(String title);
	
	Collection<Article> findByUser(User user);

}