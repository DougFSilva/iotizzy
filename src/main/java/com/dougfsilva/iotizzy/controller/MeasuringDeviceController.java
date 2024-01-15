package com.dougfsilva.iotizzy.controller;

import java.net.URI;
import java.time.LocalDateTime;
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

import com.dougfsilva.iotizzy.dto.AddMeasuredValueForm;
import com.dougfsilva.iotizzy.dto.CreateMeasuringDeviceForm;
import com.dougfsilva.iotizzy.dto.FilterMeasuringDeviceForm;
import com.dougfsilva.iotizzy.dto.UpdateMeasuringDeviceForm;
import com.dougfsilva.iotizzy.model.MeasuringDevice;
import com.dougfsilva.iotizzy.service.measuringDevice.AddValueFromMeasuringDevice;
import com.dougfsilva.iotizzy.service.measuringDevice.CreateMeasuringDevice;
import com.dougfsilva.iotizzy.service.measuringDevice.DeleteMeasuringDevice;
import com.dougfsilva.iotizzy.service.measuringDevice.FindMeasuringDevice;
import com.dougfsilva.iotizzy.service.measuringDevice.RemoveValuesFromMeasuringDevice;
import com.dougfsilva.iotizzy.service.measuringDevice.UpdateMeasuringDevice;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/measuring-device")
public class MeasuringDeviceController {

	private final CreateMeasuringDevice createMeasuringDevice;
	
	private final DeleteMeasuringDevice deleteMeasuringDevice;
	
	private final UpdateMeasuringDevice updateMeasuringDevice;
	
	private final FindMeasuringDevice findMeasuringDevice;
	
	private final AddValueFromMeasuringDevice addValueInMeasuringDevice;
	
	private final RemoveValuesFromMeasuringDevice removeValuesOfMeasuringDevice;
	
	public MeasuringDeviceController(CreateMeasuringDevice createMeasuringDevice,
			DeleteMeasuringDevice deleteMeasuringDevice, UpdateMeasuringDevice updateMeasuringDevice,
			FindMeasuringDevice findMeasuringDevice, AddValueFromMeasuringDevice addValueInMeasuringDevice, 
			RemoveValuesFromMeasuringDevice removeValuesOfMeasuringDevice) {
		this.createMeasuringDevice = createMeasuringDevice;
		this.deleteMeasuringDevice = deleteMeasuringDevice;
		this.updateMeasuringDevice = updateMeasuringDevice;
		this.findMeasuringDevice = findMeasuringDevice;
		this.addValueInMeasuringDevice = addValueInMeasuringDevice;
		this.removeValuesOfMeasuringDevice = removeValuesOfMeasuringDevice;
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
	
	@PostMapping("/add-value")
	public ResponseEntity<Void> addValuesFromMeasuringDevice(@RequestBody AddMeasuredValueForm form) {
		addValueInMeasuringDevice.add(form.device_id(), form.values(), form.timestamp());
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/remove-value")
	public ResponseEntity<Void> removeValueFromMeasuringDevice(@RequestParam("device_id") String device_id, @RequestParam("value_id") String value_id) {
		removeValuesOfMeasuringDevice.removeById(device_id, value_id);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/remove-value/timestamp")
	public ResponseEntity<Void> removeValueFromMeasuringDeviceByTimestamp(
			@RequestParam("id") String id, @RequestParam("from") LocalDateTime inicialTimestamp, @RequestParam("to") LocalDateTime finalTimestamp){
		removeValuesOfMeasuringDevice.removeByTimestamp(id, inicialTimestamp, finalTimestamp);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/all")
	public ResponseEntity<Void> removeAllValueFromMeasuringDevice(@RequestParam("id") String id){
		removeValuesOfMeasuringDevice.removeAll(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity<MeasuringDevice> findMeasuringDeviceById(@RequestParam("id") String id){
		MeasuringDevice device = findMeasuringDevice.findById(id);
		return ResponseEntity.ok().body(device);
	}
	
	@GetMapping("/timestamp")
	public ResponseEntity<MeasuringDevice> findMeasuringDeviceByIdAndFilterValuesByTimestamp(
			@Valid @RequestBody FilterMeasuringDeviceForm form){
		MeasuringDevice device = findMeasuringDevice.findByIdAndfilterValuesByTimestamp(form.id(), form.initialTimestamp(), form.finalTimestamp(), form.limit());
		return ResponseEntity.ok().body(device);
	}
	
	@GetMapping("/timestamp-value")
	public ResponseEntity<MeasuringDevice> findMeasuringDeviceByIdAndFilterValuesByTimestampAndValue(
			@Valid @RequestBody FilterMeasuringDeviceForm form){
		MeasuringDevice device = findMeasuringDevice.findByIdAndfilterValuesByTimestampAndValue(
				form.id(), form.initialTimestamp(), form.finalTimestamp(), form.initialValue(), form.finalValue(), form.limit());
		return ResponseEntity.ok().body(device);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<MeasuringDevice>> findAllMeasuringDevicesByUser(){
		List<MeasuringDevice> devices = findMeasuringDevice.findAll();
		return ResponseEntity.ok().body(devices);
	}
	
	
}
