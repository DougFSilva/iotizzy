package com.dougfsilva.iotizzy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dougfsilva.iotizzy.service.user.AuthenticatedUser;
import com.dougfsilva.iotizzy.service.user.AuthenticatedUserService;

@Configuration
public class BeanConfiguration {

	@Bean
	AuthenticatedUser authenticatedUser() {
		return new AuthenticatedUserService();
	}
}
