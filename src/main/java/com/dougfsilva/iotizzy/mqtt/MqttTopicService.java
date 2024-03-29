package com.dougfsilva.iotizzy.mqtt;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotizzy.model.User;

@Service
public class MqttTopicService {

	private final MqttParams mqtt;

	public MqttTopicService(MqttParams mqtt) {
		this.mqtt = mqtt;
	}
	
	public void addTopic(User user, String topic) {
		String rolename = mqtt.getDefaultRolename(user.getId());
		try {
			Runtime.getRuntime().exec(mqtt.getDynSecUriCommand() + String.format("addRoleACL %s publishClientSend %s allow", rolename, topic));
			Runtime.getRuntime().exec(mqtt.getDynSecUriCommand() + String.format("addRoleACL %s subscribeLiteral %s allow", rolename, topic));
			Runtime.getRuntime().exec(mqtt.getDynSecUriCommand() + String.format("addRoleACL %s unsubscribeLiteral %s allow", rolename, topic));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeTopic(User user, String topic) {
		String rolename = mqtt.getDefaultRolename(user.getId());
		try {
			Runtime.getRuntime().exec(mqtt.getDynSecUriCommand() + String.format("removeRoleACL %s publishClientSend %s", rolename, topic));
			Runtime.getRuntime().exec(mqtt.getDynSecUriCommand() + String.format("removeRoleACL %s subscribeLiteral %s", rolename, topic));
			Runtime.getRuntime().exec(mqtt.getDynSecUriCommand() + String.format("removeRoleACL %s unsubscribeLiteral %s", rolename, topic));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
