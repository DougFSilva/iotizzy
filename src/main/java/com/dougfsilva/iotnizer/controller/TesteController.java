package com.dougfsilva.iotnizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dougfsilva.iotnizer.service.clientmqtt.GetClientMqtt;

@RestController
@RequestMapping("/test")
public class TesteController {

	@Autowired
	private GetClientMqtt getClientMqtt;
	
	@GetMapping("/mqtt")
	public ResponseEntity<String> teste(@RequestParam("id") String id){
		String message = getClientMqtt.get(id);
		return ResponseEntity.ok().body(message);
	}
	
}
