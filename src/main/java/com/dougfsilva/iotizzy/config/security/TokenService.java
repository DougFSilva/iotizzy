package com.dougfsilva.iotizzy.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.dougfsilva.iotizzy.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${jwt.expiration}")
	private long expiration;

	@Value("${jwt.secret}")
	private String secret;

	public String generate(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		return Jwts.builder().setIssuer("API IotNizer")
				.setSubject(user.getId()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + (expiration * 1000 * 60)))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
	}

	public boolean valid(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret.getBytes()).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public String getUserId(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret.getBytes()).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
	
}