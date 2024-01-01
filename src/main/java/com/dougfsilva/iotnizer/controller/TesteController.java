package com.dougfsilva.iotnizer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TesteController {

	
	@GetMapping("/{username}")
	public ResponseEntity<Void> teste(@PathVariable String username){
		//service.publish(new User());
		return ResponseEntity.ok().build();
	}
	
}
