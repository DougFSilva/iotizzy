package com.dougfsilva.iotnizer.service.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.DeleteClientMqtt;
import com.dougfsilva.iotnizer.repository.UserRepository;
import com.dougfsilva.iotnizer.service.controlDevice.DeleteControlDevice;
import com.dougfsilva.iotnizer.service.measuringDevice.DeleteMeasuringDevice;

@Service
@PreAuthorize("hasAnyRole('GOLD', 'SILVER')")
public class DeleteUser {
	
    private final UserRepository repository;
    private final DeleteClientMqtt deleteClientMqtt;
    private final DeleteMeasuringDevice deleteMeasuringDevice;
    private final DeleteControlDevice deleteControlDevice;
    private final AuthenticatedUser authenticatedUser;

	public DeleteUser(UserRepository repository, DeleteClientMqtt deleteClientMqtt,
			DeleteMeasuringDevice deleteMeasuringDevice, DeleteControlDevice deleteControlDevice,
			AuthenticatedUser authenticatedUser) {
		this.repository = repository;
		this.deleteClientMqtt = deleteClientMqtt;
		this.deleteMeasuringDevice = deleteMeasuringDevice;
		this.deleteControlDevice = deleteControlDevice;
		this.authenticatedUser = authenticatedUser;
	}

	public void delete(){
        User user = authenticatedUser.getUser();
        repository.delete(user);
        deleteClientMqtt.delete(user);
        deleteMeasuringDevice.deleteAll();
        deleteControlDevice.deleteAll();
    }

}
