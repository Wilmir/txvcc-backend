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
import com.wilmir.txvcc.model.ServiceModel;
import com.wilmir.txvcc.service.ServiceService;
import com.wilmir.txvcc.view.Views;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/services")
public class ServiceModelController implements EntityController<ServiceModel> {
	
	@Autowired
	private ServiceService serviceService;
	
	@Override
	@JsonView(Views.Public.class)
	@GetMapping("")
	public List<ServiceModel> findAll(){
		return serviceService.findAll();
	}
	
	@Override
	@JsonView(Views.Public.class)
	@GetMapping("/{id}")
	public ServiceModel getEntityById(@PathVariable("id") int id) {
		return serviceService.getEntityById(id);
	}

	@Override
	@JsonView(Views.Public.class)
	@PostMapping("")
	public ServiceModel save(@RequestBody ServiceModel entity) {
		System.out.println(entity);
		entity.setId(0);

		serviceService.save(entity);
		
		return entity;
	}

	@Override
	@JsonView(Views.Public.class)
	@PutMapping("")
	public ServiceModel updateEntity(@RequestBody ServiceModel entity) {
		serviceService.update(entity);
		
		return entity;
	}

	@Override
	@JsonView(Views.Public.class)
	@DeleteMapping("/{id}")
	public void deleteEntity(@PathVariable("id") int id) {
		ServiceModel entity = serviceService.getEntityById(id);
				
		if(entity == null) {
			throw new RuntimeException("Entity id not found: " + id);
		}
		
		serviceService.deleteById(id);
		
	}
}
