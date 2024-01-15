package com.dougfsilva.iotnizer.service.user;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.exception.InvalidEmailException;
import com.dougfsilva.iotnizer.model.Email;
import com.dougfsilva.iotnizer.model.Profile;
import com.dougfsilva.iotnizer.model.ProfileType;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.commands.CreateClientMqttCommand;
import com.dougfsilva.iotnizer.mqtt.commands.EnableClientMqttCommand;
import com.dougfsilva.iotnizer.repository.UserRepository;

@Service
public class CreateUser {

    private final UserRepository repository;
    
    private final CreateClientMqttCommand createClientMqtt;
    
    private final PasswordEncoder passwordEncoder;
    
    private final EnableClientMqttCommand enableClientMqtt;
    
    public CreateUser(UserRepository repository, CreateClientMqttCommand createClientMqtt,
			PasswordEncoder passwordEncoder, EnableClientMqttCommand enableClientMqtt) {
		this.repository = repository;
		this.createClientMqtt = createClientMqtt;
		this.passwordEncoder = passwordEncoder;
		this.enableClientMqtt = enableClientMqtt;
	}

	public User create(String email, String name, String password, ProfileType profileType){
        if(repository.findByEmail(new Email(email)).isPresent()){
            throw new InvalidEmailException("Email already registered in the database!");
        }
        String passwordEncoded = passwordEncoder.encode(password);
        User user = new User(null, new Email(email), name, passwordEncoded, List.of(new Profile(profileType)), UUID.randomUUID().toString(), true);
        String createdUser_id = repository.create(user);
        user.setId(createdUser_id);
        createClientMqtt.create(user);
        enableClientMqtt.disable(user);;
        return user;
    }

}
