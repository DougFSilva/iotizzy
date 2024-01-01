package com.dougfsilva.iotnizer.service.controlDevice;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.ControlDevice;
import com.dougfsilva.iotnizer.model.ControlDeviceType;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.MqttTopicService;
import com.dougfsilva.iotnizer.repository.ControlDeviceRepository;
import com.dougfsilva.iotnizer.service.AuthenticatedUserService;

@Service
public class CreateControlDevice {

	private final ControlDeviceRepository repository;
	
	private final MqttTopicService mqttTopicService;
	
	private final AuthenticatedUserService authenticatedUserService;

	public CreateControlDevice(ControlDeviceRepository repository, MqttTopicService mqttTopicService, AuthenticatedUserService authenticatedUserService) {
		this.repository = repository;
		this.mqttTopicService = mqttTopicService;
		this.authenticatedUserService = authenticatedUserService;
	}
	
	public ControlDevice create(ControlDeviceType deviceType, String tag, String location) {
		User user = authenticatedUserService.getUser();
		String formatedTag = tag.toUpperCase().replaceAll(" ", "_").replaceAll("/", "-").replaceAll("#", "H").replaceAll("\\+", "M");
		String topic = String.format("iotnizer/%s/%s",user.getId(), formatedTag);
		mqttTopicService.addTopic(user, topic);
		ControlDevice device = new ControlDevice(null, user.getId(), deviceType, tag, location, topic);
		String device_id = repository.create(device);
		device.setId(device_id);
		return device;
	}
	
}
