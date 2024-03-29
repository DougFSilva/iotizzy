package com.dougfsilva.iotizzy.mqtt.commands;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotizzy.model.User;
import com.dougfsilva.iotizzy.mqtt.MqttParams;

@Service
public class DeleteClientMqttCommand {

	private final MqttParams mqtt;

	public DeleteClientMqttCommand(MqttParams mqtt) {
		this.mqtt = mqtt;
	}

	public void delete(User user) {
		String username = mqtt.getDefaultClientUsername(user);
		String rolename = mqtt.getDefaultRolename(user.getId());
		try {
			Runtime.getRuntime().exec(mqtt.getDynSecUriCommand() + String.format("deleteClient %s", username));
			Runtime.getRuntime().exec(mqtt.getDynSecUriCommand() + String.format("deleteRole %s", rolename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
