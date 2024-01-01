package com.dougfsilva.iotnizer.service.measuringDevice;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.MeasuringDevice;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.MqttTopicService;
import com.dougfsilva.iotnizer.repository.MeasurindDeviceRepository;

@Service
public class CreateMeasuringDevice {

	private final MeasurindDeviceRepository repository;
	
	private final MqttTopicService  mqttTopicService;

	public CreateMeasuringDevice(MeasurindDeviceRepository repository, MqttTopicService mqttTopicService) {
		this.repository = repository;
		this.mqttTopicService = mqttTopicService;
	}
	
	public MeasuringDevice create(User user, String tag, String location) {
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
