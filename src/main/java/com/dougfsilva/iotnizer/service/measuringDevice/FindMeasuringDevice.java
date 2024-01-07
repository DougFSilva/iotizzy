package com.dougfsilva.iotnizer.service.measuringDevice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.exception.ObjectNotFoundException;
import com.dougfsilva.iotnizer.model.MeasuringDevice;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.repository.MeasuringDeviceRepository;
import com.dougfsilva.iotnizer.service.user.AuthenticatedUser;

@Service
public class FindMeasuringDevice {

	private final MeasuringDeviceRepository repository;
	
	private final AuthenticatedUser authenticatedUser;

	public FindMeasuringDevice(MeasuringDeviceRepository repository, AuthenticatedUser authenticatedUser) {
		this.repository = repository;
		this.authenticatedUser = authenticatedUser;
	}
	
	public MeasuringDevice findById(String id) {
		User user = authenticatedUser.getUser();
		Optional<MeasuringDevice> device = repository.findById(user, id);
		if(device.isEmpty()) {
			throw new ObjectNotFoundException(String.format("Measuring device with id %s not found in database!", id));
		}
		return device.get();
	}
	
	public MeasuringDevice findByIdAndfilterValuesByTimestamp(String id, LocalDateTime initialTimestamp, LocalDateTime finalTimestamp){
		User user = authenticatedUser.getUser();
		Optional<MeasuringDevice> device = repository.findByIdAndfilterValuesByTimestamp(user, id, initialTimestamp, finalTimestamp);
		if(device.isEmpty()) {
			throw new ObjectNotFoundException(String.format("Measuring device with id %s not found in database!", id));
		}
		return device.get();
	}
	
	public MeasuringDevice findByIdAndfilterValuesByTimestampAndValue(
			String id, LocalDateTime initialTimestamp, LocalDateTime finalTimestamp, Double initialValue, Double finalValue){
		User user = authenticatedUser.getUser();
		Optional<MeasuringDevice> device = repository.findByIdAndFilterValuesByTimestampAndValues(user, id, initialTimestamp, finalTimestamp, initialValue, finalValue);
		if(device.isEmpty()) {
			throw new ObjectNotFoundException(String.format("Measuring device with id %s not found in database!", id));
		}
		return device.get();
	}
	
	public List<MeasuringDevice> findAllByUser(){
		User user = authenticatedUser.getUser();
		return repository.findAllByUser(user);
	}
	
	public List<MeasuringDevice> findAll(){
		return repository.findAll();
	}
}
