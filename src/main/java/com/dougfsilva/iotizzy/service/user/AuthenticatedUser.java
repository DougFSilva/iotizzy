package com.dougfsilva.iotizzy.service.user;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotizzy.model.User;

@Service
public interface AuthenticatedUser {

	User getUser();
	
}
