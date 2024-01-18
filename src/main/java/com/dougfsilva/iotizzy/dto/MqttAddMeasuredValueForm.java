package com.dougfsilva.iotizzy.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MqttAddMeasuredValueForm(

		@NotBlank String device_id,

		@NotNull List<Double> values,

		LocalDateTime timestamp,
		
		@NotNull Boolean persist
		) {

}
