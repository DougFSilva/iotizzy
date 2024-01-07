package com.dougfsilva.iotnizer.service.measuringDevice;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.MeasuringDevice;
import com.dougfsilva.iotnizer.repository.MeasuringDeviceRepository;

@Service
public class RemoveValuesFromMeasuringDevice {

	private final MeasuringDeviceRepository repository;
	
	private final FindMeasuringDevice findMeasuringDevice;
	
	public RemoveValuesFromMeasuringDevice(MeasuringDeviceRepository repository, FindMeasuringDevice findMeasuringDevice) {
		this.repository = repository;
		this.findMeasuringDevice = findMeasuringDevice;
	}
	
	public void removeById(String device_id, String value_id) {
		MeasuringDevice device = findMeasuringDevice.findById(device_id);
		repository.removeValue(device, value_id);
	}
	
	public void removeByTimestamp(String id, LocalDateTime inicialTimetamp, LocalDateTime finalTimestamp) {
		MeasuringDevice device = findMeasuringDevice.findById(id);
		repository.removeValueByTimestamp(device, inicialTimetamp, finalTimestamp );
	}
	
	public void removeAll(String id) {
		MeasuringDevice device = findMeasuringDevice.findById(id);
		repository.removeAllValuesByDevice(device);
	}
}
