package com.dougfsilva.iotnizer.service.controlDevice;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.exception.ObjectNotFoundException;
import com.dougfsilva.iotnizer.model.ControlDevice;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.repository.ControlDeviceRepository;
import com.dougfsilva.iotnizer.service.AuthenticatedUserService;

@Service
public class FindControlDevice {

	private final ControlDeviceRepository repository;
	
	private final AuthenticatedUserService authenticatedUserService;

	public FindControlDevice(ControlDeviceRepository repository, AuthenticatedUserService authenticatedUserService) {
		this.repository = repository;
		this.authenticatedUserService = authenticatedUserService;
	}
	
	public ControlDevice findById(String id) {
		Optional<ControlDevice> device = repository.findById(id);
		if(device.isEmpty()) {
			throw new ObjectNotFoundException(String.format("Control device with id %s not found in database!", id));
		}
		authenticatedUserService.deviceVerify(device.get());
		return device.get();
	}
	
	public List<ControlDevice> findAllByUser(){
		User user = authenticatedUserService.getUser();
		return repository.findAllByUser(user);
	}
	
	public List<ControlDevice> findAll(){
		return repository.findAll();
	}
}
