package com.dougfsilva.iotnizer.controller;

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

import com.dougfsilva.iotnizer.dto.CreateMeasuringDeviceForm;
import com.dougfsilva.iotnizer.dto.UpdateMeasuringDeviceForm;
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
	
	public MeasuringDeviceController(CreateMeasuringDevice createMeasuringDevice,
			DeleteMeasuringDevice deleteMeasuringDevice, UpdateMeasuringDevice updateMeasuringDevice,
			FindMeasuringDevice findMeasuringDevice) {
		this.createMeasuringDevice = createMeasuringDevice;
		this.deleteMeasuringDevice = deleteMeasuringDevice;
		this.updateMeasuringDevice = updateMeasuringDevice;
		this.findMeasuringDevice = findMeasuringDevice;
	}
	
	@PostMapping
	public ResponseEntity<MeasuringDevice> createMeasuringDevice(@Valid @RequestBody CreateMeasuringDeviceForm form){
		MeasuringDevice device = createMeasuringDevice.create(form.tag(), form.location());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(device.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleteMeasuringDevice(@RequestParam("id") String id) {
		deleteMeasuringDevice.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping
	public ResponseEntity<MeasuringDevice> updateMeasuringDevice(@RequestParam("id") String id, @RequestBody UpdateMeasuringDeviceForm form) {
		MeasuringDevice device = updateMeasuringDevice.update(id, form.tag(), form.location());
		return ResponseEntity.ok().body(device);
	}
	
	@GetMapping
	public ResponseEntity<MeasuringDevice> findMeasuringDeviceById(@RequestParam("id") String id){
		MeasuringDevice device = findMeasuringDevice.findById(id);
		return ResponseEntity.ok().body(device);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<MeasuringDevice>> findAllMeasuringDevicesByUser(){
		List<MeasuringDevice> devices = findMeasuringDevice.findAllByUser();
		return ResponseEntity.ok().body(devices);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<MeasuringDevice>> findAllMeasuringDevices(){
		List<MeasuringDevice> devices = findMeasuringDevice.findAll();
		return ResponseEntity.ok().body(devices);
	}
	
	
	
}
