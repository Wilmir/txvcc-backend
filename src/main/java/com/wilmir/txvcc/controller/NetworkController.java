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
import com.wilmir.txvcc.model.Link;
import com.wilmir.txvcc.model.Network;
import com.wilmir.txvcc.model.Node;
import com.wilmir.txvcc.model.ServiceModel;
import com.wilmir.txvcc.service.NetworkService;
import com.wilmir.txvcc.view.Views;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/networks")
public class NetworkController implements EntityController<Network> {
	
	@Autowired
	private NetworkService networkService;
	
	@Override
	@JsonView(Views.Public.class)
	@GetMapping("")
	public List<Network> findAll(){
		return networkService.findAll();
	}
	
	@Override
	@JsonView(Views.Public.class)
	@GetMapping("/{id}")
	public Network getEntityById(@PathVariable("id") int id) {
		return networkService.getEntityById(id);
	}

	@Override
	@JsonView(Views.Public.class)
	@PostMapping("")
	public Network save(@RequestBody Network entity) {
		System.out.println(entity);
		entity.setId(0);
		
		networkService.save(entity);
		
		return entity;
	}

	@Override
	@JsonView(Views.Public.class)
	@PutMapping("")
	public Network updateEntity(@RequestBody Network entity) {
		networkService.update(entity);
		
		return entity;
	}

	@Override
	@JsonView(Views.Public.class)
	@DeleteMapping("/{id}")
	public void deleteEntity(@PathVariable("id") int id) {
		Network entity = networkService.getEntityById(id);
				
		if(entity == null) {
			throw new RuntimeException("Entity id not found: " + id);
		}
		
		networkService.deleteById(id);
		
	}
	
	@JsonView(Views.Public.class)
	@PostMapping("/{id}/nodes")
	public List<Node> addNodes(@PathVariable("id") int id,@RequestBody List<Node> nodes) {
		return networkService.addNodes(id, nodes);
	}
	
	@JsonView(Views.Public.class)
	@PostMapping("/{id}/links")
	public List<Link> addLinks(@PathVariable("id") int id,@RequestBody List<Link> links) {
		return networkService.addLinks(id, links);
	}
	
	@JsonView(Views.Public.class)
	@PostMapping("/{id}/services")
	public List<ServiceModel> addServices(@PathVariable("id") int id,@RequestBody List<ServiceModel> services) {
		return networkService.addServices(id, services);
	}
	
	@JsonView(Views.Public.class)
	@DeleteMapping("/{id}/nodes")
	public void deleteNodes(@PathVariable("id") int id) {
		System.out.println("Nodes deletions request");
		networkService.deleteNodes(id);
		System.out.println("Nodes deletions completed");

	}
	
	@JsonView(Views.Public.class)
	@DeleteMapping("/{id}/links")
	public void deleteLinks(@PathVariable("id") int id) {
		System.out.println("Links deletions request");
		networkService.deleteLinks(id);
		System.out.println("Links deletions completed");
	}
	
	@JsonView(Views.Public.class)
	@DeleteMapping("/{id}/services")
	public void deleteServices(@PathVariable("id") int id) {
		System.out.println("Services deletions request");
		networkService.deleteServices(id);
		System.out.println("Services deletions completed");

	}
	
	
	
	
}
