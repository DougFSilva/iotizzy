package com.dougfsilva.iotnizer.service.controlDevice;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.ControlDevice;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.MqttTopicService;
import com.dougfsilva.iotnizer.repository.ControlDeviceRepository;
import com.dougfsilva.iotnizer.service.user.AuthenticatedUser;

@Service
public class DeleteControlDevice {

	private final ControlDeviceRepository repository;

	private final FindControlDevice findControlDevice;

	private final MqttTopicService mqttTopicService;

	private final AuthenticatedUser authenticatedUser;

	public DeleteControlDevice(ControlDeviceRepository repository, FindControlDevice findControlDevice,
			MqttTopicService mqttTopicService, AuthenticatedUser authenticatedUser) {
		this.repository = repository;
		this.findControlDevice = findControlDevice;
		this.mqttTopicService = mqttTopicService;
		this.authenticatedUser  = authenticatedUser;
	}

	public void delete(String id) {
		User user = authenticatedUser.getUser();
		ControlDevice device = findControlDevice.findById(id);
		mqttTopicService.removeTopic(user, device.getMqttTopic());
		repository.delete(user, device);
	}
	
	public void deleteAllByUser(User user) {
		repository.deleteAllByUser(user);
	}
	
	public void deleteAllByUser() {
		User user = authenticatedUser.getUser();
		repository.deleteAllByUser(user);
	}
}
