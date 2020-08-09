package com.wilmir.txvcc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wilmir.txvcc.dao.EntityDAO;
import com.wilmir.txvcc.dao.LinkDAO;
import com.wilmir.txvcc.dao.NetworkDAO;
import com.wilmir.txvcc.dao.NodeDAO;
import com.wilmir.txvcc.dao.ServiceDAO;
import com.wilmir.txvcc.dto.LinkDTO;
import com.wilmir.txvcc.dto.NetworkDTO;
import com.wilmir.txvcc.dto.NodeDTO;
import com.wilmir.txvcc.dto.ServiceDTO;
import com.wilmir.txvcc.model.Link;
import com.wilmir.txvcc.model.Network;
import com.wilmir.txvcc.model.Node;
import com.wilmir.txvcc.model.ServiceModel;
import com.wilmir.txvcc.model.User;

@Service
public class NetworkService {

	@Autowired
	private AuthService authService;

	@Autowired
	private NetworkDAO networkDAO;

	@Autowired
	private NodeDAO nodeDAO;

	@Autowired
	private LinkDAO linkDAO;

	@Autowired
	private ServiceDAO serviceDAO;

	@Autowired
	private UserService userService;

	@Transactional
	public List findAll() {
		return networkDAO.findAll();
	}

	@Transactional
	public NetworkDTO getEntityById(int id) {
		Network network = networkDAO.getEntityById(id);
		
		NetworkDTO networkDTO = null;
		if(networkOwnerIsLoggedIn(network)) {
			networkDTO= mapFromNetworktoDto(network);
		}
			
		return networkDTO;
	}

	@Transactional
	public void save(NetworkDTO networkDTO) {
		Network network = mapFromDtoToNetwork(networkDTO);

		networkDAO.save(network);
	}

	@Transactional
	public void update(NetworkDTO networkDTO) {
		Network network = mapFromDtoToNetwork(networkDTO);

		networkDAO.update(network);
	}

	@Transactional
	public void deleteById(int id) {
		Network network = networkDAO.getEntityById(id);

		if(networkOwnerIsLoggedIn(network)) {
			networkDAO.deleteById(id);
		}
	}

	@Transactional
	public void addNodes(int id, List<NodeDTO> nodesDTO){
		Network network = networkDAO.getEntityById(id);

		if(networkOwnerIsLoggedIn(network)) {
			for(NodeDTO nodeDTO:nodesDTO) {
				Node node = mapFromDtoToNode(nodeDTO);
				network.addNode(node);
			}

			networkDAO.save(network);
		}

	}

	@Transactional
	public void addLinks(int id, List<LinkDTO> links) {
		Network network = networkDAO.getEntityById(id);

		if(networkOwnerIsLoggedIn(network)) {
			for(LinkDTO linkDTO:links) {
				Link link = mapFromDtoToLink(linkDTO);
				link.setNetwork(network);
				linkDAO.save(link);
			}

			networkDAO.save(network);
		}
	}

	@Transactional
	public void addServices(int id, List<ServiceDTO> services) {
		Network network = networkDAO.getEntityById(id);

		if(networkOwnerIsLoggedIn(network)) {
			for(ServiceDTO serviceDTO : services) {
				ServiceModel service = mapFromDtoToService(serviceDTO);
				serviceDAO.save(service);
			}
		}
	}

	
	@Transactional
	public void deleteNodes(int id){
		Network network = networkDAO.getEntityById(id);

		if(networkOwnerIsLoggedIn(network)) {
			for(Node node:network.getNodes()) {
				nodeDAO.deleteById(node.getId());
			}
		}
	}

	@Transactional
	public void deleteLinks(int id) {
		Network network = networkDAO.getEntityById(id);
		
		if(networkOwnerIsLoggedIn(network)) {
			for(Link link:network.getLinks()) {
				linkDAO.deleteById(link.getId());
			}	
		}
	}

	
	@Transactional
	public void deleteServices(int id) {
		Network network = networkDAO.getEntityById(id);

		if(networkOwnerIsLoggedIn(network)) {
			for(Node node : network.getNodes()) {
				serviceDAO.deleteByNodeId(node.getId());
			}
		}
	}


	private boolean networkOwnerIsLoggedIn(Network network) {		
		org.springframework.security.core.userdetails.User loggedInUser = authService.getCurrentUser().orElseThrow(() -> 
		new IllegalArgumentException("No user logged in"));

		return network.getUser().getUsername().equals(loggedInUser.getUsername());
	}
	
	
	
	private NetworkDTO mapFromNetworktoDto(Network network) {
		NetworkDTO networkDTO = new NetworkDTO();
		networkDTO.setId(network.getId());
		networkDTO.setNetworkName(network.getNetworkName());
		networkDTO.setDescription(network.getDescription());
		networkDTO.setNodes(network.getNodes());
		networkDTO.setLinks(network.getLinks());
		networkDTO.setDateCreated(network.getDateCreated());
		networkDTO.setLastUpdated(network.getLastUpdated());

		return networkDTO;
	}

	private Network mapFromDtoToNetwork(NetworkDTO networkDTO) {
		Network network = new Network();
		network.setId(networkDTO.getId());
		network.setNetworkName(networkDTO.getNetworkName());
		network.setDescription(networkDTO.getDescription());

		org.springframework.security.core.userdetails.User loggedInUser = authService.getCurrentUser().orElseThrow(() -> 
		new IllegalArgumentException("No user logged in"));

		User user = userService.getEntityByUserName(loggedInUser.getUsername());
		network.setUser(user);

		return network;
	}

	private Node mapFromDtoToNode(NodeDTO nodeDTO) {
		Node node = new Node();
		node.setId(nodeDTO.getId());
		node.setName(nodeDTO.getName());
		node.setHoming(nodeDTO.isHoming());

		return node;
	}

	private Link mapFromDtoToLink(LinkDTO linkDTO) {
		Link link = new Link();
		link.setCapacity(linkDTO.getCapacity());
		link.setType(linkDTO.getType());		
		Node source = nodeDAO.getEntityById(linkDTO.getSource_id());
		Node target = nodeDAO.getEntityById(linkDTO.getTarget_id());
		
		link.setSource(source);
		link.setTarget(target);
		
		return link;
	}

	private ServiceModel mapFromDtoToService(ServiceDTO serviceDTO) {
		ServiceModel service = new ServiceModel();
		service.setType(serviceDTO.getType());
		service.setCapacity(serviceDTO.getCapacity());

		Node node = nodeDAO.getEntityById(serviceDTO.getNode_id());
		Node homingNode = nodeDAO.getEntityById(serviceDTO.getHoming_node_id());

		service.setNode(node);
		service.setHomingNode(homingNode);
		
		return service;
	}
		

}
