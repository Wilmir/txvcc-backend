package com.wilmir.txvcc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wilmir.txvcc.dao.NodeDAO;
import com.wilmir.txvcc.model.Node;

@Service
public class NodeService implements EntityService<Node> {

	@Autowired
	private NodeDAO nodeDAO;
	
	@Override
	@Transactional
	public List<Node> findAll() {
		return nodeDAO.findAll();
	}

	@Override
	@Transactional
	public Node getEntityById(int id) {
		return nodeDAO.getEntityById(id);
	}

	@Override
	@Transactional
	public void save(Node entity) {
		nodeDAO.save(entity);
	}

	@Transactional
	public void update(Node entity) {
		System.out.println("We are in the NodeService");
		
		nodeDAO.update(entity);
	}
	
	
	@Override
	@Transactional
	public void deleteById(int id) {
		nodeDAO.deleteById(id);
	}

}
