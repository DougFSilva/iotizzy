package com.dougfsilva.iotnizer.service.user;

import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindUser {
    private final UserRepository repository;

    public FindUser(UserRepository repository) {
        this.repository = repository;
    }


}
