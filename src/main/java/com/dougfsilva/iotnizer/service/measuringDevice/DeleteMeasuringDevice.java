package com.dougfsilva.iotnizer.service.measuringDevice;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.exception.OperationNotPermittedException;
import com.dougfsilva.iotnizer.model.MeasuringDevice;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.MqttTopicService;
import com.dougfsilva.iotnizer.repository.MeasurindDeviceRepository;

@Service
public class DeleteMeasuringDevice {

	private final MeasurindDeviceRepository repository;

	private final FindMeasuringDevice findMeasuringDevice;
	
	private final MqttTopicService mqttTopicService;

	public DeleteMeasuringDevice(MeasurindDeviceRepository repository, FindMeasuringDevice findMeasuringDevice, MqttTopicService mqttTopicService) {
		this.repository = repository;
		this.findMeasuringDevice = findMeasuringDevice;
		this.mqttTopicService = mqttTopicService;
	}
	
	public void delete(User user, String id) {
		MeasuringDevice device = findMeasuringDevice.findById(id);
		if(!user.getId().equals(device.getUser_id())) {
			throw new OperationNotPermittedException("Deleting devices belonging to another user is not allowed!");
		}
		mqttTopicService.removeTopic(user, device.getMqttTopic());
		repository.delete(device);
		repository.deleteCollection(MeasuringDeviceCollectionNameGenerator.generate(device));
	}
}
