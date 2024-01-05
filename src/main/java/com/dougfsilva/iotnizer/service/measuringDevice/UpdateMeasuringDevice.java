package com.dougfsilva.iotnizer.service.measuringDevice;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.MeasuringDevice;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.MqttTopicService;
import com.dougfsilva.iotnizer.repository.MeasuringDeviceRepository;
import com.dougfsilva.iotnizer.service.AuthenticatedUserService;

@Service
public class UpdateMeasuringDevice {

	private final MeasuringDeviceRepository repository;

	private final FindMeasuringDevice findMeasuringDevice;
	
	private final MqttTopicService mqttTopicService;
	
	private final AuthenticatedUserService authenticatedUserService;

	public UpdateMeasuringDevice(MeasuringDeviceRepository repository, FindMeasuringDevice findMeasuringDevice,
			MqttTopicService mqttTopicService, AuthenticatedUserService authenticatedUserService) {
		this.repository = repository;
		this.findMeasuringDevice = findMeasuringDevice;
		this.mqttTopicService = mqttTopicService;
		this.authenticatedUserService = authenticatedUserService;
	}
	
	public MeasuringDevice update(String id, String tag, String location) {
		MeasuringDevice device = findMeasuringDevice.findById(id);
		User user = authenticatedUserService.getUser();
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
		MeasuringDevice updatedDevice = repository.update(device);
		return updatedDevice;
	}
}
