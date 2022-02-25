package com.edersondev.apitest.controller;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.edersondev.apitest.domain.User;
import com.edersondev.apitest.domain.dto.UserDTO;
import com.edersondev.apitest.service.UserService;

@SpringBootTest
class UserControllerTest {
	
	private static final Integer ID			= 1;
	private static final String NAME		= "John Snow";
	private static final String EMAIL		= "john@gmail.com";
	private static final String PASSWORD	= "123";
	
	private User user;
	private UserDTO userDTO;

	@InjectMocks
	private UserController controller;
	
	@Mock
	private UserService service;
	
	@Mock
	private ModelMapper mapper;
	
	@BeforeEach
	void setup() throws Exception {
		MockitoAnnotations.openMocks(this);
		starUser();
	}

	@Test
	void testFindById() {
		fail("Not yet implemented");
	}

	@Test
	void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	void testCreate() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}
	
	private void starUser() {
		user = new User(ID,NAME,EMAIL,PASSWORD);
		userDTO = new UserDTO(ID,NAME,EMAIL,PASSWORD);
	}

}
