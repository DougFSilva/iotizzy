package com.dougfsilva.iotnizer.service.controlDevice;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.ControlDevice;
import com.dougfsilva.iotnizer.model.ControlDeviceType;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.repository.ControlDeviceRepository;
import com.dougfsilva.iotnizer.service.user.FindUser;

@Service
public class CreateControlDevice {

	private final ControlDeviceRepository repository;
	
	private final FindUser findUser;

	public CreateControlDevice(ControlDeviceRepository repository, FindUser findUser) {
		this.repository = repository;
		this.findUser = findUser;
	}
	
	public String create(String user_id, ControlDeviceType deviceType, String name, String location) {
		User user = findUser.findById(user_id);
		ControlDevice device = new ControlDevice(null, user.getId(), deviceType, name, location);
		return repository.create(device);
	}
	
}
