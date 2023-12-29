package com.dougfsilva.iotnizer.dto;

import com.dougfsilva.iotnizer.model.ProfileType;

public record UpdateUserForm(
		
		String email,
        String name,
        ProfileType profileType
        
		) {

	
}
