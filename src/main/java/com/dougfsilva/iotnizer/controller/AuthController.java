package com.dougfsilva.iotnizer.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dougfsilva.iotnizer.config.security.TokenService;
import com.dougfsilva.iotnizer.dto.LoginForm;
import com.dougfsilva.iotnizer.dto.TokenDto;
import com.dougfsilva.iotnizer.model.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	
	private final TokenService tokenService;
	
	public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
		this.authenticationManager = authenticationManager;
		this.tokenService = tokenService;
	}
	
	@PostMapping
	public ResponseEntity<?> authenticate(@Valid @RequestBody LoginForm form){
		UsernamePasswordAuthenticationToken login = form.convert();
		try {
			Authentication authentication = authenticationManager.authenticate(login);
			User user = (User) authentication.getPrincipal();
			String token = tokenService.generate(authentication);
			TokenDto tokenDto = new TokenDto(token, "Bearer ", user.getEmail().getAddress(), user.getProfiles());
			return ResponseEntity.ok().body(tokenDto);
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	

}