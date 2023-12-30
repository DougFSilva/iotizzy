package com.dougfsilva.iotnizer.mqtt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.ControlDevice;
import com.dougfsilva.iotnizer.model.User;

import lombok.Getter;

@Getter
@Service
public class MqttParams {

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
	
	public String getDefaultClientUsername(User user) {
		return user.getEmail().getAddress();
	}
	
	public String getDefaultRolename(String user_id) {
		return String.format("role_%s", user_id);
	}
	
	public String getDefaultTopicName(ControlDevice device) {
		return String.format("iotnizer/%s", device.getId());
	}
}
