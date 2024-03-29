package com.dougfsilva.iotizzy.service.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotizzy.model.User;

@Service
@PreAuthorize("hasAnyRole('GOLD_USER', 'SILVER_USER', 'ADMIN')")
public class FindUser {
	
    private final AuthenticatedUser authenticatedUser;

    public FindUser(AuthenticatedUser authenticatedUser) {
		this.authenticatedUser = authenticatedUser;
	}

	public User findUser() {
    	return authenticatedUser.getUser();
    }
    
}
