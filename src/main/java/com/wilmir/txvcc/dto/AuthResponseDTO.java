package com.wilmir.txvcc.dto;

public class AuthResponseDTO {
	private String authenticationToken;
	private String username;
	
	public AuthResponseDTO(String authenticationToken, String username) {
		this.authenticationToken = authenticationToken;
		this.username = username;
	}

	public String getAuthenticationToken() {
		return authenticationToken;
	}
	
	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}
