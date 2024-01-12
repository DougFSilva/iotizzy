package com.dougfsilva.iotnizer.service.controldevice;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.ControlDevice;
import com.dougfsilva.iotnizer.model.ControlDeviceType;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.MqttTopicService;
import com.dougfsilva.iotnizer.repository.ControlDeviceRepository;
import com.dougfsilva.iotnizer.service.DevicesPerUserChecker;
import com.dougfsilva.iotnizer.service.user.AuthenticatedUser;
import com.dougfsilva.iotnizer.service.user.UserPermissionsChecker;

@Service
@PreAuthorize("hasAnyRole('GOLD_USER', 'SILVER_USER', 'ADMIN')")
public class CreateControlDevice {

	private final ControlDeviceRepository repository;
	
	private final MqttTopicService mqttTopicService;
	
	private final AuthenticatedUser authenticatedUser;
	
	private final UserPermissionsChecker permissionsChecker;
	
	private final DevicesPerUserChecker devicesPerUserChecker;

	public CreateControlDevice(ControlDeviceRepository repository, MqttTopicService mqttTopicService,
			AuthenticatedUser authenticatedUser, UserPermissionsChecker permissionsChecker,
			DevicesPerUserChecker devicesPerUserChecker) {
		super();
		this.repository = repository;
		this.mqttTopicService = mqttTopicService;
		this.authenticatedUser = authenticatedUser;
		this.permissionsChecker = permissionsChecker;
		this.devicesPerUserChecker = devicesPerUserChecker;
	}


	public ControlDevice create(ControlDeviceType deviceType, String tag, String location) {
		User user = authenticatedUser.getUser();
		permissionsChecker.checkBlock(user);
		devicesPerUserChecker.checkNumberOfControlDevices(user);
		String formatedTag = tag.toUpperCase().replaceAll(" ", "_").replaceAll("/", "-").replaceAll("#", "H").replaceAll("\\+", "M");
		String topic = String.format("iotnizer/%s/%s",user.getId(), formatedTag);
		mqttTopicService.addTopic(user, topic);
		ControlDevice device = new ControlDevice(null, user.getId(), deviceType, tag, location, topic);
		String device_id = repository.create(device);
		device.setId(device_id);
		return device;
	}
	
}
