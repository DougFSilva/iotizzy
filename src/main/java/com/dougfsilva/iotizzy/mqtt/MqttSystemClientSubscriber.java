package com.dougfsilva.iotizzy.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotizzy.exception.MqttFailException;
import com.dougfsilva.iotizzy.service.measuringDevice.AddValueFromMeasuringDeviceByMqttMessage;

@Service
public class MqttSystemClientSubscriber implements MqttCallback {
	
	private final MqttParams mqtt;
	
	private final AddValueFromMeasuringDeviceByMqttMessage addValueFromMeasuringDevice;
	
	public MqttSystemClientSubscriber(MqttParams mqtt,
			AddValueFromMeasuringDeviceByMqttMessage addValueFromMeasuringDevice) {
		this.mqtt = mqtt;
		this.addValueFromMeasuringDevice = addValueFromMeasuringDevice;
	}

	public void connect() {
		String uri = String.format("tcp://%s:%s",mqtt.getHost(),mqtt.getPort());
		try {
			MqttClient client = new MqttClient(uri, mqtt.getSystemSubscriberId(), new MqttDefaultFilePersistence());
			MqttConnectOptions options = new MqttConnectOptions();
			options.setUserName(mqtt.getSystemSubscriberUsername());
			options.setPassword(mqtt.getSystemSubscriberPassword().toCharArray());
			options.setCleanSession(false);
			options.setConnectionTimeout(0);
			options.setAutomaticReconnect(true);
			client.connect(options);
			if (client.isConnected()) {
				System.out.println("Mqtt Connected!");
				try {
					client.subscribe(mqtt.getSystemsubscriberTopic(), 1);
				} catch (MqttException e) {
					throw new MqttFailException("Impossible subscribe, cause: " + e.getMessage());
				}
				client.setCallback(this);
			}
		} catch (MqttException e) {
			throw new MqttFailException("Connection failure!, cause: " + e.getMessage());
		}
	}

	@Override
	public void connectionLost(Throwable cause) {
		System.out.println(cause);
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		addValueFromMeasuringDevice.add(topic, message);
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println(token);
	}

}
