package com.dougfsilva.iotizzy.mqtt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotizzy.model.ControlDevice;
import com.dougfsilva.iotizzy.model.User;

import lombok.Getter;

@Getter
@Service
public class MqttParams {

	@Value("${mqtt.host}")
	private String host;
	
	@Value("${mqtt.port}")
	private Integer port;
	
	@Value("${mqtt.client.dynsec.username}")
	private String dynsecClientUsername;
	
	@Value("${mqtt.client.dynsec.password}")
	private String dynsecClientPassword;
	
	@Value("${mqtt.client.systemsubscriber.username}")
	private String systemSubscriberUsername;
	
	@Value("${mqtt.client.systemsubscriber.password}")
	private String systemSubscriberPassword;
	
	@Value("${mqtt.client.systemsubscriber.id}")
	private String systemSubscriberId;
	
	private final String systemsubscriberTopic = "iotnizer/persist/#";
	
	
	public String getDynSecUriCommand() {
		String uri = String.format("mosquitto_ctrl -h %s -p %s -u %s -P %s dynsec ", this.host,
				this.port, this.dynsecClientUsername, this.dynsecClientPassword);
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
