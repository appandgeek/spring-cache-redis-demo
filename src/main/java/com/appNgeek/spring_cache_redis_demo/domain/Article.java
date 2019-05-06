package com.appNgeek.spring_cache_redis_demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"user"})
@ToString(callSuper = true, exclude = {"user"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Article.class)
@Entity
@Table(name = "articles")
public class Article extends BaseEntity {

	private static final long serialVersionUID = 5630264440931055927L;

	@Column(name = "title", unique = true, nullable = false, length = 200)
	private String title;

	@Column(name = "body", columnDefinition = "TEXT")
	private String body;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private User user;

}
