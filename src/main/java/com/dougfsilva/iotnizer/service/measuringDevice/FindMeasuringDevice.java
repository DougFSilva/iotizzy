package com.dougfsilva.iotnizer.service.measuringDevice;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.exception.ObjectNotFoundException;
import com.dougfsilva.iotnizer.model.MeasuringDevice;
import com.dougfsilva.iotnizer.repository.MeasurindDeviceRepository;

@Service
public class FindMeasuringDevice {

	private final MeasurindDeviceRepository repository;

	public FindMeasuringDevice(MeasurindDeviceRepository repository) {
		this.repository = repository;
	}
	
	public MeasuringDevice findById(String id) {
		Optional<MeasuringDevice> device = repository.findById(id);
		return device.orElseThrow(() -> new ObjectNotFoundException(String.format("Device with id %s not found in database!", id)));
	}
}
