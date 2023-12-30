package com.dougfsilva.iotnizer.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dougfsilva.iotnizer.dto.CreateControlDeviceForm;
import com.dougfsilva.iotnizer.service.controlDevice.CreateControlDevice;

@RestController
@RequestMapping("/control-device")
public class ControlDeviceController {
	
	private final CreateControlDevice createDevice;

	public ControlDeviceController(CreateControlDevice createDevice) {
		this.createDevice = createDevice;
	}
	
	@PostMapping
	public ResponseEntity<String> createDevice(@RequestBody CreateControlDeviceForm form){
		String device_id = createDevice.create(form.user_id(), form.deviceType(), form.tag(), form.location());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(device_id).toUri();
		return ResponseEntity.created(uri).build();
	}

}
