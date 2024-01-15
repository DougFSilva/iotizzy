package com.dougfsilva.iotizzy.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dougfsilva.iotizzy.config.security.AuthenticationService;
import com.dougfsilva.iotizzy.dto.CreateControlDeviceForm;
import com.dougfsilva.iotizzy.dto.UpdateControlDeviceForm;
import com.dougfsilva.iotizzy.model.ControlDevice;
import com.dougfsilva.iotizzy.service.controldevice.CreateControlDevice;
import com.dougfsilva.iotizzy.service.controldevice.DeleteControlDevice;
import com.dougfsilva.iotizzy.service.controldevice.FindControlDevice;
import com.dougfsilva.iotizzy.service.controldevice.UpdateControlDevice;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/control-device")
public class ControlDeviceController {

	private final CreateControlDevice createControlDevice;

	private final DeleteControlDevice deleteControlDevice;

	private final UpdateControlDevice updateControleDevice;

	private final FindControlDevice findControlDevice;
	
	public ControlDeviceController(CreateControlDevice createControlDevice, DeleteControlDevice deleteControlDevice,
			UpdateControlDevice updateControleDevice, FindControlDevice findControlDevice, AuthenticationService authenticationService) {
		this.createControlDevice = createControlDevice;
		this.deleteControlDevice = deleteControlDevice;
		this.findControlDevice = findControlDevice;
		this.updateControleDevice = updateControleDevice;
	}

	@PostMapping
	public ResponseEntity<String> createDevice(@RequestBody @Valid CreateControlDeviceForm form) {
		ControlDevice device = createControlDevice.create(form.deviceType(), form.tag(), form.location());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(device.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteDevice(@RequestParam("id") String id) {
		deleteControlDevice.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping
	public ResponseEntity<ControlDevice> updateDevice(@RequestParam("id") String id, @RequestBody UpdateControlDeviceForm form) {
		ControlDevice device = updateControleDevice.update(id, form.deviceType(), form.tag(), form.location());
		return ResponseEntity.ok().body(device);
	}
	
	@GetMapping
	public ResponseEntity<ControlDevice> findDeviceById(@RequestParam("id") String id) {
		ControlDevice device = findControlDevice.findById(id);
		return ResponseEntity.ok().body(device);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<ControlDevice>> findAllDevicesByUser(){
		List<ControlDevice> devices = findControlDevice.findAll();
		return ResponseEntity.ok().body(devices);
	}
	
}
