package com.dougfsilva.iotnizer.service.measuringDevice;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.repository.MeasuringDeviceRepository;
import com.dougfsilva.iotnizer.service.AuthenticatedUserService;

@Service
public class RemoveValuesFromMeasuringDevice {

	private final MeasuringDeviceRepository repository;
	
	private final AuthenticatedUserService authenticatedUserService;

	public RemoveValuesFromMeasuringDevice(MeasuringDeviceRepository repository, AuthenticatedUserService authenticatedUserService) {
		this.repository = repository;
		this.authenticatedUserService = authenticatedUserService;
	}
	
	public void removeById(String device_id, String value_id) {
		User user = authenticatedUserService.getUser();
		repository.removeValue(user, device_id, value_id);
	}
	
	public void removeByTimestamp(String device_id, LocalDateTime inicialTimetamp, LocalDateTime finalTimestamp) {
		User user = authenticatedUserService.getUser();
		repository.removeValueByTimestamp(user, device_id, inicialTimetamp, finalTimestamp );
	}
}
