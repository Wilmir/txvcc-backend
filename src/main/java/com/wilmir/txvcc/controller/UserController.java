package com.wilmir.txvcc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.wilmir.txvcc.model.Network;
import com.wilmir.txvcc.model.User;
import com.wilmir.txvcc.service.UserService;
import com.wilmir.txvcc.view.Views;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
public class UserController{

	@Autowired
	private UserService userService;

	@JsonView(Views.Public.class)
	@GetMapping("/{id}")
	public User getEntityById(@PathVariable int id) {
		return userService.getEntityById(id);
	}
	
	@JsonView(Views.Public.class)
	@GetMapping("/{username}")
	public User getEntityByUserName(@PathVariable("username") String username) {
		return userService.getEntityByUserName(username);
	}
	
	@JsonView(Views.Public.class)
	@PostMapping("")
	public User save(@RequestBody User entity) {
		 userService.save(entity);
		 
		 return entity;
	}

	@JsonView(Views.Public.class)
	@PutMapping("")
	public User updateEntity(@RequestBody User entity) {
		userService.update(entity);
		
		return entity;
	}

	@JsonView(Views.Public.class)
	@DeleteMapping("/{id}")
	public void deleteEntity(@PathVariable int id) {
		userService.deleteById(id);
	}
	
	@JsonView(Views.Public.class)
	@GetMapping("/{id}/networks")
	public List<Network> findNetworksByUserId(@PathVariable("id") int userId){
		return userService.findByUserId(userId);
	}
	
}
