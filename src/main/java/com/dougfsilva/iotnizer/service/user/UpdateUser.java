package com.dougfsilva.iotnizer.service.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.exception.InvalidEmailException;
import com.dougfsilva.iotnizer.model.Email;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.repository.UserRepository;

@Service
@PreAuthorize("hasAnyRole('GOLD_USER', 'SILVER_USER')")
public class UpdateUser {

	private final UserRepository repository;

	private final AuthenticatedUser authenticatedUser;
	public UpdateUser(UserRepository repository, AuthenticatedUser authenticatedUser) {
		this.repository = repository;
		this.authenticatedUser = authenticatedUser;
	}

	public User update(String name, String email) {
		User user = authenticatedUser.getUser();
		if (email != null) {
			Email newEmail = new Email(email);
			if (repository.findByEmail(newEmail).isPresent()) {
				throw new InvalidEmailException("Email already registered in the database!");
			}
			user.setEmail(newEmail);
		}
		if (name != null && !name.isBlank()) {
			user.setName(name);
		}
		
		User updatedUser = repository.update(user);
		return updatedUser;
	}

}
