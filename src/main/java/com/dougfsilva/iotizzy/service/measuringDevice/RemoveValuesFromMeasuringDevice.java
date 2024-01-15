package com.dougfsilva.iotizzy.service.measuringDevice;

import java.time.LocalDateTime;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotizzy.model.MeasuringDevice;
import com.dougfsilva.iotizzy.model.User;
import com.dougfsilva.iotizzy.repository.MeasuringDeviceRepository;
import com.dougfsilva.iotizzy.service.user.AuthenticatedUser;

@Service
@PreAuthorize("hasAnyRole('GOLD_USER', 'SILVER_USER', 'ADMIN')")
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
