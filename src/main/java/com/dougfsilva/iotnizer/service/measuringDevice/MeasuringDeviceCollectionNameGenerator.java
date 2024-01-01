package com.dougfsilva.iotnizer.service.measuringDevice;

import com.dougfsilva.iotnizer.model.MeasuringDevice;

public class MeasuringDeviceCollectionNameGenerator {

	public static String generate(MeasuringDevice device) {
		return String.format("md_%s", device.getId());
	}
}
