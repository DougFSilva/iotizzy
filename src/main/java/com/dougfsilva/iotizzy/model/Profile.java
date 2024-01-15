package com.dougfsilva.iotizzy.model;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "profileType")
public class Profile implements GrantedAuthority{

    private static final long serialVersionUID = 1L;
	private ProfileType profileType;

	@Override
	public String getAuthority() {
		return profileType.getDescription();
	}
}
