package com.dougfsilva.iotizzy.service.measuringDevice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotizzy.exception.ObjectNotFoundException;
import com.dougfsilva.iotizzy.model.MeasuringDevice;
import com.dougfsilva.iotizzy.model.User;
import com.dougfsilva.iotizzy.repository.MeasuringDeviceRepository;
import com.dougfsilva.iotizzy.service.user.AuthenticatedUser;
import com.dougfsilva.iotizzy.service.user.UserPermissionsChecker;

@Service
@PreAuthorize("hasAnyRole('GOLD_USER', 'SILVER_USER', 'ADMIN')")
public class FindMeasuringDevice {

	private final MeasuringDeviceRepository repository;
	
	private final AuthenticatedUser authenticatedUser;
	
	private final UserPermissionsChecker permissionsChecker;

	
	public FindMeasuringDevice(MeasuringDeviceRepository repository, AuthenticatedUser authenticatedUser,
			UserPermissionsChecker permissionsChecker) {
		this.repository = repository;
		this.authenticatedUser = authenticatedUser;
		this.permissionsChecker = permissionsChecker;
	}

	public MeasuringDevice findById(String id) {
		User user = authenticatedUser.getUser();
		permissionsChecker.checkBlock(user);
		Optional<MeasuringDevice> device = repository.findById(user, id);
		if(device.isEmpty()) {
			throw new ObjectNotFoundException(String.format("Measuring device with id %s not found in database!", id));
		}
		return device.get();
	}
	
	public MeasuringDevice findByIdAndfilterValuesByTimestamp(String id, LocalDateTime initialTimestamp, LocalDateTime finalTimestamp, Integer limit){
		User user = authenticatedUser.getUser();
		permissionsChecker.checkBlock(user);
		Optional<MeasuringDevice> device = repository.findByIdAndfilterValuesByTimestamp(user, id, initialTimestamp, finalTimestamp, limit);
		if(device.isEmpty()) {
			throw new ObjectNotFoundException(String.format("Measuring device with id %s not found in database!", id));
		}
		return device.get();
	}
	
	public MeasuringDevice findByIdAndfilterValuesByTimestampAndValue(
			String id, LocalDateTime initialTimestamp, LocalDateTime finalTimestamp, Double initialValue, Double finalValue, Integer limit){
		User user = authenticatedUser.getUser();
		permissionsChecker.checkBlock(user);
		Optional<MeasuringDevice> device = repository.findByIdAndFilterValuesByTimestampAndValues(user, id, initialTimestamp, finalTimestamp, initialValue, finalValue, limit);
		if(device.isEmpty()) {
			throw new ObjectNotFoundException(String.format("Measuring device with id %s not found in database!", id));
		}
		return device.get();
	}
	
	public List<MeasuringDevice> findAll(){
		User user = authenticatedUser.getUser();
		permissionsChecker.checkBlock(user);
		return repository.findAllByUser(user);
	}
	
}
