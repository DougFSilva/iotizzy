package com.dougfsilva.iotizzy.service.clientmqtt;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotizzy.mqtt.commands.ListClientsMqttCommand;

@Service
@PreAuthorize("hasRole('ADMIN')")
public class ListClientMqtt {

	private final ListClientsMqttCommand mqttCommand;

	public ListClientMqtt(ListClientsMqttCommand mqttCommand) {
		this.mqttCommand = mqttCommand;
	}

	public String list() {
		return mqttCommand.list();
	}

}
