package com.dougfsilva.iotizzy.mqtt.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotizzy.model.User;
import com.dougfsilva.iotizzy.mqtt.MqttParams;

@Service
public class GetRoleMqttCommand {

	private final MqttParams mqtt;
	
	public GetRoleMqttCommand(MqttParams mqtt) {
		this.mqtt = mqtt;
	}

	public String get(User user) {
		String username = mqtt.getDefaultRolename(user.getId());
		String message = "";
		try {
			String line;
			Process getClient = Runtime.getRuntime().exec(mqtt.getDynSecUriCommand() + String.format("getRole %s", username));
			BufferedReader input = new BufferedReader(new InputStreamReader(getClient.getInputStream()));
			  while ((line = input.readLine()) != null) {
			    message += line;
			    message += "\n";
			  }
			  input.close();
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return message;
	}
}
