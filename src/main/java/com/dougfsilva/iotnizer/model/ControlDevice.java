package com.dougfsilva.iotnizer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ControlDevice {

	private String id;
	
	private String user_id;
	
	private ControlDeviceType deviceType;
	
	private String tag;
	
	private String location;
	
}
