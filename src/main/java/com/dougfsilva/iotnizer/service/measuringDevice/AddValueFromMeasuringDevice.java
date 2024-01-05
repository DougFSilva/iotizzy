package com.dougfsilva.iotnizer.service.measuringDevice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.MeasuredValue;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.repository.MeasuringDeviceRepository;
import com.dougfsilva.iotnizer.service.AuthenticatedUserService;

@Service
public class AddValueFromMeasuringDevice {
	
	private final MeasuringDeviceRepository repository;
	
	private final AuthenticatedUserService authenticatedUserService;

	public AddValueFromMeasuringDevice(MeasuringDeviceRepository repository, AuthenticatedUserService authenticatedUserService) {
		this.repository = repository;
		this.authenticatedUserService = authenticatedUserService;
	}
	
	
	public void add(String device_id, List<Double> values, LocalDateTime timestamp) {
		User user = authenticatedUserService.getUser();
		List<MeasuredValue> measuredValues = new ArrayList<>();
		if(timestamp == null) {
			 values.forEach(value -> measuredValues.add(new MeasuredValue(null, LocalDateTime.now(), value)));
		}else {
			values.forEach(value -> measuredValues.add(new MeasuredValue(null, timestamp, value)));
		}
		repository.addValues(user, device_id, measuredValues);
	}
	
}
