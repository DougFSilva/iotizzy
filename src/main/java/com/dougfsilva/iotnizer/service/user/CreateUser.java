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
import com.dougfsilva.iotnizer.mqtt.CreateClientMqtt;
import com.dougfsilva.iotnizer.repository.UserRepository;

@Service
public class CreateUser {

    private final UserRepository repository;
    
    private final CreateClientMqtt createClientMqtt;
    
    private final PasswordEncoder passwordEncoder;

    public CreateUser(UserRepository repository, CreateClientMqtt createClientMqtt, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.createClientMqtt = createClientMqtt;
        this.passwordEncoder = passwordEncoder;
    }
    public User create(String email, String name, String password, ProfileType profileType){
        if(repository.findByEmail(new Email(email)).isPresent()){
            throw new InvalidEmailException("Email already registered in the database!");
        }
        String passwordEncoded = passwordEncoder.encode(password);
        User user = new User(null, new Email(email), name, passwordEncoded, List.of(new Profile(profileType)), UUID.randomUUID().toString(), false);
        String createdUser_id = repository.create(user);
        user.setId(createdUser_id);
        createClientMqtt.create(user);
        return user;
    }

}
