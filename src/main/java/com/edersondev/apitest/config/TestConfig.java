package com.edersondev.apitest.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.edersondev.apitest.domain.User;
import com.edersondev.apitest.repository.UserRepository;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private UserRepository repository;
	
	@Bean
	public void startDB() {
		User u1 = new User(null,"Beltrano","beltrano@gmail.com","123");
		User u2 = new User(null,"Cicrano","cicrano@gmail.com","123");
		repository.saveAll(List.of(u1,u2));
		
	}
}
