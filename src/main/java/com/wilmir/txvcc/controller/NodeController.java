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
import com.wilmir.txvcc.model.Node;
import com.wilmir.txvcc.service.NodeService;
import com.wilmir.txvcc.view.Views;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/nodes")
public class NodeController implements EntityController<Node> {
	
	@Autowired
	private NodeService nodeService;
	
	@Override
	@JsonView(Views.Public.class)
	@GetMapping("")
	public List<Node> findAll(){
		return nodeService.findAll();
	}
	
	@Override
	@JsonView(Views.Public.class)
	@GetMapping("/{id}")
	public Node getEntityById(@PathVariable("id") int id) {
		return nodeService.getEntityById(id);
	}

	@Override
	@JsonView(Views.Public.class)
	@PostMapping("")
	public Node save(@RequestBody Node entity) {
		System.out.println(entity);
		entity.setId(0);
		
		nodeService.save(entity);
		
		return entity;
	}

	@Override
	@JsonView(Views.Public.class)
	@PutMapping("")
	public Node updateEntity(@RequestBody Node entity) {
		nodeService.update(entity);
		
		return entity;
	}

	@Override
	@JsonView(Views.Public.class)
	@DeleteMapping("/{id}")
	public void deleteEntity(@PathVariable("id") int id) {
		Node entity = nodeService.getEntityById(id);
				
		if(entity == null) {
			throw new RuntimeException("Entity id not found: " + id);
		}
		
		nodeService.deleteById(id);
		
	}
	
	
}
