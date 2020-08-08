package com.wilmir.txvcc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wilmir.txvcc.dto.LoginDTO;
import com.wilmir.txvcc.dto.RegistrationDTO;
import com.wilmir.txvcc.model.User;
import com.wilmir.txvcc.security.JWTProvider;

@Service
public class AuthService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTProvider jwtProvider;
	
	public void signup(RegistrationDTO userDTO) {
		User user = new User();
		
		user.setName(userDTO.getName());
		user.setUsername(userDTO.getUsername());
		user.setEmail(userDTO.getEmail());
		user.setPassword(encodePassword(userDTO.getPassword()));
		
		userService.save(user);
	}

	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}


	public String login(LoginDTO loginDTO) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return jwtProvider.generateToken(authentication);
	}

    public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }
	
	
	
}
