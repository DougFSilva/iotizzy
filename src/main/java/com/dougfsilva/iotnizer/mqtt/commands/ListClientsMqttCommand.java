package com.dougfsilva.iotnizer.mqtt.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.mqtt.MqttParams;

@Service
public class ListClientsMqttCommand {
	
private final MqttParams mqtt;
	
	public ListClientsMqttCommand(MqttParams mqtt) {
		this.mqtt = mqtt;
	}

	public String list() {
		String message = "";
		try {
			String line;
			Process getClient = Runtime.getRuntime().exec(mqtt.getDynSecUriCommand() + String.format("listClients"));
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
