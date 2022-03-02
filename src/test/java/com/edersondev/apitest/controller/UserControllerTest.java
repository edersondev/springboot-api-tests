package com.edersondev.apitest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.edersondev.apitest.domain.User;
import com.edersondev.apitest.domain.dto.UserDTO;
import com.edersondev.apitest.service.UserService;

@SpringBootTest
class UserControllerTest {
	
	private static final int INDEX = 0;
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
	void whenFindByIdThenReturnSuccess() {
		when(service.findById(anyInt())).thenReturn(user);
		when(mapper.map(any(), any())).thenReturn(userDTO);
		
		ResponseEntity<UserDTO> response = controller.findById(ID);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(UserDTO.class, response.getBody().getClass());
		
		assertEquals(ID, response.getBody().getId());
		assertEquals(NAME, response.getBody().getName());
		assertEquals(EMAIL, response.getBody().getEmail());
		assertEquals(PASSWORD, response.getBody().getPassword());
	}

	@Test
	void whenFindAllThenReturnListOfUserDTO() {
		when(service.findAll()).thenReturn(List.of(user));
		when(mapper.map(any(),any())).thenReturn(userDTO);
		
		ResponseEntity<List<UserDTO>> response = controller.findAll();
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ArrayList.class, response.getBody().getClass());
		assertEquals(UserDTO.class, response.getBody().get(INDEX).getClass());
		
		assertEquals(ID, response.getBody().get(INDEX).getId());
		assertEquals(NAME, response.getBody().get(INDEX).getName());
		assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
		assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());
	}

	@Test
	void whenCreateThenReturnCreated() {
		when(service.create(any())).thenReturn(user);
		when(mapper.map(any(), any())).thenReturn(userDTO);
		
		ResponseEntity<UserDTO> response = controller.create(userDTO);
		
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getHeaders().get("Location"));
	}

	@Test
	void whenUpdateThenReturnSuccess() {
		when(service.update(anyInt(), any())).thenReturn(user);
		
		ResponseEntity<Void> response = controller.update(ID, userDTO);
		
		assertNotNull(response);
		assertNull(response.getBody());
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
	}

	@Test
	void whenDeleteThenReturnSuccess() {
		doNothing().when(service).delete(anyInt());
		
		ResponseEntity<Void> response = controller.delete(ID);
		
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(service,times(1)).delete(anyInt());
	}
	
	private void starUser() {
		user = new User(ID,NAME,EMAIL,PASSWORD);
		userDTO = new UserDTO(ID,NAME,EMAIL,PASSWORD);
	}

}
