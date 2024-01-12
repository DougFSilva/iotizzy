package com.dougfsilva.iotnizer.mqtt.commands;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.MqttParams;

@Service
public class EnableClientMqttCommand {

	private final MqttParams mqtt;
	
	public EnableClientMqttCommand(MqttParams mqtt) {
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
