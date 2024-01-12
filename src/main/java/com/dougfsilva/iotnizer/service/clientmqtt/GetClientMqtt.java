package com.dougfsilva.iotnizer.service.clientmqtt;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.commands.GetClientMqttCommand;
import com.dougfsilva.iotnizer.service.admin.FindUserAsAdmin;

@Service
public class GetClientMqtt {

	private final GetClientMqttCommand getClientMqttCommand;
	
	private final FindUserAsAdmin findUser;

	public GetClientMqtt(GetClientMqttCommand getClientMqttCommand, FindUserAsAdmin findUser) {
		this.getClientMqttCommand = getClientMqttCommand;
		this.findUser = findUser;
	}

	public String get(String user_id) {
		User user = findUser.findById(user_id);
		return getClientMqttCommand.get(user);
	}
	
	
}
