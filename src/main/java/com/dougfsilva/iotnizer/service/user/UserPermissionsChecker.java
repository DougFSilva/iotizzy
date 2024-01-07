package com.dougfsilva.iotnizer.service.user;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.exception.OperationNotPermittedException;
import com.dougfsilva.iotnizer.model.User;

@Service
public class UserPermissionsChecker {
	
	public UserPermissionsChecker checkBlock(User user) {
		if(user.getBlocked()) {
			throw new OperationNotPermittedException(String.format("User %s is blocked!", user.getEmail().getAddress()));
		}
		return this;
	}
	
}
