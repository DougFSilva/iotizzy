package com.dougfsilva.iotizzy.dto;

import java.util.List;

import com.dougfsilva.iotizzy.model.Profile;
import com.dougfsilva.iotizzy.model.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {
	
	private String id;
    private String email;
    private String name;
    private List<Profile> profiles;
    private String clientMqttPassword;
    private Boolean blocked;
    
    public UserDto(User user) {
    	this.id = user.getId();
    	this.email = user.getEmail().getAddress();
    	this.name = user.getName();
    	this.profiles = user.getProfiles();
    	this.clientMqttPassword = user.getClientMqttPassword();
    	this.blocked = user.getBlocked();
    }

}
