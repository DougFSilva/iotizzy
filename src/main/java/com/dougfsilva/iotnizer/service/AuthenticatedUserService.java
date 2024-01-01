package com.dougfsilva.iotnizer.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.exception.OperationNotPermittedException;
import com.dougfsilva.iotnizer.model.ControlDevice;
import com.dougfsilva.iotnizer.model.MeasuringDevice;
import com.dougfsilva.iotnizer.model.User;

@Service
public class AuthenticatedUserService {

	public void deviceVerify(ControlDevice device) {
		if(!getUser().getId().equals(device.getUser_id())) {
			throw new OperationNotPermittedException("This device does not belong to you!");
		}
	}
	
	public void deviceVerify(MeasuringDevice device) {
		if(!getUser().getId().equals(device.getUser_id())) {
			throw new OperationNotPermittedException("This device does not belong to you!");
		}
	}

	public User getUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
