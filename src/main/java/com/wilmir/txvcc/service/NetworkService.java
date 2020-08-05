package com.wilmir.txvcc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wilmir.txvcc.dao.LinkDAO;
import com.wilmir.txvcc.dao.NetworkDAO;
import com.wilmir.txvcc.dao.ServiceDAO;
import com.wilmir.txvcc.model.Link;
import com.wilmir.txvcc.model.Network;
import com.wilmir.txvcc.model.Node;
import com.wilmir.txvcc.model.ServiceModel;

@Service
public class NetworkService implements EntityService<Network> {

	@Autowired
	private NetworkDAO networkDAO;
	
	@Autowired
	private LinkDAO linkDAO;
	
	@Autowired
	private ServiceDAO serviceDAO;
	
	@Override
	@Transactional
	public List<Network> findAll() {
		return networkDAO.findAll();
	}

	@Override
	@Transactional
	public Network getEntityById(int id) {
		return networkDAO.getEntityById(id);
	}

	@Override
	@Transactional
	public void save(Network entity) {
		networkDAO.save(entity);
	}
	
	
	@Transactional
	public void update(Network entity) {
		networkDAO.update(entity);
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		networkDAO.deleteById(id);
	}
	
	@Transactional
	public List<Node> addNodes(int id, List<Node> nodes){
		Network network = networkDAO.getEntityById(id);
		
		for(Node node:nodes) {
			network.addNode(node);
		}
		
		networkDAO.save(network);
		
		return nodes;
	}

	@Transactional
	public List<Link> addLinks(int id, List<Link> links) {
		Network network = networkDAO.getEntityById(id);
				
		for(Link link:links) {
			link.setNetwork(network);
			linkDAO.save(link);
		}
		
		return links;
	}

	@Transactional
	public List<ServiceModel> addServices(int id, List<ServiceModel> services) {
		for(ServiceModel service : services) {
			serviceDAO.save(service);
		}
		
		return services;
	}

}
