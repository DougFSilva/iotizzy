package com.dougfsilva.iotizzy.service.measuringDevice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotizzy.dto.AddMeasuredValueForm;
import com.dougfsilva.iotizzy.model.MeasuredValue;
import com.dougfsilva.iotizzy.mqtt.MqttMessageConverter;
import com.dougfsilva.iotizzy.repository.MeasuringDeviceRepository;

@Service
public class AddValueFromMeasuringDeviceByMqttMessage {

	private final MeasuringDeviceRepository repository;
	
	private final MqttMessageConverter messageConverter;

	public AddValueFromMeasuringDeviceByMqttMessage(MeasuringDeviceRepository repository,
			MqttMessageConverter messageConverter) {
		this.repository = repository;
		this.messageConverter = messageConverter;
	}

	public void add(String topic, MqttMessage message) {
		String[] topicSplit = topic.split("/");
		String user_id = topicSplit[2];
		AddMeasuredValueForm form = messageConverter.toAddMeasuredValueForm(message);
		List<MeasuredValue> measuredValues = new ArrayList<>();
		if(form.timestamp() == null) {
			 form.values().forEach(value -> measuredValues.add(new MeasuredValue(null, LocalDateTime.now(), value)));
		}else {
			form.values().forEach(value -> measuredValues.add(new MeasuredValue(null, form.timestamp(), value)));
		}
		repository.addValues(user_id, form.device_id() , measuredValues);
	}
	
}
