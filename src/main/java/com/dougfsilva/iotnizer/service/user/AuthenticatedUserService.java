package com.dougfsilva.iotnizer.service.user;

import org.springframework.security.core.context.SecurityContextHolder;

import com.dougfsilva.iotnizer.model.User;

public class AuthenticatedUserService implements AuthenticatedUser {

	@Override
	public User getUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
