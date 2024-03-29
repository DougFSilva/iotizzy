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
	
	public void deleteAll() {
		User user = authenticatedUser.getUser();
		repository.deleteAllByUser(user);
	}
}
