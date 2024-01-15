package com.dougfsilva.iotizzy.service.clientmqtt;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotizzy.model.User;
import com.dougfsilva.iotizzy.mqtt.commands.GetRoleMqttCommand;
import com.dougfsilva.iotizzy.service.admin.FindUserAsAdmin;

@Service
@PreAuthorize("hasRole('ADMIN')")
public class GetRoleMqtt {

	private final GetRoleMqttCommand mqttCommand;

	private final FindUserAsAdmin findUser;

	public GetRoleMqtt(GetRoleMqttCommand mqttCommand, FindUserAsAdmin findUser) {
		this.mqttCommand = mqttCommand;
		this.findUser = findUser;
	}

	public String get(String user_id) {
		User user = findUser.findById(user_id);
		return mqttCommand.get(user);
	}
}
