package com.dougfsilva.iotnizer.mqtt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Getter;

@Getter
@Service
public class MqttConnectionParam {

	@Value("${mqtt.host}")
	private String host;
	
	@Value("${mqtt.port}")
	private Integer port;
	
	@Value("${mqtt.username}")
	private String username;
	
	@Value("${mqtt.password}")
	private String password;
	
	public String getDynSecUriCommand() {
		String uri = String.format("mosquitto_ctrl -h %s -p %s -u %s -P %s dynsec ", this.host,
				this.port, this.username, this.password);
		return uri;
	}
}
