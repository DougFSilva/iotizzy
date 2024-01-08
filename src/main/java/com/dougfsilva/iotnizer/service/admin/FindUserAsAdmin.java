package com.dougfsilva.iotnizer.service.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.exception.ObjectNotFoundException;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.repository.UserRepository;

@Service
@PreAuthorize("hasRole('ADMIN')")
public class FindUserAsAdmin {

	private final UserRepository repository;

	public FindUserAsAdmin(UserRepository repository) {
		this.repository = repository;
	}
	
	public User findById(String id) {
		Optional<User> user = repository.findById(id);
		return user.orElseThrow(() -> new ObjectNotFoundException(String.format("User with id %s not found in database!", id)));
	}
	
	public List<User> findAll(){
		return repository.findAll();
	}
	
	
}
