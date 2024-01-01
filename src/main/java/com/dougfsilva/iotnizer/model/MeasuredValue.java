package com.dougfsilva.iotnizer.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class MeasuredValue {

	private String id;
	
	private String device_id;
	
	private Date timestamp;
	
	private Double value;
}
