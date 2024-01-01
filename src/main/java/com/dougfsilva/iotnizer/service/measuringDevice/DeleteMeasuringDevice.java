package com.dougfsilva.iotnizer.service.measuringDevice;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.MeasuringDevice;
import com.dougfsilva.iotnizer.mqtt.MqttTopicService;
import com.dougfsilva.iotnizer.repository.MeasurindDeviceRepository;
import com.dougfsilva.iotnizer.service.AuthenticatedUserService;

@Service
public class DeleteMeasuringDevice {

	private final MeasurindDeviceRepository repository;

	private final FindMeasuringDevice findMeasuringDevice;
	
	private final MqttTopicService mqttTopicService;
	
	private final AuthenticatedUserService authenticatedUserService;

	public DeleteMeasuringDevice(MeasurindDeviceRepository repository, FindMeasuringDevice findMeasuringDevice,
			MqttTopicService mqttTopicService, AuthenticatedUserService authenticatedUserService) {
		this.repository = repository;
		this.findMeasuringDevice = findMeasuringDevice;
		this.mqttTopicService = mqttTopicService;
		this.authenticatedUserService = authenticatedUserService;
	}
	
	public void delete(String id) {
		MeasuringDevice device = findMeasuringDevice.findById(id);
		mqttTopicService.removeTopic(authenticatedUserService.getUser(), device.getMqttTopic());
		repository.delete(device);
		repository.deleteCollection(MeasuringDeviceCollectionNameGenerator.generate(device));
	}
}
