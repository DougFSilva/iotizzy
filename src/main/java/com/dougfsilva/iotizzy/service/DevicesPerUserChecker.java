package com.dougfsilva.iotizzy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotizzy.exception.OperationNotPermittedException;
import com.dougfsilva.iotizzy.model.ProfileType;
import com.dougfsilva.iotizzy.model.User;
import com.dougfsilva.iotizzy.repository.ControlDeviceRepository;
import com.dougfsilva.iotizzy.repository.MeasuringDeviceRepository;

@Service
public class DevicesPerUserChecker {

	private final ControlDeviceRepository controlDeviceRepository;

	private final MeasuringDeviceRepository measuringDeviceRepository;

	@Value("${user.silver.controldevices}")
	private Integer silverUserMaxControlDevices;

	@Value("${user.gold.controldevices}")
	private Integer goldUserMaxControlDevices;

	@Value("${user.silver.measuringdevices}")
	private Integer silverUserMaxMeasuringDevices;

	@Value("${user.gold.measuringdevices}")
	private Integer goldUserMaxMeasuringDevices;

	public DevicesPerUserChecker(ControlDeviceRepository controlDeviceRepository,
			MeasuringDeviceRepository measuringDeviceRepository) {
		this.controlDeviceRepository = controlDeviceRepository;
		this.measuringDeviceRepository = measuringDeviceRepository;
	}

	public DevicesPerUserChecker checkNumberOfControlDevices(User user) {
		ProfileType profileType = filterMostRelevantProfile(user);
		Long countedDevices = controlDeviceRepository.countDevicesByUser(user);
		if ((profileType == ProfileType.GOLD_USER) && (countedDevices < goldUserMaxControlDevices)) {
			return this;
		} else if ((profileType == ProfileType.SILVER_USER) && (countedDevices < silverUserMaxControlDevices)) {
			return this;
		} else {
			throw new OperationNotPermittedException(String.format("Limit of control devices per user exceeded! The user has %s control device(s). "
					+ "Silver users can register up to %s devices and gold users up to %s devices", 
					countedDevices, silverUserMaxControlDevices, goldUserMaxControlDevices));
		}
	}
	
	public DevicesPerUserChecker checkNumberOfMeasuringDevices(User user) {
		ProfileType profileType = filterMostRelevantProfile(user);
		Long countedDevices = measuringDeviceRepository.countDevicesByUser(user);
		if ((profileType == ProfileType.GOLD_USER) && (countedDevices < goldUserMaxMeasuringDevices)) {
			return this;
		} else if ((profileType == ProfileType.SILVER_USER) && (countedDevices < silverUserMaxMeasuringDevices)) {
			return this;
		} else {
			throw new OperationNotPermittedException(String.format("Limit of measuring devices per user exceeded! The user has %s measuring device(s). "
					+ "Silver users can register up to %s devices and gold users up to %s devices", 
					countedDevices, silverUserMaxMeasuringDevices, goldUserMaxMeasuringDevices));
		}
	}
	
	private ProfileType filterMostRelevantProfile(User user) {
		ProfileType profileType = user.getProfiles().stream()
				.sorted((p1, p2) -> Long.compare(p1.getProfileType().getCod(), p2.getProfileType().getCod()))
				.findFirst().get().getProfileType();
		return profileType;
	}

}
