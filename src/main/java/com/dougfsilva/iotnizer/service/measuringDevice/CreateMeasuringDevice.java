package com.dougfsilva.iotnizer.service.measuringDevice;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.MeasuringDevice;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.MqttTopicService;
import com.dougfsilva.iotnizer.repository.MeasurindDeviceRepository;
import com.dougfsilva.iotnizer.service.AuthenticatedUserService;

@Service
public class CreateMeasuringDevice {

	private final MeasurindDeviceRepository repository;
	
	private final MqttTopicService  mqttTopicService;
	
	private final AuthenticatedUserService authenticatedUserService;

	public CreateMeasuringDevice(MeasurindDeviceRepository repository, MqttTopicService mqttTopicService, AuthenticatedUserService authenticatedUserService) {
		this.repository = repository;
		this.mqttTopicService = mqttTopicService;
		this.authenticatedUserService = authenticatedUserService;
	}
	
	public MeasuringDevice create(String tag, String location) {
		User user = authenticatedUserService.getUser();
		String formatedTag = tag.toUpperCase().replaceAll(" ", "_").replaceAll("/", "-").replaceAll("#", "H").replaceAll("\\+", "M");
		String topic = String.format("iotnizer/persist/%s/%s",user.getId(), formatedTag);
		mqttTopicService.addTopic(user, topic);
		MeasuringDevice device = new MeasuringDevice(null, user.getId(), tag, location, topic);
		String device_id = repository.create(device);
		device.setId(device_id);
		repository.createCollection(MeasuringDeviceCollectionNameGenerator.generate(device));
		return device;
	}
	
	
}
