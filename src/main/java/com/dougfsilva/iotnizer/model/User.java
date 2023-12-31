package com.dougfsilva.iotnizer.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class User implements UserDetails{

    private static final long serialVersionUID = 1L;
	private String id;
    private Email email;
    private String name;
    private String password;
    private List<Profile> profiles;
    private String clientMqttPassword;
    private Boolean blocked;
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return profiles;
	}
	@Override
	public String getUsername() {
		return email.getAddress();
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}

}
