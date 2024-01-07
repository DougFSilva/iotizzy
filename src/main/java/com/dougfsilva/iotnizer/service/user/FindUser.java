package com.dougfsilva.iotnizer.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.exception.ObjectNotFoundException;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.repository.UserRepository;

@Service
public class FindUser {
	
    private final UserRepository repository;
    private final AuthenticatedUser authenticatedUser;


    public FindUser(UserRepository repository, AuthenticatedUser authenticatedUser) {
		this.repository = repository;
		this.authenticatedUser = authenticatedUser;
	}

	public User findById(String id){
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException(String.format("User with id %s not found in database!", id)));
    }
    
    public User findUser() {
    	return authenticatedUser.getUser();
    }
    
    public List<User> findAll(){
    	List<User> users = repository.findAll();
    	return users;
    }

}
