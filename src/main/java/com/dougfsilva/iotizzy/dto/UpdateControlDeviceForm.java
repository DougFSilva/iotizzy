package com.dougfsilva.iotizzy.dto;

import com.dougfsilva.iotizzy.model.ControlDeviceType;

public record UpdateControlDeviceForm(

		ControlDeviceType deviceType,

		String tag,

		String location) {

}
