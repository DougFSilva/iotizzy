package com.dougfsilva.iotnizer.service.controlDevice;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.exception.ObjectNotFoundException;
import com.dougfsilva.iotnizer.model.ControlDevice;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.repository.ControlDeviceRepository;
import com.dougfsilva.iotnizer.service.user.AuthenticatedUser;
import com.dougfsilva.iotnizer.service.user.UserCheckPermissions;

@Service
public class FindControlDevice {

	private final ControlDeviceRepository repository;
	
	private final AuthenticatedUser authenticatedUser;
	
	private final UserCheckPermissions checkPermissions;

	
	public FindControlDevice(ControlDeviceRepository repository, AuthenticatedUser authenticatedUser,
			UserCheckPermissions checkPermissions) {
		this.repository = repository;
		this.authenticatedUser = authenticatedUser;
		this.checkPermissions = checkPermissions;
	}

	public ControlDevice findById(String id) {
		User user = authenticatedUser.getUser();
		Optional<ControlDevice> device = repository.findById(user, id);
		if(device.isEmpty()) {
			throw new ObjectNotFoundException(String.format("Control device with id %s not found in database!", id));
		}
		return device.get();
	}
	
	public List<ControlDevice> findAllByUser(User user){
		return repository.findAllByUser(user);
	}
	
	public List<ControlDevice> findAllByUser(){
		User user = authenticatedUser.getUser();
		return repository.findAllByUser(user);
	}
	
	public List<ControlDevice> findAll(){
		return repository.findAll();
	}
}
