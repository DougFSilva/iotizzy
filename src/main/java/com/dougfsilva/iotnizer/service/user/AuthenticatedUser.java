package com.dougfsilva.iotnizer.service.user;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.User;

@Service
public interface AuthenticatedUser {

	User getUser();
	
}
