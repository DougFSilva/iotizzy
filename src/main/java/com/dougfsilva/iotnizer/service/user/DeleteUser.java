package com.dougfsilva.iotnizer.service.user;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.DeleteClientMqtt;
import com.dougfsilva.iotnizer.repository.UserRepository;

@Service
public class DeleteUser {
	
    private final UserRepository repository;
    private final FindUser findUser;
    private final DeleteClientMqtt deleteClientMqtt;

    public DeleteUser(UserRepository repository, FindUser findUser, DeleteClientMqtt deleteClientMqtt) {

        this.repository = repository;
        this.findUser = findUser;
        this.deleteClientMqtt = deleteClientMqtt;
    }

    public void delete(String id){
        User user = findUser.findById(id);
        this.repository.delete(user);
        deleteClientMqtt.delete(user);
    }

}
