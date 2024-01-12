package com.dougfsilva.iotnizer.service.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.commands.DeleteClientMqttCommand;
import com.dougfsilva.iotnizer.repository.ControlDeviceRepository;
import com.dougfsilva.iotnizer.repository.MeasuringDeviceRepository;
import com.dougfsilva.iotnizer.repository.UserRepository;

@Service
@PreAuthorize("hasRole('ADMIN')")
public class DeleteUserAsAdmin {

	private final UserRepository repository;
	
	private final ControlDeviceRepository controlDeviceRepository;
	
	private final MeasuringDeviceRepository measuringDeviceRepository;

	private final FindUserAsAdmin findUser;

	private final DeleteClientMqttCommand deleteClientMqtt;

	public DeleteUserAsAdmin(UserRepository repository, ControlDeviceRepository controlDeviceRepository,
			MeasuringDeviceRepository measuringDeviceRepository, FindUserAsAdmin findUser,
			DeleteClientMqttCommand deleteClientMqtt) {
		this.repository = repository;
		this.controlDeviceRepository = controlDeviceRepository;
		this.measuringDeviceRepository = measuringDeviceRepository;
		this.findUser = findUser;
		this.deleteClientMqtt = deleteClientMqtt;
	}

	public void delete(String id) {
		User user = findUser.findById(id);
		deleteClientMqtt.delete(user);
		controlDeviceRepository.deleteAllByUser(user);
		measuringDeviceRepository.deleteAllByUser(user);
		repository.delete(user);
	}

}
