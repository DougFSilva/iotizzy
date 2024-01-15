package com.dougfsilva.iotizzy.dto;

import com.dougfsilva.iotizzy.model.ControlDeviceType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateControlDeviceForm(
		
		@NotNull ControlDeviceType deviceType,
		
		@NotBlank String tag,
		
		@NotBlank String location
		) {

}
