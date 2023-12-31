package com.dougfsilva.iotnizer.service.controlDevice;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.ControlDevice;
import com.dougfsilva.iotnizer.model.ControlDeviceType;
import com.dougfsilva.iotnizer.repository.ControlDeviceRepository;

@Service
public class UpdateControlDevice {
	
	private final ControlDeviceRepository repository;

	private final FindControlDevice findControlDevice;


	public UpdateControlDevice(ControlDeviceRepository repository, FindControlDevice findControlDevice) {
		this.repository = repository;
		this.findControlDevice = findControlDevice;
	}
	
	public ControlDevice update(String id, ControlDeviceType deviceType, String tag, String location) {
		ControlDevice device = findControlDevice.findById(id);
		if(deviceType != null) {
			device.setDeviceType(deviceType);
		}
		if(tag != null && !tag.isBlank()) {
			device.setTag(tag);
		}
		if(location != null && !location.isBlank()) {
			device.setLocation(location);
		}
		ControlDevice updatedDevice = repository.update(device);
		return updatedDevice;
	}
}
