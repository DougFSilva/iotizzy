package com.dougfsilva.iotnizer.mqtt;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.User;

@Service
public class DeleteClientMqtt {
	
private final MqttConnectionParam mqtt;
	
	public DeleteClientMqtt(MqttConnectionParam mqtt) {
		this.mqtt = mqtt;
	}


	public void delete(User user) {
		try {
			Runtime.getRuntime().exec(mqtt.getDynSecUriCommand() + String.format("deleteClient %s", user.getEmail().getAddress()));
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
}
