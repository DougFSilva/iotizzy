package com.dougfsilva.iotnizer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dougfsilva.iotnizer.service.user.AuthenticatedUser;
import com.dougfsilva.iotnizer.service.user.AuthenticatedUserService;

@Configuration
public class BeanConfiguration {

	@Bean
	AuthenticatedUser authenticatedUser() {
		return new AuthenticatedUserService();
	}
}
