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

import com.dougfsilva.iotnizer.config.security.AuthenticationService;
import com.dougfsilva.iotnizer.dto.CreateMeasuringDeviceForm;
import com.dougfsilva.iotnizer.model.MeasuringDevice;
import com.dougfsilva.iotnizer.service.measuringDevice.CreateMeasuringDevice;
import com.dougfsilva.iotnizer.service.measuringDevice.DeleteMeasuringDevice;
import com.dougfsilva.iotnizer.service.measuringDevice.FindMeasuringDevice;
import com.dougfsilva.iotnizer.service.measuringDevice.UpdateMeasuringDevice;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/measuring-device")
public class MeasuringDeviceController {

	private final CreateMeasuringDevice createMeasuringDevice;
	
	private final DeleteMeasuringDevice deleteMeasuringDevice;
	
	private final UpdateMeasuringDevice updateMeasuringDevice;
	
	private final FindMeasuringDevice findMeasuringDevice;
	
	private final AuthenticationService authenticationService;

	public MeasuringDeviceController(CreateMeasuringDevice createMeasuringDevice,
			DeleteMeasuringDevice deleteMeasuringDevice, UpdateMeasuringDevice updateMeasuringDevice,
			FindMeasuringDevice findMeasuringDevice, AuthenticationService authenticationService) {
		this.createMeasuringDevice = createMeasuringDevice;
		this.deleteMeasuringDevice = deleteMeasuringDevice;
		this.updateMeasuringDevice = updateMeasuringDevice;
		this.findMeasuringDevice = findMeasuringDevice;
		this.authenticationService = authenticationService;
	}
	
	@PostMapping
	public ResponseEntity<MeasuringDevice> createMeasuringDevice(@Valid @RequestBody CreateMeasuringDeviceForm form){
		MeasuringDevice device = createMeasuringDevice.create(authenticationService.getAuthenticatedUser(), form.tag(), form.location());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(device.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleteMeasuringDevice(@RequestParam("id") String id) {
		deleteMeasuringDevice.delete(authenticationService.getAuthenticatedUser(), id);
		return ResponseEntity.noContent().build();
	}
	
	
}
