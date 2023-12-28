package com.dougfsilva.iotnizer.service.user;

import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteUser {
    private final UserRepository repository;
    private final FindUser findUser;

    public DeleteUser(UserRepository repository, FindUser findUser) {

        this.repository = repository;
        this.findUser = findUser;
    }

    public void delete(String id){
        User user = findUser.findById(id);
        this.repository.delete(user);
    }

}
