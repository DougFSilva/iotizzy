package com.dougfsilva.iotizzy.service.measuringDevice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotizzy.model.MeasuredValue;
import com.dougfsilva.iotizzy.model.User;
import com.dougfsilva.iotizzy.repository.MeasuringDeviceRepository;
import com.dougfsilva.iotizzy.service.user.AuthenticatedUser;

@Service
@PreAuthorize("hasAnyRole('GOLD_USER', 'SILVER_USER', 'ADMIN')")
public class AddValueFromMeasuringDevice {
	
	private final MeasuringDeviceRepository repository;
	
	private final AuthenticatedUser authenticatedUser;

	public AddValueFromMeasuringDevice(MeasuringDeviceRepository repository, AuthenticatedUser authenticatedUser) {
		this.repository = repository;
		this.authenticatedUser = authenticatedUser;
	}
	
	public void add(String device_id, List<Double> values, LocalDateTime timestamp) {
		User user = authenticatedUser.getUser();
		List<MeasuredValue> measuredValues = new ArrayList<>();
		if(timestamp == null) {
			 values.forEach(value -> measuredValues.add(new MeasuredValue(null, LocalDateTime.now(), value)));
		}else {
			values.forEach(value -> measuredValues.add(new MeasuredValue(null, timestamp, value)));
		}
		repository.addValues(user, device_id, measuredValues);
	}
	
}
