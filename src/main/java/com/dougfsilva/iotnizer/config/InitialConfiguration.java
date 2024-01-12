package com.dougfsilva.iotnizer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import com.dougfsilva.iotnizer.model.Email;
import com.dougfsilva.iotnizer.model.ProfileType;
import com.dougfsilva.iotnizer.repository.UserRepository;
import com.dougfsilva.iotnizer.service.user.CreateUser;

@Configuration
public class InitialConfiguration implements ApplicationRunner{
	
	@Value("${user.admin.email}")
	private String adminEmail;
	
	@Value("${user.admin.password}")
	private String adminPassoword;
	
	private final CreateUser createUser;
	
	private final UserRepository userRepository;


	public InitialConfiguration(CreateUser createUser,
			UserRepository userRepository) {
		this.createUser = createUser;
		this.userRepository = userRepository;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		createAdmin();
	}
	
	private void createAdmin() {
		if(userRepository.findByEmail(new Email(adminEmail)).isEmpty()){
			createUser.create(adminEmail, "admin", adminEmail, ProfileType.ADMIN);
		}
	}

}
