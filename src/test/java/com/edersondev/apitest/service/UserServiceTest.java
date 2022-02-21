package com.edersondev.apitest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

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

	@Test
	void testExistsByEmail() {
		fail("Not yet implemented");
	}

	private void starUser() {
		user = new User(ID,NAME,EMAIL,PASSWORD);
		userDTO = new UserDTO(ID,NAME,EMAIL,PASSWORD);
		optionalUser = Optional.of(new User(ID,NAME,EMAIL,PASSWORD));
	}
}
