package com.dougfsilva.iotnizer.mqtt;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.User;

@Service
public class EnableClientMqtt {

	private final MqttParams mqtt;
	
	public EnableClientMqtt(MqttParams mqtt) {
		this.mqtt = mqtt;
	}

	public void enable(User user) {
		try {
			Runtime.getRuntime().exec(mqtt.getDynSecUriCommand() + String.format("enableClient %s", user.getEmail().getAddress()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void disable(User user) {
		try {
			Runtime.getRuntime().exec(mqtt.getDynSecUriCommand() + String.format("disableClient %s", user.getEmail().getAddress()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
