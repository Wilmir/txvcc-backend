package com.wilmir.txvcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wilmir.txvcc.dto.AuthResponseDTO;
import com.wilmir.txvcc.dto.LoginDTO;
import com.wilmir.txvcc.dto.RegistrationDTO;
import com.wilmir.txvcc.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity signUp(@RequestBody RegistrationDTO registrtationDTO) {
		authService.signup(registrtationDTO);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public AuthResponseDTO login(@RequestBody LoginDTO loginDTO) {
		return authService.login(loginDTO);
	}
	
}
