package com.dougfsilva.iotnizer.service.measuringDevice;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.MeasuringDevice;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.repository.MeasuringDeviceRepository;
import com.dougfsilva.iotnizer.service.user.AuthenticatedUser;

@Service
public class RemoveValuesFromMeasuringDevice {

	private final MeasuringDeviceRepository repository;
	
	private final FindMeasuringDevice findMeasuringDevice;
	
	private final AuthenticatedUser authenticatedUser;
	
	
	public RemoveValuesFromMeasuringDevice(MeasuringDeviceRepository repository,
			FindMeasuringDevice findMeasuringDevice, AuthenticatedUser authenticatedUser) {
		this.repository = repository;
		this.findMeasuringDevice = findMeasuringDevice;
		this.authenticatedUser = authenticatedUser;
	}

	public void removeById(String device_id, String value_id) {
		User user = authenticatedUser.getUser();
		MeasuringDevice device = findMeasuringDevice.findById(device_id);
		repository.removeValue(user, device, value_id);
	}
	
	public void removeByTimestamp(String id, LocalDateTime inicialTimetamp, LocalDateTime finalTimestamp) {
		User user = authenticatedUser.getUser();
		MeasuringDevice device = findMeasuringDevice.findById(id);
		repository.removeValueByTimestamp(user, device, inicialTimetamp, finalTimestamp );
	}
	
	public void removeAll(String id) {
		User user = authenticatedUser.getUser();
		MeasuringDevice device = findMeasuringDevice.findById(id);
		repository.removeAllValuesByDevice(user, device);
	}
}
