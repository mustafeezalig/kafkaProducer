package com.javatpoint.config.security;

import java.util.List;

public class LoginResponse {
	
	private String jwtToken;
	public String getJwtToken() {
		return jwtToken;
	}
	public String getUsername() {
		return username;
	}
	public List<String> getRoles() {
		return roles;
	}
	private String username;
	private List<String> roles;
	public LoginResponse(String jwtToken, String username, List<String> roles) {
		super();
		this.jwtToken = jwtToken;
		this.username = username;
		this.roles = roles;
	}
	

}
