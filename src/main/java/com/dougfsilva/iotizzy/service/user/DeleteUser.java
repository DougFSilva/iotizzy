package com.dougfsilva.iotizzy.service.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotizzy.exception.OperationNotPermittedException;
import com.dougfsilva.iotizzy.model.User;
import com.dougfsilva.iotizzy.mqtt.commands.DeleteClientMqttCommand;
import com.dougfsilva.iotizzy.repository.UserRepository;
import com.dougfsilva.iotizzy.service.controldevice.DeleteControlDevice;
import com.dougfsilva.iotizzy.service.measuringDevice.DeleteMeasuringDevice;

@Service
@PreAuthorize("hasAnyRole('GOLD_USER', 'SILVER_USER', 'ADMIN')")
public class DeleteUser {
	
	@Value("${user.master.email}")
	private String masterEmail;
	
    private final UserRepository repository;
    private final DeleteClientMqttCommand deleteClientMqtt;
    private final DeleteMeasuringDevice deleteMeasuringDevice;
    private final DeleteControlDevice deleteControlDevice;
    private final AuthenticatedUser authenticatedUser;

	public DeleteUser(UserRepository repository, DeleteClientMqttCommand deleteClientMqtt,
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
        if(user.getEmail().getAddress().equals(masterEmail)) {
        	throw new OperationNotPermittedException("It is not allowed to delete the master user");
        }
        repository.delete(user);
        deleteClientMqtt.delete(user);
        deleteMeasuringDevice.deleteAll();
        deleteControlDevice.deleteAll();
    }

}
