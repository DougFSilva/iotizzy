package com.dougfsilva.iotizzy.service.user;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotizzy.exception.OperationNotPermittedException;
import com.dougfsilva.iotizzy.model.User;

@Service
public class UserPermissionsChecker {
	
	public UserPermissionsChecker checkBlock(User user) {
		if(user.getBlocked()) {
			throw new OperationNotPermittedException(String.format("User %s is blocked!", user.getEmail().getAddress()));
		}
		return this;
	}
	
}
