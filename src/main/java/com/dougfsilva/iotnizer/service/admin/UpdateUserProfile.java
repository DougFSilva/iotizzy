package com.dougfsilva.iotnizer.service.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.Profile;
import com.dougfsilva.iotnizer.model.ProfileType;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.repository.UserRepository;
import com.dougfsilva.iotnizer.service.DevicesPerUserChecker;

@Service
@PreAuthorize("hasRole('ADMIN')")
public class UpdateUserProfile {

	private final FindUserAsAdmin findUser;

	private final UserRepository repository;
	
	private final DevicesPerUserChecker devicesPerUserChecker;


	public UpdateUserProfile(FindUserAsAdmin findUser, UserRepository repository,
			DevicesPerUserChecker devicesPerUserChecker) {
		this.findUser = findUser;
		this.repository = repository;
		this.devicesPerUserChecker = devicesPerUserChecker;
	}

	public User updateProfile(String id, Long profileCod) {
		ProfileType profileType = ProfileType.toEnum(profileCod);
		User user = findUser.findById(id);
		List<Profile> profiles = new ArrayList<>();
		profiles.add(new Profile(profileType));
		user.setProfiles(profiles);
		if (profileType.equals(ProfileType.SILVER_USER)) {
			devicesPerUserChecker.checkNumberOfControlDevices(user).checkNumberOfMeasuringDevices(user);
		}
		return repository.update(user);
	}

}
