package com.dougfsilva.iotnizer.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dougfsilva.iotnizer.config.security.AuthenticationService;
import com.dougfsilva.iotnizer.dto.CreateControlDeviceForm;
import com.dougfsilva.iotnizer.dto.UpdateControlDeviceForm;
import com.dougfsilva.iotnizer.model.ControlDevice;
import com.dougfsilva.iotnizer.service.controlDevice.CreateControlDevice;
import com.dougfsilva.iotnizer.service.controlDevice.DeleteControlDevice;
import com.dougfsilva.iotnizer.service.controlDevice.FindControlDevice;
import com.dougfsilva.iotnizer.service.controlDevice.UpdateControlDevice;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/control-device")
public class ControlDeviceController {

	private final CreateControlDevice createControlDevice;

	private final DeleteControlDevice deleteControlDevice;

	private final UpdateControlDevice updateControleDevice;

	private final FindControlDevice findControlDevice;
	
	private final AuthenticationService authenticationService;

	public ControlDeviceController(CreateControlDevice createControlDevice, DeleteControlDevice deleteControlDevice,
			UpdateControlDevice updateControleDevice, FindControlDevice findControlDevice, AuthenticationService authenticationService) {
		this.createControlDevice = createControlDevice;
		this.deleteControlDevice = deleteControlDevice;
		this.findControlDevice = findControlDevice;
		this.updateControleDevice = updateControleDevice;
		this.authenticationService = authenticationService;
	}

	@PostMapping
	public ResponseEntity<String> createDevice(@RequestBody @Valid CreateControlDeviceForm form) {
		ControlDevice device = createControlDevice.create(authenticationService.getAuthenticatedUser(), form.deviceType(), form.tag(), form.location());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(device.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteDevice(@RequestParam("id") String id) {
		deleteControlDevice.delete(authenticationService.getAuthenticatedUser(), id);
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
	
	@GetMapping("/user/{user_id}")
	public ResponseEntity<List<ControlDevice>> findAllDevicesByUser(@PathVariable String user_id){
		List<ControlDevice> devices = findControlDevice.findAllByUser(user_id);
		return ResponseEntity.ok().body(devices);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<ControlDevice>> findAllDevices(){
		List<ControlDevice> devices = findControlDevice.findAll();
		return ResponseEntity.ok().body(devices);
	}
	
}
