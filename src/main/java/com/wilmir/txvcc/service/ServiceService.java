package com.wilmir.txvcc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wilmir.txvcc.dao.ServiceDAO;
import com.wilmir.txvcc.model.ServiceModel;

@Service
public class ServiceService implements EntityService<ServiceModel> {

	@Autowired
	private ServiceDAO serviceDAO;
	
	@Override
	@Transactional
	public List<ServiceModel> findAll() {
		return serviceDAO.findAll();
	}

	@Override
	@Transactional
	public ServiceModel getEntityById(int id) {
		return serviceDAO.getEntityById(id);
	}

	@Override
	@Transactional	
	public void save(ServiceModel entity) {
		serviceDAO.save(entity);
	}

	@Transactional
	public void update(ServiceModel entity) {
		 serviceDAO.update(entity);
	}
	
	@Override
	@Transactional
	public void deleteById(int id) {
		serviceDAO.deleteById(id);
	}

}
