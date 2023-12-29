package com.dougfsilva.iotnizer.service.user;

import java.util.List;
import java.util.UUID;

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

    public CreateUser(UserRepository repository, CreateClientMqtt createClientMqtt) {
        this.repository = repository;
        this.createClientMqtt = createClientMqtt;
    }
    public String create(String email, String name, String password, ProfileType profileType){
        if(repository.findByEmail(new Email(email)).isPresent()){
            throw new InvalidEmailException("Email already registered in the database!");
        }
        User user = new User(null, new Email(email), name, password, List.of(new Profile(profileType)), UUID.randomUUID().toString(), false);
        String createdUser_id = repository.create(user);
        createClientMqtt.create(user);
        return createdUser_id;
    }

}
