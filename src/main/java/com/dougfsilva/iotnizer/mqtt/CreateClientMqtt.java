package com.dougfsilva.iotnizer.mqtt;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.User;

@Service
public class CreateClientMqtt {
	
	@Value("${mqtt.host}")
	private String host;
	
	@Value("${mqtt.port}")
	private Integer port;
	
	@Value("${mqtt.username}")
	private String username;
	
	@Value("${mqtt.password}")
	private String password;
	
	public void create(User user) {
		try {
		    Process exec = Runtime.getRuntime().exec( String.format("mosquitto_ctrl -h %s -p %s -u %s -P %s dynsec createClient %s", host, port, username, password, user.getEmail().getAddress() ));
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
