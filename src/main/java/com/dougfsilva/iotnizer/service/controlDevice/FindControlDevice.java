package com.dougfsilva.iotnizer.service.controlDevice;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.exception.ObjectNotFoundException;
import com.dougfsilva.iotnizer.model.ControlDevice;
import com.dougfsilva.iotnizer.repository.ControlDeviceRepository;

@Service
public class FindControlDevice {

	private final ControlDeviceRepository repository;

	public FindControlDevice(ControlDeviceRepository repository) {
		this.repository = repository;
	}
	
	public ControlDevice findById(String id) {
		Optional<ControlDevice> device = repository.findById(id);
		return device.orElseThrow(() -> new ObjectNotFoundException(String.format("Control device with id %s not found in database!", id)));
	}
}
