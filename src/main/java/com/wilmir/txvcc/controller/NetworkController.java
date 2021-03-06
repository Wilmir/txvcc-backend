package com.wilmir.txvcc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.wilmir.txvcc.dto.LinkDTO;
import com.wilmir.txvcc.dto.NetworkDTO;
import com.wilmir.txvcc.dto.NodeDTO;
import com.wilmir.txvcc.dto.ServiceDTO;
import com.wilmir.txvcc.model.Network;
import com.wilmir.txvcc.model.ServiceModel;
import com.wilmir.txvcc.model.User;
import com.wilmir.txvcc.service.NetworkService;
import com.wilmir.txvcc.service.UserService;
import com.wilmir.txvcc.view.Views;

@RestController
@RequestMapping("/api")
public class NetworkController{

	@Autowired
	private NetworkService networkService;

	@Autowired
	private UserService userService;
	
	@JsonView(Views.Public.class)
	@GetMapping("/networks")
	public ResponseEntity<List> findAll(){
		List list = networkService.findAll();
		
		return new ResponseEntity<List>(list, HttpStatus.ACCEPTED);
	}
	
	
	@JsonView(Views.Public.class)
	@GetMapping("users/{username}/networks")
	public ResponseEntity<List> findAllByUserName(@PathVariable("username") String username) {
		List list = networkService.findAllByUserName(username);
				
		return new ResponseEntity<List>(list, HttpStatus.ACCEPTED);

	}
	
	
	@JsonView(Views.Public.class)
	@GetMapping("/networks/{id}")
	public ResponseEntity<NetworkDTO> getEntityById(@PathVariable("id") int id) {
		NetworkDTO networkDTO =  networkService.getEntityById(id);
		
		return new ResponseEntity<NetworkDTO>(networkDTO, HttpStatus.ACCEPTED);		
	}
	
	
	@JsonView(Views.Public.class)
	@PostMapping("/networks")
	public ResponseEntity save(@RequestBody NetworkDTO networkDTO) {
		networkDTO.setId(0);
		
		networkService.save(networkDTO);
		
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}

	
	@JsonView(Views.Public.class)
	@PutMapping("/networks")
	public ResponseEntity updateEntity(@RequestBody NetworkDTO networkDTO) {
		networkService.update(networkDTO);
		
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}

	
	@JsonView(Views.Public.class)
	@DeleteMapping("/networks/{id}")
	public ResponseEntity deleteEntity(@PathVariable("id") int id) {
		NetworkDTO networkDTO = networkService.getEntityById(id);
				
		if(networkDTO == null) {
			throw new RuntimeException("Entity id not found: " + id);
		}
		
		networkService.deleteById(id);
		
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}
	
	@JsonView(Views.Public.class)
	@PostMapping("/networks/{id}/nodes")
	public ResponseEntity addNodes(@PathVariable("id") int id,@RequestBody List<NodeDTO> nodes) {
		networkService.addNodes(id, nodes);
		
		return new ResponseEntity(HttpStatus.ACCEPTED);

	}
	
	
	@JsonView(Views.Public.class)
	@PostMapping("/networks/{id}/links")
	public ResponseEntity addLinks(@PathVariable("id") int id,@RequestBody List<LinkDTO> links) {
		networkService.addLinks(id, links);
		
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}
	
	
	@JsonView(Views.Public.class)
	@PostMapping("/networks/{id}/services")
	public ResponseEntity addServices(@PathVariable("id") int id,@RequestBody List<ServiceDTO> servicesDTO) {
		networkService.addServices(id, servicesDTO);
	
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}
	
	
	@JsonView(Views.Public.class)
	@DeleteMapping("/networks/{id}/nodes")
	public ResponseEntity deleteNodes(@PathVariable("id") int id) {
		networkService.deleteNodes(id);
		
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}
	
	
	@JsonView(Views.Public.class)
	@DeleteMapping("/networks/{id}/links")
	public ResponseEntity deleteLinks(@PathVariable("id") int id) {
		networkService.deleteLinks(id);
		
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}
	
	
	@JsonView(Views.Public.class)
	@DeleteMapping("/networks/{id}/services")
	public ResponseEntity deleteServices(@PathVariable("id") int id) {
		networkService.deleteServices(id);
		
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}
	
}
