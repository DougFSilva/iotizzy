package com.dougfsilva.iotnizer.service.controlDevice;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.ControlDevice;
import com.dougfsilva.iotnizer.model.ControlDeviceType;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.MqttTopicService;
import com.dougfsilva.iotnizer.repository.ControlDeviceRepository;
import com.dougfsilva.iotnizer.service.user.AuthenticatedUser;

@Service
@PreAuthorize("hasAnyRole('GOLD', 'SILVER')")
public class UpdateControlDevice {
	
	private final ControlDeviceRepository repository;

	private final FindControlDevice findControlDevice;
	
	private final MqttTopicService mqttTopicService;
	
	private final AuthenticatedUser authenticatedUser;

	public UpdateControlDevice(ControlDeviceRepository repository, FindControlDevice findControlDevice
			, MqttTopicService mqttTopicService, AuthenticatedUser authenticatedUser) {
		this.repository = repository;
		this.findControlDevice = findControlDevice;
		this.mqttTopicService = mqttTopicService;
		this.authenticatedUser = authenticatedUser;
	}
	
	public ControlDevice update(String id, ControlDeviceType deviceType, String tag, String location) {
		ControlDevice device = findControlDevice.findById(id);
		User user = authenticatedUser.getUser();
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
		ControlDevice updatedDevice = repository.update(user, device);
		return updatedDevice;
	}
}
