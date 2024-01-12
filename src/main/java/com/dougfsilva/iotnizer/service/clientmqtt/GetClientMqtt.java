package com.dougfsilva.iotnizer.service.clientmqtt;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.commands.GetClientMqttCommand;
import com.dougfsilva.iotnizer.service.admin.FindUserAsAdmin;

@Service
@PreAuthorize("hasRole('ADMIN')")
public class GetClientMqtt {

	private final GetClientMqttCommand mqttCommand;

	private final FindUserAsAdmin findUser;

	public GetClientMqtt(GetClientMqttCommand mqttCommand, FindUserAsAdmin findUser) {
		this.mqttCommand = mqttCommand;
		this.findUser = findUser;
	}

	public String get(String user_id) {
		User user = findUser.findById(user_id);
		return mqttCommand.get(user);
	}

}
