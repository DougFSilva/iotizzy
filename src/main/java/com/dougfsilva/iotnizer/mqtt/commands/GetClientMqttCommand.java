package com.dougfsilva.iotnizer.mqtt.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.MqttParams;

@Service
public class GetClientMqttCommand {
	
	private final MqttParams mqtt;
	
	public GetClientMqttCommand(MqttParams mqtt) {
		this.mqtt = mqtt;
	}

	public String get(User user) {
		String username = mqtt.getDefaultClientUsername(user);
		String message = "";
		try {
			String line;
			Process getClient = Runtime.getRuntime().exec(mqtt.getDynSecUriCommand() + String.format("getClient %s", username));
			BufferedReader input = new BufferedReader(new InputStreamReader(getClient.getInputStream()));
			  while ((line = input.readLine()) != null) {
			    message += line;
			    message += "; ";
			  }
			  input.close();
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return message;
	}
}
