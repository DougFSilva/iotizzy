package com.dougfsilva.iotizzy.service.user;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotizzy.exception.InvalidEmailException;
import com.dougfsilva.iotizzy.model.Email;
import com.dougfsilva.iotizzy.model.Profile;
import com.dougfsilva.iotizzy.model.ProfileType;
import com.dougfsilva.iotizzy.model.User;
import com.dougfsilva.iotizzy.mqtt.commands.CreateClientMqttCommand;
import com.dougfsilva.iotizzy.mqtt.commands.EnableClientMqttCommand;
import com.dougfsilva.iotizzy.repository.UserRepository;

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

	public User create(String email, String name, String password){
        if(repository.findByEmail(new Email(email)).isPresent()){
            throw new InvalidEmailException("Email already registered in the database!");
        }
        String passwordEncoded = passwordEncoder.encode(password);
        User user = new User(null, new Email(email), name, passwordEncoded, List.of(new Profile(ProfileType.SILVER_USER)), UUID.randomUUID().toString(), true);
        String createdUser_id = repository.create(user);
        user.setId(createdUser_id);
        createClientMqtt.create(user);
        enableClientMqtt.disable(user);;
        return user;
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
