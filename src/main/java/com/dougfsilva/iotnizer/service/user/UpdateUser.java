package com.dougfsilva.iotnizer.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.exception.InvalidEmailException;
import com.dougfsilva.iotnizer.model.Email;
import com.dougfsilva.iotnizer.model.Profile;
import com.dougfsilva.iotnizer.model.ProfileType;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.EnableClientMqtt;
import com.dougfsilva.iotnizer.repository.UserRepository;

@Service
public class UpdateUser {

	private final UserRepository repository;

	private final FindUser findUser;
	
	private final AuthenticatedUser authenticatedUser;
	
	private final EnableClientMqtt enableClientMqtt;

	public UpdateUser(UserRepository repository, FindUser findUser, AuthenticatedUser authenticatedUser,
			EnableClientMqtt enableClientMqtt) {
		this.repository = repository;
		this.findUser = findUser;
		this.authenticatedUser = authenticatedUser;
		this.enableClientMqtt = enableClientMqtt;
	}

	public User update(String name, String email, ProfileType profileType) {
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
		if (profileType != null) {
			List<Profile> profiles = new ArrayList<>();
			profiles.add(new Profile(profileType));
			user.setProfiles(profiles);
		}
		User updatedUser = repository.update(user);
		return updatedUser;
	}

	public User block(String id) {
		User user = findUser.findById(id);
		enableClientMqtt.disable(user);
		user.setBlocked(true);
		return repository.update(user);
	}

	public User unBlock(String id) {
		User user = findUser.findById(id);
		enableClientMqtt.enable(user);
		user.setBlocked(false);
		return repository.update(user);
	}

}
