package com.edersondev.apitest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edersondev.apitest.domain.User;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@GetMapping(value = "/{id}")
	public ResponseEntity<User> show(@PathVariable Integer id){
		return ResponseEntity.ok().body(new User(1,"Ederson","ederson@teste.com.br","123"));
	}
}
