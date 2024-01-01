package com.dougfsilva.iotnizer.service.controlDevice;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.ControlDevice;
import com.dougfsilva.iotnizer.model.ControlDeviceType;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.MqttTopicService;
import com.dougfsilva.iotnizer.repository.ControlDeviceRepository;
import com.dougfsilva.iotnizer.service.AuthenticatedUserService;

@Service
public class UpdateControlDevice {
	
	private final ControlDeviceRepository repository;

	private final FindControlDevice findControlDevice;
	
	private final MqttTopicService mqttTopicService;
	
	private final AuthenticatedUserService authenticatedUserService;

	public UpdateControlDevice(ControlDeviceRepository repository, FindControlDevice findControlDevice
			, MqttTopicService mqttTopicService, AuthenticatedUserService authenticatedUserService) {
		this.repository = repository;
		this.findControlDevice = findControlDevice;
		this.mqttTopicService = mqttTopicService;
		this.authenticatedUserService = authenticatedUserService;
	}
	
	public ControlDevice update(String id, ControlDeviceType deviceType, String tag, String location) {
		ControlDevice device = findControlDevice.findById(id);
		User user = authenticatedUserService.getUser();
		if(deviceType != null) {
			device.setDeviceType(deviceType);
		}
		if(tag != null && !tag.isBlank() && !tag.equals(device.getTag())) {
			String formatedTag = tag.toUpperCase().replaceAll(" ", "_").replaceAll("/", "-").replaceAll("#", "H").replaceAll("\\+", "M");
			String topic = String.format("iotnizer/%s/%s",user.getId(), formatedTag);
			mqttTopicService.removeTopic(user, device.getMqttTopic());
			device.setTag(tag);
			device.setMqttTopic(topic);
			mqttTopicService.addTopic(user, topic);
		}
		if(location != null && !location.isBlank()) {
			device.setLocation(location);
		}
		ControlDevice updatedDevice = repository.update(device);
		return updatedDevice;
	}
}
