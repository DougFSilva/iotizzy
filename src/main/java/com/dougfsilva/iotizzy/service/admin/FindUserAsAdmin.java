package com.dougfsilva.iotizzy.service.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotizzy.dto.DetailedUserDto;
import com.dougfsilva.iotizzy.exception.ObjectNotFoundException;
import com.dougfsilva.iotizzy.model.User;
import com.dougfsilva.iotizzy.repository.ControlDeviceRepository;
import com.dougfsilva.iotizzy.repository.MeasuringDeviceRepository;
import com.dougfsilva.iotizzy.repository.UserRepository;

@Service
@PreAuthorize("hasRole('ADMIN')")
public class FindUserAsAdmin {

	private final UserRepository repository;
	
	private final ControlDeviceRepository controlDeviceRepository;
	
	private final MeasuringDeviceRepository measuringDeviceRepository;
	
	public FindUserAsAdmin(UserRepository repository, ControlDeviceRepository controlDeviceRepository,
			MeasuringDeviceRepository measuringDeviceRepository) {
		this.repository = repository;
		this.controlDeviceRepository = controlDeviceRepository;
		this.measuringDeviceRepository = measuringDeviceRepository;
	}

	public User findById(String id) {
		Optional<User> user = repository.findById(id);
		return user.orElseThrow(() -> new ObjectNotFoundException(String.format("User with id %s not found in database!", id)));
	}
	
	public DetailedUserDto findByIdDetailed(String id) {
		User user = findById(id);
		Long controlDevicesCount = controlDeviceRepository.countDevicesByUser(user);
		Long measringDeviceCount = measuringDeviceRepository.countDevicesByUser(user);
		return new DetailedUserDto(user, controlDevicesCount, measringDeviceCount);
	}
	
	public List<User> findAllByBlocked(Boolean blocked){
		return repository.findAllByBlocked(blocked);
	}
	
	public List<User> findAll(){
		return repository.findAll();
	}
	
	
}
