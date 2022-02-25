package com.edersondev.apitest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.edersondev.apitest.domain.User;
import com.edersondev.apitest.domain.dto.UserDTO;
import com.edersondev.apitest.repository.UserRepository;
import com.edersondev.apitest.service.exception.DataIntegrityException;
import com.edersondev.apitest.service.exception.ResourceNotFoundException;

@SpringBootTest
class UserServiceTest {
	
	private static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
	private static final Integer ID			= 1;
	private static final String NAME		= "John Snow";
	private static final String EMAIL		= "john@gmail.com";
	private static final String PASSWORD	= "123";

	@InjectMocks
	private UserService service;
	
	@Mock
	private UserRepository repository;
	
	@Mock
	private ModelMapper mapper;
	
	private User user;
	private UserDTO userDTO;
	private Optional<User> optionalUser;
	
	@BeforeEach
	void setup() throws Exception {
		MockitoAnnotations.openMocks(this);
		starUser();
	}

	@Test
	void whenFindByIdThenReturnAnUserInstance() {
		when(repository.findById(anyInt())).thenReturn(optionalUser);
		User response = service.findById(ID);
		
		assertNotNull(response);
		assertEquals(User.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getName());
		assertEquals(EMAIL, response.getEmail());
	}
	
	@Test
	void whenFindByIdThenReturnAnObjectNotFoundException() {
		when(repository.findById(anyInt())).thenThrow(new ResourceNotFoundException(OBJETO_NAO_ENCONTRADO));
		try {
			service.findById(ID);
		} catch (Exception ex) {
			assertEquals(ResourceNotFoundException.class,ex.getClass());
			assertEquals(OBJETO_NAO_ENCONTRADO,ex.getMessage());
		}
	}

	@Test
	void whenFindAllThenReturnAnListOfUsers() {
		when(repository.findAll()).thenReturn(List.of(user));
		
		List<User> response = service.findAll();
		
		assertNotNull(response);
		assertEquals(1,response.size());
		assertEquals(User.class, response.get(0).getClass());
		assertEquals(ID, response.get(0).getId());
		assertEquals(NAME, response.get(0).getName());
		assertEquals(EMAIL, response.get(0).getEmail());
		assertEquals(PASSWORD, response.get(0).getPassword());
	}

	@Test
	void whenCreateThenReturnSuccess() {
		when(repository.save(any())).thenReturn(user);
		User response = service.create(userDTO);
		
		assertNotNull(response);
		assertEquals(User.class,response.getClass());
		assertEquals(ID,response.getId());
		assertEquals(NAME,response.getName());
		assertEquals(EMAIL,response.getEmail());
		assertEquals(PASSWORD,response.getPassword());
	}
	
	@Test
	void whenCreateThenReturnDataIntegrityException() {
		when(repository.findByEmail(anyString())).thenReturn(optionalUser);
		
		try {
			optionalUser.get().setId(2);
			service.create(userDTO);
		} catch (Exception ex) {
			assertEquals(DataIntegrityException.class,ex.getClass());
			assertEquals("E-mail já cadastrado no sistema",ex.getMessage());
		}
	}

	@Test
	void whenUpdateThenReturnSuccess() {
		when(repository.findById(anyInt())).thenReturn(optionalUser);
		when(repository.save(any())).thenReturn(user);
		
		User response = service.update(ID,userDTO);
		
		assertNotNull(response);
		assertEquals(User.class,response.getClass());
		assertEquals(ID,response.getId());
		assertEquals(NAME,response.getName());
		assertEquals(EMAIL,response.getEmail());
		assertEquals(PASSWORD,response.getPassword());
	}
	
	@Test
	void whenUpdateThenReturnDataIntegrityException() {
		when(repository.findById(anyInt())).thenReturn(optionalUser);
		when(repository.findByEmail(anyString())).thenReturn(optionalUser);
		
		try {
			optionalUser.get().setId(2);
			service.update(ID,userDTO);
		} catch (Exception ex) {
			assertEquals(DataIntegrityException.class,ex.getClass());
			assertEquals("E-mail já cadastrado no sistema",ex.getMessage());
		}
	}
	
	@Test
	void whenUpdateThenReturnResourceNotFoundException() {
		when(repository.findById(anyInt())).thenThrow(new ResourceNotFoundException(OBJETO_NAO_ENCONTRADO));
		try {
			service.update(ID,userDTO);
		} catch (Exception ex) {
			assertEquals(ResourceNotFoundException.class,ex.getClass());
			assertEquals(OBJETO_NAO_ENCONTRADO,ex.getMessage());
		}
	}

	@Test
	void whenDeleteThenReturnSuccess() {
		when(repository.findById(anyInt())).thenReturn(optionalUser);
		doNothing().when(repository).deleteById(anyInt());
		service.delete(ID);
		verify(repository,times(1)).deleteById(anyInt());
	}
	
	@Test
	void whenDeleteThenReturnResourceNotFoundException() {
		when(repository.findById(anyInt())).thenThrow(new ResourceNotFoundException(OBJETO_NAO_ENCONTRADO));
		try {
			service.delete(ID);
		} catch (Exception ex) {
			assertEquals(ResourceNotFoundException.class,ex.getClass());
			assertEquals(OBJETO_NAO_ENCONTRADO,ex.getMessage());
		}
	}

	private void starUser() {
		user = new User(ID,NAME,EMAIL,PASSWORD);
		userDTO = new UserDTO(ID,NAME,EMAIL,PASSWORD);
		optionalUser = Optional.of(new User(ID,NAME,EMAIL,PASSWORD));
	}
}
