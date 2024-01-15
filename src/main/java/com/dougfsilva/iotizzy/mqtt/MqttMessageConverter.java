package com.dougfsilva.iotizzy.mqtt;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotizzy.dto.AddMeasuredValueForm;
import com.dougfsilva.iotizzy.exception.MqttFailException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

@Service
public class MqttMessageConverter {

	public AddMeasuredValueForm toAddMeasuredValueForm(MqttMessage message) {
		try {
			Gson gson = new GsonBuilder()
					.registerTypeAdapter(LocalDateTime.class,
							(JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> LocalDateTime
									.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
					.create();
			return gson.fromJson(message.toString(), AddMeasuredValueForm.class);
		} catch (Exception e) {
			throw new MqttFailException( String.format("Error converting MqttMessage to AddMeasuredValueForm \n %s ", e.getMessage()));
		}
	}
}
