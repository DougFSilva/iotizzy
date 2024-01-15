package com.dougfsilva.iotizzy.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import jakarta.validation.constraints.NotBlank;

public record LoginForm(
		
		@NotBlank String username,
		
		@NotBlank String password) {

	public UsernamePasswordAuthenticationToken convert() {
		return new UsernamePasswordAuthenticationToken(username, password);
	}
}
