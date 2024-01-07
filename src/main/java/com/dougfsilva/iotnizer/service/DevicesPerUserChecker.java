package com.dougfsilva.iotnizer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.exception.OperationNotPermittedException;
import com.dougfsilva.iotnizer.model.ProfileType;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.repository.ControlDeviceRepository;
import com.dougfsilva.iotnizer.repository.MeasuringDeviceRepository;

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

	public void checkNumberOfControlDevices(User user) {
		ProfileType profileType = filterMostRelevantProfile(user);
		Long countedDevices = controlDeviceRepository.countDevicesByUser(user);
		if ((profileType == ProfileType.GOLD_USER) && (countedDevices < goldUserMaxControlDevices)) {
			return;
		} else if ((profileType == ProfileType.SILVER_USER) && (countedDevices < silverUserMaxControlDevices)) {
			return;
		} else {
			throw new OperationNotPermittedException("Limit of control devices per user exceeded!");
		}
	}
	
	public void checkNumberOfMeasuringDevices(User user) {
		ProfileType profileType = filterMostRelevantProfile(user);
		Long countedDevices = measuringDeviceRepository.countDevicesByUser(user);
		if ((profileType == ProfileType.GOLD_USER) && (countedDevices < goldUserMaxMeasuringDevices)) {
			return;
		} else if ((profileType == ProfileType.SILVER_USER) && (countedDevices < silverUserMaxMeasuringDevices)) {
			return;
		} else {
			throw new OperationNotPermittedException("Limit of measuring devices per user exceeded!");
		}
	}
	
	private ProfileType filterMostRelevantProfile(User user) {
		ProfileType profileType = user.getProfiles().stream()
				.sorted((p1, p2) -> Long.compare(p1.getProfileType().getCod(), p2.getProfileType().getCod()))
				.findFirst().get().getProfileType();
		return profileType;
	}

}
