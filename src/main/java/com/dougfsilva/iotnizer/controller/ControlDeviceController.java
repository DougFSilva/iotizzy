package com.dougfsilva.iotnizer.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dougfsilva.iotnizer.dto.CreateControlDeviceForm;
import com.dougfsilva.iotnizer.model.ControlDevice;
import com.dougfsilva.iotnizer.service.controlDevice.CreateControlDevice;
import com.dougfsilva.iotnizer.service.controlDevice.DeleteControlDevice;
import com.dougfsilva.iotnizer.service.controlDevice.FindControlDevice;

@RestController
@RequestMapping("/control-device")
public class ControlDeviceController {
	
	private final CreateControlDevice createDevice;
	
	private final DeleteControlDevice deleteControlDevice;
	
	private final FindControlDevice findControlDevice;

	
	public ControlDeviceController(CreateControlDevice createDevice, DeleteControlDevice deleteControlDevice,
			FindControlDevice findControlDevice) {
		this.createDevice = createDevice;
		this.deleteControlDevice = deleteControlDevice;
		this.findControlDevice = findControlDevice;
	}

	@PostMapping
	public ResponseEntity<String> createDevice(@RequestBody CreateControlDeviceForm form){
		ControlDevice device = createDevice.create(form.user_id(), form.deviceType(), form.tag(), form.location());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(device.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleteDevice(@RequestParam("id") String id){
		deleteControlDevice.delete(id);
		return ResponseEntity.noContent().build();
	}

}
