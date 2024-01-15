package com.dougfsilva.iotizzy.service.user;

import org.springframework.security.core.context.SecurityContextHolder;

import com.dougfsilva.iotizzy.model.User;

public class AuthenticatedUserService implements AuthenticatedUser {

	@Override
	public User getUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
