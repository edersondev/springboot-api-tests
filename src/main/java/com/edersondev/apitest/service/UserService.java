package com.edersondev.apitest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edersondev.apitest.domain.User;
import com.edersondev.apitest.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public User findById(Integer id) {
		Optional<User> user = repository.findById(id);
		return user.orElse(null);
	}
}
