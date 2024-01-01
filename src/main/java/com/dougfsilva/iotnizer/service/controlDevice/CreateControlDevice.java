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
	
	public ControlDevice create(User user, ControlDeviceType deviceType, String tag, String location) {
		String formatedTag = tag.toUpperCase().replaceAll(" ", "_").replaceAll("/", "-").replaceAll("#", "H").replaceAll("\\+", "M");
		String topic = String.format("iotnizer/%s/%s",user.getId(), formatedTag);
		topicManagerMqtt.addTopic(user, topic);
		ControlDevice device = new ControlDevice(null, user.getId(), deviceType, tag, location, topic);
		String device_id = repository.create(device);
		device.setId(device_id);
		return device;
	}
	
}
