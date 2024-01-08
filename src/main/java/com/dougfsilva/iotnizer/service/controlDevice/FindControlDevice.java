package com.dougfsilva.iotnizer.service.controlDevice;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.exception.ObjectNotFoundException;
import com.dougfsilva.iotnizer.model.ControlDevice;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.repository.ControlDeviceRepository;
import com.dougfsilva.iotnizer.service.user.AuthenticatedUser;
import com.dougfsilva.iotnizer.service.user.UserPermissionsChecker;

@Service
@PreAuthorize("hasAnyRole('GOLD_USER', 'SILVER_USER')")
public class FindControlDevice {

	private final ControlDeviceRepository repository;
	
	private final AuthenticatedUser authenticatedUser;
	
	private final UserPermissionsChecker permissionsChecker;

	public FindControlDevice(ControlDeviceRepository repository, AuthenticatedUser authenticatedUser,
			UserPermissionsChecker permissionsChecker) {
		this.repository = repository;
		this.authenticatedUser = authenticatedUser;
		this.permissionsChecker = permissionsChecker;
	}

	public ControlDevice findById(String id) {
		User user = authenticatedUser.getUser();
		permissionsChecker.checkBlock(user);
		Optional<ControlDevice> device = repository.findById(user, id);
		if(device.isEmpty()) {
			throw new ObjectNotFoundException(String.format("Control device with id %s not found in database!", id));
		}
		return device.get();
	}
	
	public List<ControlDevice> findAll(){
		User user = authenticatedUser.getUser();
		permissionsChecker.checkBlock(user);
		return repository.findAllByUser(user);
	}
	
}
