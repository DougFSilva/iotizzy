package com.dougfsilva.iotnizer.service.controlDevice;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.exception.ObjectNotFoundException;
import com.dougfsilva.iotnizer.model.ControlDevice;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.repository.ControlDeviceRepository;
import com.dougfsilva.iotnizer.service.user.FindUser;

@Service
public class FindControlDevice {

	private final ControlDeviceRepository repository;
	
	private final FindUser findUser;

	public FindControlDevice(ControlDeviceRepository repository, FindUser findUser) {
		this.repository = repository;
		this.findUser = findUser;
	}
	
	public ControlDevice findById(String id) {
		Optional<ControlDevice> device = repository.findById(id);
		return device.orElseThrow(() -> new ObjectNotFoundException(String.format("Control device with id %s not found in database!", id)));
	}
	
	public List<ControlDevice> findAllByUser(String user_id){
		User user = findUser.findById(user_id);
		return repository.findAllByUser(user);
	}
	
	public List<ControlDevice> findAll(){
		return repository.findAll();
	}
}
