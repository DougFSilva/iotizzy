package com.dougfsilva.iotnizer.service.controlDevice;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.ControlDevice;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.MqttTopicService;
import com.dougfsilva.iotnizer.repository.ControlDeviceRepository;
import com.dougfsilva.iotnizer.service.AuthenticatedUserService;

@Service
public class DeleteControlDevice {

	private final ControlDeviceRepository repository;

	private final FindControlDevice findControlDevice;

	private final MqttTopicService mqttTopicService;

	private final AuthenticatedUserService authenticatedUserService;

	public DeleteControlDevice(ControlDeviceRepository repository, FindControlDevice findControlDevice,
			MqttTopicService mqttTopicService, AuthenticatedUserService authenticatedUserService) {
		this.repository = repository;
		this.findControlDevice = findControlDevice;
		this.mqttTopicService = mqttTopicService;
		this.authenticatedUserService  = authenticatedUserService;
	}

	public void delete(String id) {
		ControlDevice device = findControlDevice.findById(id);
		mqttTopicService.removeTopic(authenticatedUserService.getUser(), device.getMqttTopic());
		repository.delete(device);
	}
	
	public void deleteAllByUser(User user) {
		repository.deleteAllByUser(user);
	}
}
