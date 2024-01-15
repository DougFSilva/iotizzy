package com.dougfsilva.iotizzy.service.measuringDevice;

import com.dougfsilva.iotizzy.model.MeasuringDevice;

public class MeasuringDeviceCollectionNameGenerator {

	public static String generate(MeasuringDevice device) {
		return String.format("md_%s", device.getId());
	}
}
