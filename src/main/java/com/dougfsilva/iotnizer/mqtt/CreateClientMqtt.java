package com.dougfsilva.iotnizer.mqtt;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.User;

@Service
public class CreateClientMqtt {

	private final MqttConnectionParam mqtt;
	
	public CreateClientMqtt(MqttConnectionParam mqtt) {
		this.mqtt = mqtt;
	}

	public void create(User user) {
		try {
			Process exec = Runtime.getRuntime().exec(mqtt.getDynSecUriCommand() + String.format("createClient %s", user.getEmail().getAddress()));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(exec.getOutputStream()));
			out.write(user.getClientMqttPassword() + "\n");
			out.flush();
			out.write(user.getClientMqttPassword() + "\n");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
