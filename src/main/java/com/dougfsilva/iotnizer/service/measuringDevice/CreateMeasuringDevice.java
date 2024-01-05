package com.dougfsilva.iotnizer.service.measuringDevice;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.MeasuredValue;
import com.dougfsilva.iotnizer.model.MeasuringDevice;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.MqttTopicService;
import com.dougfsilva.iotnizer.repository.MeasuringDeviceRepository;
import com.dougfsilva.iotnizer.service.AuthenticatedUserService;

@Service
public class CreateMeasuringDevice {

	private final MeasuringDeviceRepository repository;
	
	private final MqttTopicService  mqttTopicService;
	
	private final AuthenticatedUserService authenticatedUserService;
	
	public CreateMeasuringDevice(MeasuringDeviceRepository repository, MqttTopicService mqttTopicService, AuthenticatedUserService authenticatedUserService) {
		this.repository = repository;
		this.mqttTopicService = mqttTopicService;
		this.authenticatedUserService = authenticatedUserService;
	}
	
	public MeasuringDevice create(String tag, String location) {
		User user = authenticatedUserService.getUser();
		MeasuringDevice device = new MeasuringDevice(null, user.getId(), tag, location, null, new ArrayList<MeasuredValue>());
		String device_id = repository.create(device);
		device.setId(device_id);
		String formatedTag = tag.toUpperCase().replaceAll(" ", "_").replaceAll("/", "-").replaceAll("#", "H").replaceAll("\\+", "M");
		String topic = String.format("iotnizer/persist/%s/%s/%s",user.getId(), device.getId(),formatedTag);
		mqttTopicService.addTopic(user, topic);
		device.setMqttTopic(topic);
		device = repository.update(device);
		return device;
	}
	
	
}
