package com.wilmir.txvcc.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wilmir.txvcc.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getEntityByUserName(username);
        
		if(user == null) {
			throw new UsernameNotFoundException("User no found: 404");
		}else {
	        return new org.springframework.security.core.userdetails.User(user.getUsername(),
	                user.getPassword(),
	                true, true, true, true,
	                getAuthorities("ROLE_USER"));
		}
	}
	
    private Collection<? extends GrantedAuthority> getAuthorities(String role_user) {
        return Collections.singletonList(new SimpleGrantedAuthority(role_user));
    }

}
