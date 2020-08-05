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
import com.wilmir.txvcc.service.LinkService;
import com.wilmir.txvcc.view.Views;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/links")
public class LinkController implements EntityController<Link> {
	
	@Autowired
	private LinkService linkService;
	
	@Override
	@JsonView(Views.Public.class)
	@GetMapping("")
	public List<Link> findAll(){
		return linkService.findAll();
	}
	
	@Override
	@JsonView(Views.Public.class)
	@GetMapping("/{id}")
	public Link getEntityById(@PathVariable("id") int id) {
		return linkService.getEntityById(id);
	}

	@Override
	@JsonView(Views.Public.class)
	@PostMapping("")
	public Link save(@RequestBody Link entity) {
		System.out.println(entity);
		entity.setId(0);
		
		linkService.save(entity);
		
		return entity;
	}

	@Override
	@JsonView(Views.Public.class)
	@PutMapping("")
	public Link updateEntity(@RequestBody Link entity) {
		linkService.update(entity);
		
		return entity;
	}

	@Override
	@JsonView(Views.Public.class)
	@DeleteMapping("/{id}")
	public void deleteEntity(@PathVariable("id") int id) {
		Link entity = linkService.getEntityById(id);
				
		if(entity == null) {
			throw new RuntimeException("Entity id not found: " + id);
		}
		
		linkService.deleteById(id);
		
	}
}
