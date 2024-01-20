package com.dougfsilva.iotizzy.service.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotizzy.exception.InvalidEmailException;
import com.dougfsilva.iotizzy.exception.OperationNotPermittedException;
import com.dougfsilva.iotizzy.model.Email;
import com.dougfsilva.iotizzy.model.User;
import com.dougfsilva.iotizzy.repository.UserRepository;

@Service
@PreAuthorize("hasAnyRole('GOLD_USER', 'SILVER_USER', 'ADMIN')")
public class UpdateUser {
	
	@Value("${user.master.email}")
	private String masterEmail;

	private final UserRepository repository;

	private final AuthenticatedUser authenticatedUser;
	public UpdateUser(UserRepository repository, AuthenticatedUser authenticatedUser) {
		this.repository = repository;
		this.authenticatedUser = authenticatedUser;
	}

	public User update(String name, String email) {
		User user = authenticatedUser.getUser();
		if(user.getEmail().getAddress().equals(masterEmail)) {
        	throw new OperationNotPermittedException("It is not allowed to delete the master user");
        }
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
