package com.dougfsilva.iotnizer.mqtt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.User;

@Service
public class DeleteClientMqtt {
	
	@Value("${mqtt.host}")
	private String host;
	
	@Value("${mqtt.port}")
	private Integer port;
	
	@Value("${mqtt.username}")
	private String username;
	
	@Value("${mqtt.password}")
	private String password;

	public void delete(User user) {
		try {
		    Runtime.getRuntime().exec( String.format("mosquitto_ctrl -h localhost -p 1883 -u admin -P 170515 dynsec deleteClient %s", user.getEmail().getAddress() ));
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
}
