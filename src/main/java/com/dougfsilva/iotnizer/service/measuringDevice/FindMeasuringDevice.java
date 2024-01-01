package com.dougfsilva.iotnizer.service.measuringDevice;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.exception.ObjectNotFoundException;
import com.dougfsilva.iotnizer.model.MeasuringDevice;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.repository.MeasurindDeviceRepository;
import com.dougfsilva.iotnizer.service.AuthenticatedUserService;

@Service
public class FindMeasuringDevice {

	private final MeasurindDeviceRepository repository;
	
	private final AuthenticatedUserService authenticatedUserService;

	public FindMeasuringDevice(MeasurindDeviceRepository repository, AuthenticatedUserService authenticatedUserService) {
		this.repository = repository;
		this.authenticatedUserService = authenticatedUserService;
	}
	
	public MeasuringDevice findById(String id) {
		Optional<MeasuringDevice> device = repository.findById(id);
		if(device.isEmpty()) {
			throw new ObjectNotFoundException(String.format("Measuring device with id %s not found in database!", id));
		}
		authenticatedUserService.deviceVerify(device.get());
		return device.get();
	}
	
	public List<MeasuringDevice> findAllByUser(){
		User user = authenticatedUserService.getUser();
		return repository.findAllByUser(user);
	}
	
	public List<MeasuringDevice> findAll(){
		return repository.findAll();
	}
}
