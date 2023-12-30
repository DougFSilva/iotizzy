package com.dougfsilva.iotnizer.mqtt;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.ControlDevice;

@Service
public class AddTopicToClientMqtt {

	private final MqttParams mqtt;

	public AddTopicToClientMqtt(MqttParams mqtt) {
		this.mqtt = mqtt;
	}
	
	public void addTopic(ControlDevice device) {
		String rolename = mqtt.getDefaultRolename(device.getUser_id());
		String topic = mqtt.getDefaultTopicName(device);
		try {
			Runtime.getRuntime().exec(mqtt.getDynSecUriCommand() + String.format("addRoleACL %s publishClientSend %s allow", rolename, topic));
			Runtime.getRuntime().exec(mqtt.getDynSecUriCommand() + String.format("addRoleACL %s subscribeLiteral %s allow", rolename, topic));
			Runtime.getRuntime().exec(mqtt.getDynSecUriCommand() + String.format("addRoleACL %s unsubscribeLiteral %s allow", rolename, topic));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
