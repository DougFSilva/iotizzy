package com.dougfsilva.iotnizer.dto;

import java.util.ArrayList;
import java.util.List;

import com.dougfsilva.iotnizer.model.Profile;

import lombok.Getter;

@Getter
public class TokenDto {

	private String token;
	private String type;
	private String user;
	private List<String> profiles = new ArrayList<>();

	public TokenDto(String token, String type, String user, List<Profile> profiles) {
		this.token = token;
		this.type = type;
		this.user = user;
		profiles.forEach(profile -> this.profiles.add(profile.getProfileType().toString()));
	}

}
