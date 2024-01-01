package com.dougfsilva.iotnizer.service.controlDevice;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.exception.OperationNotPermittedException;
import com.dougfsilva.iotnizer.model.ControlDevice;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.TopicManagerMqtt;
import com.dougfsilva.iotnizer.repository.ControlDeviceRepository;

@Service
public class DeleteControlDevice {

	private final ControlDeviceRepository repository;
	
	private final FindControlDevice findControlDevice;
	
	private final TopicManagerMqtt topicManagerMqtt;

	public DeleteControlDevice(ControlDeviceRepository repository, FindControlDevice findControlDevice, TopicManagerMqtt topicManagerMqtt) {
		this.repository = repository;
		this.findControlDevice = findControlDevice;
		this.topicManagerMqtt = topicManagerMqtt;
	}

	public void delete(User user, String id) {
		ControlDevice device = findControlDevice.findById(id);
		if(!user.getId().equals(device.getUser_id())) {
			throw new OperationNotPermittedException("Deleting devices belonging to another user is not allowed!");
		}
		topicManagerMqtt.removeTopic(user, device.getMqttTopic());
		repository.delete(device);
	}
}
