package com.dougfsilva.iotizzy.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public record FilterMeasuringDeviceForm(
		
		@NotBlank String id,
		
		LocalDateTime initialTimestamp,
		
		LocalDateTime finalTimestamp,
		
		Double initialValue,
		
		Double finalValue,
		
		Integer limit
		) {

}
