package com.dougfsilva.iotnizer.service.user;

import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteUser {
    private UserRepository repository;

    public DeleteUser(UserRepository repository) {
        this.repository = repository;
    }

    public void delete(String id){
        this.repository.delete(id);
    }

}
