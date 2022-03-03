package com.edersondev.apitest.controller.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import com.edersondev.apitest.service.exception.DataIntegrityException;
import com.edersondev.apitest.service.exception.ResourceNotFoundException;

@SpringBootTest
class ControllerExceptionHandlerTest {

	private static final String E_MAIL_JA_CADASTRADO = "E-mail já cadastrado";

	private static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
	
	@InjectMocks
	private ControllerExceptionHandler exceptionHandler;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void whenResourceNotFoundThenReturnResponseEntity() {
		ResponseEntity<StandardError> response = exceptionHandler
				.resourceNotFound(
						new ResourceNotFoundException(OBJETO_NAO_ENCONTRADO),
						new MockHttpServletRequest()
				);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(StandardError.class, response.getBody().getClass());
		assertEquals(OBJETO_NAO_ENCONTRADO, response.getBody().getError());
		assertEquals(404, response.getBody().getStatus());
		assertNotEquals("/user/2", response.getBody().getPath());
		assertNotEquals(LocalDateTime.now(), response.getBody().getTimestamp());
	}

	@Test
	void testDataIntegrityException() {
		ResponseEntity<StandardError> response = exceptionHandler
				.dataIntegrityException(
						new DataIntegrityException(E_MAIL_JA_CADASTRADO),
						new MockHttpServletRequest()
				);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(StandardError.class, response.getBody().getClass());
		assertEquals(E_MAIL_JA_CADASTRADO, response.getBody().getError());
		assertEquals(400, response.getBody().getStatus());
	}

}
