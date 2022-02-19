package com.edersondev.apitest.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edersondev.apitest.domain.User;
import com.edersondev.apitest.domain.dto.UserDTO;
import com.edersondev.apitest.repository.UserRepository;
import com.edersondev.apitest.service.exception.DataIntegrityException;
import com.edersondev.apitest.service.exception.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private ModelMapper mapper;
	
	public User findById(Integer id) {
		Optional<User> user = repository.findById(id);
		return user.orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado"));
	}
	
	public List<User> findAll() {
		return repository.findAll();
	}
	
	public User create(UserDTO obj) {
		this.existsByEmail(obj);
		return repository.save(mapper.map(obj, User.class));
	}
	
	public void update(Integer id, UserDTO obj) {
		findById(id);
		obj.setId(id);
		this.existsByEmail(obj);
		repository.save(mapper.map(obj, User.class));
	}
	
	public void delete(Integer id) {
		findById(id);
		repository.deleteById(id);
	}
	
	public void existsByEmail(UserDTO obj) {
		Optional<User> user = repository.findByEmail(obj.getEmail());
		if(user.isPresent() && !user.get().getId().equals(obj.getId())) {
			throw new DataIntegrityException("E-mail já cadastrado no sistema");
		}
	}
}
