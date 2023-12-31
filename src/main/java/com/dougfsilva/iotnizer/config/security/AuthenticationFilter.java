package com.dougfsilva.iotnizer.config.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends OncePerRequestFilter {

	private TokenService tokenService;

	private UserRepository userRepository;

	public AuthenticationFilter(TokenService tokenService, UserRepository userRepository) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = tokenRecover(request);
		if (tokenService.valid(token)) {
			authentication(token);
		}
		filterChain.doFilter(request, response);
	}

	private void authentication(String token) {
		String user_id = tokenService.getUserId(token);
		Optional<User> user = userRepository.findById(user_id);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.get(), null,
				user.get().getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String tokenRecover(HttpServletRequest request) {
		String headerToken = request.getHeader("Authorization");
		if (headerToken == null || headerToken.isEmpty() || !headerToken.startsWith("Bearer ")) {
			String requestParameter = request.getParameter("token");
			if(requestParameter != null && !requestParameter.isEmpty()) {
				return requestParameter;
			}
			return null;
		}
		return headerToken.substring(7, headerToken.length());
	}

}
