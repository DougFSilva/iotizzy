package com.dougfsilva.iotnizer.service.controlDevice;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.ControlDevice;
import com.dougfsilva.iotnizer.model.ControlDeviceType;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.TopicManagerMqtt;
import com.dougfsilva.iotnizer.repository.ControlDeviceRepository;

@Service
public class CreateControlDevice {

	private final ControlDeviceRepository repository;
	
	private final TopicManagerMqtt topicManagerMqtt;

	public CreateControlDevice(ControlDeviceRepository repository, TopicManagerMqtt topicManagerMqtt) {
		this.repository = repository;
		this.topicManagerMqtt = topicManagerMqtt;
	}
	
	public ControlDevice create(User user, ControlDeviceType deviceType, String name, String location) {
		ControlDevice device = new ControlDevice(null, user.getId(), deviceType, name, location);
		String device_id = repository.create(device);
		device.setId(device_id);
		topicManagerMqtt.addTopic(device);
		return device;
	}
	
}
