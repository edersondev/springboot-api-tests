package com.edersondev.apitest.service;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.edersondev.apitest.domain.User;
import com.edersondev.apitest.domain.dto.UserDTO;
import com.edersondev.apitest.repository.UserRepository;

@SpringBootTest
class UserServiceTest {
	
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
	void testFindById() {
		Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);
		User response = service.findById(ID);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(User.class, response.getClass());
		Assertions.assertEquals(ID, response.getId());
		Assertions.assertEquals(NAME, response.getName());
		Assertions.assertEquals(EMAIL, response.getEmail());
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
