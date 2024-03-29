package com.dougfsilva.iotizzy.service.measuringDevice;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotizzy.model.MeasuringDevice;
import com.dougfsilva.iotizzy.model.User;
import com.dougfsilva.iotizzy.mqtt.MqttTopicService;
import com.dougfsilva.iotizzy.repository.MeasuringDeviceRepository;
import com.dougfsilva.iotizzy.service.user.AuthenticatedUser;

@Service
@PreAuthorize("hasAnyRole('GOLD_USER', 'SILVER_USER', 'ADMIN')")
public class UpdateMeasuringDevice {

	private final MeasuringDeviceRepository repository;

	private final FindMeasuringDevice findMeasuringDevice;
	
	private final MqttTopicService mqttTopicService;
	
	private final AuthenticatedUser authenticatedUser;

	public UpdateMeasuringDevice(MeasuringDeviceRepository repository, FindMeasuringDevice findMeasuringDevice,
			MqttTopicService mqttTopicService, AuthenticatedUser authenticatedUser) {
		this.repository = repository;
		this.findMeasuringDevice = findMeasuringDevice;
		this.mqttTopicService = mqttTopicService;
		this.authenticatedUser = authenticatedUser;
	}
	
	public MeasuringDevice update(String id, String tag, String location) {
		MeasuringDevice device = findMeasuringDevice.findById(id);
		User user = authenticatedUser.getUser();
		if(tag != null && !tag.isBlank() && !tag.equals(device.getTag())) {
			String formatedTag = tag.toUpperCase().replaceAll(" ", "_").replaceAll("/", "-").replaceAll("#", "H").replaceAll("\\+", "M");
			String topic = String.format("iotnizer/persist/%s/%s/%s",user.getId(), device.getId(), formatedTag);
			mqttTopicService.removeTopic(user, device.getMqttTopic());	
			device.setTag(tag);
			device.setMqttTopic(topic);
			mqttTopicService.addTopic(user, topic);
		}
		if(location != null && !location.isBlank()) {
			device.setLocation(location);
		}
		MeasuringDevice updatedDevice = repository.update(user, device);
		return updatedDevice;
	}
}
