package com.dougfsilva.iotnizer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class MeasuringDevice {

	private String id;

	private String user_id;

	private String tag;

	private String location;

	private String mqttTopic;

}
