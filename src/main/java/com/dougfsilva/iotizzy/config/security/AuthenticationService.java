package com.dougfsilva.iotizzy.config.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotizzy.model.Email;
import com.dougfsilva.iotizzy.model.User;
import com.dougfsilva.iotizzy.repository.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService {

	private final UserRepository repository;

	public AuthenticationService(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repository.findByEmail(new Email(username));
		return user.orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found in database!"));
	}
	
}