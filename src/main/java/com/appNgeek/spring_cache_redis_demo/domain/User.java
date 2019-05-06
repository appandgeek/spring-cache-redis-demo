package com.appNgeek.spring_cache_redis_demo.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;
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
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = User.class)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

	private static final long serialVersionUID = 5135149878647405678L;

	@Column(name = "name", length = 40)
	private String name;

	@Column(name = "email", nullable = false, unique = true, length = 60)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@ApiModelProperty(required = false, accessMode = AccessMode.READ_ONLY, readOnly = true)
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Article> articles;

}
