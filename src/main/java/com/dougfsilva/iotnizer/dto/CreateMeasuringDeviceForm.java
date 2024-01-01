package com.dougfsilva.iotnizer.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateMeasuringDeviceForm(

		@NotBlank String tag,

		@NotBlank String location) {

}
