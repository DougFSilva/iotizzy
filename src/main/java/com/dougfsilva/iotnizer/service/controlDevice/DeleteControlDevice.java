package com.dougfsilva.iotnizer.service.controlDevice;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.ControlDevice;
import com.dougfsilva.iotnizer.repository.ControlDeviceRepository;

@Service
public class DeleteControlDevice {

	private final ControlDeviceRepository repository;
	
	private final FindControlDevice findControlDevice;

	public DeleteControlDevice(ControlDeviceRepository repository, FindControlDevice findControlDevice) {
		this.repository = repository;
		this.findControlDevice = findControlDevice;
	}

	public void delete(String id) {
		ControlDevice device = findControlDevice.findById(id);
		repository.delete(device);
	}
}
