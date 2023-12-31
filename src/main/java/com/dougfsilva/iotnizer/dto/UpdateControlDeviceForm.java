package com.dougfsilva.iotnizer.dto;

import com.dougfsilva.iotnizer.model.ControlDeviceType;

public record UpdateControlDeviceForm(

		ControlDeviceType deviceType,

		String tag,

		String location) {

}
