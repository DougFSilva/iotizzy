package com.dougfsilva.iotnizer.service.user;

import org.springframework.stereotype.Service;

import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.mqtt.DeleteClientMqtt;
import com.dougfsilva.iotnizer.repository.UserRepository;
import com.dougfsilva.iotnizer.service.controlDevice.DeleteControlDevice;
import com.dougfsilva.iotnizer.service.measuringDevice.DeleteMeasuringDevice;

@Service
public class DeleteUser {
	
    private final UserRepository repository;
    private final FindUser findUser;
    private final DeleteClientMqtt deleteClientMqtt;
    private final DeleteMeasuringDevice deleteMeasuringDevice;
    private final DeleteControlDevice deleteControlDevice;


    public DeleteUser(UserRepository repository, FindUser findUser, DeleteClientMqtt deleteClientMqtt,
			DeleteMeasuringDevice deleteMeasuringDevice, DeleteControlDevice deleteControlDevice) {
		this.repository = repository;
		this.findUser = findUser;
		this.deleteClientMqtt = deleteClientMqtt;
		this.deleteMeasuringDevice = deleteMeasuringDevice;
		this.deleteControlDevice = deleteControlDevice;
	}


	public void delete(String id){
        User user = findUser.findById(id);	
        this.repository.delete(user);
        deleteClientMqtt.delete(user);
        deleteMeasuringDevice.deleteAllByUser(user);
        deleteControlDevice.deleteAllByUser(user);
    }

}
