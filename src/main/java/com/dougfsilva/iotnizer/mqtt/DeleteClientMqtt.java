package com.dougfsilva.iotnizer.mqtt;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.User;

@Service
public class DeleteClientMqtt {

	private final MqttParams mqtt;

	public DeleteClientMqtt(MqttParams mqtt) {
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
