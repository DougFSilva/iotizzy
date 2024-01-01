package com.dougfsilva.iotnizer.service.controlDevice;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.exception.OperationNotPermittedException;
import com.dougfsilva.iotnizer.model.ControlDevice;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.MqttTopicService;
import com.dougfsilva.iotnizer.repository.ControlDeviceRepository;

@Service
public class DeleteControlDevice {

	private final ControlDeviceRepository repository;
	
	private final FindControlDevice findControlDevice;
	
	private final MqttTopicService mqttTopicService;

	public DeleteControlDevice(ControlDeviceRepository repository, FindControlDevice findControlDevice, MqttTopicService mqttTopicService) {
		this.repository = repository;
		this.findControlDevice = findControlDevice;
		this.mqttTopicService = mqttTopicService;
	}

	public void delete(User user, String id) {
		ControlDevice device = findControlDevice.findById(id);
		if(!user.getId().equals(device.getUser_id())) {
			throw new OperationNotPermittedException("Deleting devices belonging to another user is not allowed!");
		}
		mqttTopicService.removeTopic(user, device.getMqttTopic());
		repository.delete(device);
	}
}
