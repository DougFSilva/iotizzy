package com.dougfsilva.iotizzy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dougfsilva.iotizzy.service.clientmqtt.GetClientMqtt;
import com.dougfsilva.iotizzy.service.clientmqtt.GetRoleMqtt;
import com.dougfsilva.iotizzy.service.clientmqtt.ListClientMqtt;

@RestController
@RequestMapping("/mqtt")
public class MqttController {

	private final GetClientMqtt getClientMqtt;
	
	private final ListClientMqtt listClientMqtt;
	
	private final GetRoleMqtt getRoleMqtt;
	
	public MqttController(GetClientMqtt getClientMqtt, ListClientMqtt listClientMqtt, GetRoleMqtt getRoleMqtt) {
		this.getClientMqtt = getClientMqtt;
		this.listClientMqtt = listClientMqtt;
		this.getRoleMqtt = getRoleMqtt;
	}

	@GetMapping("/client")
	public ResponseEntity<String> getClientMqtt(@RequestParam("id") String id){
		String message = getClientMqtt.get(id);
		return ResponseEntity.ok().body(message);
	}
	
	@GetMapping("/clients")
	public ResponseEntity<String> getClientMqtt(){
		String message = listClientMqtt.list();
		return ResponseEntity.ok().body(message);
	}
	
	@GetMapping("/role")
	public ResponseEntity<String> getRoleMqtt(@RequestParam("id") String id){
		String message = getRoleMqtt.get(id);
		return ResponseEntity.ok().body(message);
	}
	
}
