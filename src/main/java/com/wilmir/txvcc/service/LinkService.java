package com.wilmir.txvcc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wilmir.txvcc.dao.LinkDAO;
import com.wilmir.txvcc.model.Link;

@Service
public class LinkService implements EntityService<Link> {

	@Autowired
	private LinkDAO linkDAO;
	
	@Override
	@Transactional
	public List<Link> findAll() {
		return linkDAO.findAll();
	}

	@Override
	@Transactional
	public Link getEntityById(int id) {
		return linkDAO.getEntityById(id);
	}

	@Override
	@Transactional
	public void save(Link entity) {
		linkDAO.save(entity);
	}

	@Transactional
	public void update(Link entity) {
		linkDAO.update(entity);
	}
	
	
	@Override
	@Transactional
	public void deleteById(int id) {
		linkDAO.deleteById(id);
	}

}
