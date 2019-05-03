package com.appNgeek.spring_cache_redis_demo.rest.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.appNgeek.spring_cache_redis_demo.domain.User;
import com.appNgeek.spring_cache_redis_demo.exception.BlogAppException;
import com.appNgeek.spring_cache_redis_demo.repo.UserRepository;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@GetMapping("/find")
	public User findByEmail(@RequestParam String email) {
		return userRepository.findByEmail(email);
	}

	@GetMapping("/{id}")
	public User findOne(@PathVariable Long id) throws BlogAppException {
		Optional<User> result = userRepository.findById(id);
		if (result.isPresent())
			return result.get();
		else
			throw new BlogAppException("User with given id not found");
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User create(@RequestBody User user) {
		return userRepository.save(user);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		userRepository.deleteById(id);
	}
	
	

}
