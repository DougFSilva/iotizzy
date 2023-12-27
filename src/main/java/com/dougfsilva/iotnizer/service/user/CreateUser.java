package com.dougfsilva.iotnizer.service.user;

import com.dougfsilva.iotnizer.model.EmailValidator;
import com.dougfsilva.iotnizer.model.Profile;
import com.dougfsilva.iotnizer.model.ProfileType;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CreateUser {

    private final UserRepository repository;

    public CreateUser(UserRepository repository) {
        this.repository = repository;
    }
    public String create(String email, String name, String password, ProfileType profileType){
        User user = new User(null, email, name, password, List.of(new Profile(profileType)), UUID.randomUUID().toString(), false);
        return repository.create(user);
    }

}
