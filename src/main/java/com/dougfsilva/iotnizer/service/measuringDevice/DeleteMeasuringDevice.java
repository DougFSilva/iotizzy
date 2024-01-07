package com.dougfsilva.iotnizer.service.measuringDevice;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.MeasuringDevice;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.MqttTopicService;
import com.dougfsilva.iotnizer.repository.MeasuringDeviceRepository;
import com.dougfsilva.iotnizer.service.user.AuthenticatedUser;

@Service
public class DeleteMeasuringDevice {

	private final MeasuringDeviceRepository repository;

	private final FindMeasuringDevice findMeasuringDevice;
	
	private final MqttTopicService mqttTopicService;
	
	private final AuthenticatedUser authenticatedUser;

	public DeleteMeasuringDevice(MeasuringDeviceRepository repository, FindMeasuringDevice findMeasuringDevice,
			MqttTopicService mqttTopicService, AuthenticatedUser authenticatedUser) {
		this.repository = repository;
		this.findMeasuringDevice = findMeasuringDevice;
		this.mqttTopicService = mqttTopicService;
		this.authenticatedUser = authenticatedUser;
	}
	
	public void delete(String id) {
		MeasuringDevice device = findMeasuringDevice.findById(id);
		User user = authenticatedUser.getUser();
		mqttTopicService.removeTopic(user, device.getMqttTopic());
		repository.delete(user, device);
	}
	
	public void deleteAllByUser(User user) {
		repository.deleteAllByUser(user);
	}
}
