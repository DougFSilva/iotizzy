package com.dougfsilva.iotnizer.service.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.commands.EnableClientMqttCommand;
import com.dougfsilva.iotnizer.repository.UserRepository;

@Service
@PreAuthorize("hasRole('ADMIN')")
public class BlockUser {

	private final FindUserAsAdmin findUser;
	
	private final EnableClientMqttCommand enableClientMqtt;
	
	private final UserRepository repository;
	
	public BlockUser(FindUserAsAdmin findUser, EnableClientMqttCommand enableClientMqtt, UserRepository repository) {
		this.findUser = findUser;
		this.enableClientMqtt = enableClientMqtt;
		this.repository = repository;
	}

	public User block(String id, Boolean status) {
		User user = findUser.findById(id);
		if(status) {
			enableClientMqtt.disable(user);
		}else {
			enableClientMqtt.enable(user);
		}
		user.setBlocked(status);
		return repository.update(user);
	}

}
