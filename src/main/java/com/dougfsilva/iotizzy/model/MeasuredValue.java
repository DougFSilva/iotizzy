package com.dougfsilva.iotizzy.model;

import java.time.LocalDateTime;

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
public class MeasuredValue {

	private String id;
	
	private LocalDateTime timestamp;
	
	private Double value;
}
