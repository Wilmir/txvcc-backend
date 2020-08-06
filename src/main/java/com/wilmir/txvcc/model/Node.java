package com.wilmir.txvcc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonView;
import com.wilmir.txvcc.view.Views;


@Entity
@Table(name = "node")
public class Node{

	@JsonView(Views.Public.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@JsonView(Views.Public.class)
	@Column(name = "name")
	private String name;
	
	@JsonView(Views.Public.class)
	@Column(name = "is_homing")
	private boolean isHoming;
	
	@JsonView(Views.Public.class)
	@CreationTimestamp
	@Column(name = "date_created")
	private Date dateCreated;
	
	@JsonView(Views.Public.class)
	@UpdateTimestamp
	@Column(name = "last_updated")
	private Date lastUpdated;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
						  CascadeType.DETACH, CascadeType.REFRESH
						  },
			   fetch = FetchType.LAZY)
	@JoinColumn(name = "network_id")
	private Network network;

	@JsonView(Views.Public.class)
	@OneToMany(mappedBy="node", cascade = CascadeType.ALL)
	private List<ServiceModel> services;
	
	@OneToMany(mappedBy="homingNode", cascade = {CascadeType.ALL})
	private List<ServiceModel> homingServices;
	
	@OneToMany(mappedBy="target", cascade = {CascadeType.ALL})
	public List<Link> incomingLinks;
	
	@OneToMany(mappedBy="source", cascade = {CascadeType.ALL})
	public List<Link> outgoingLinks;

	@Transient
	@JsonView(Views.Public.class)
	private int linkCount;
	
	@Transient
	private List<Node> neighbors;

	@Transient
	private Node parentNode;
	
	@Transient
	private boolean visited = false;
	
	public Node() {
		
	}

	public Node(String name, Date dateCreated, Date lastUpdated, Network network, List<ServiceModel> services,
			List<ServiceModel> homingServices, List<Link> incomingLinks, List<Link> outgoingLinks) {
		this.name = name;
		this.dateCreated = dateCreated;
		this.lastUpdated = lastUpdated;
		this.network = network;
		this.services = services;
		this.homingServices = homingServices;
		this.incomingLinks = incomingLinks;
		this.outgoingLinks = outgoingLinks;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	
	
	public boolean isHoming() {
		return isHoming;
	}


	public void setHoming(boolean isHoming) {
		this.isHoming = isHoming;
	}


	public Date getDateCreated() {
		return dateCreated;
	}


	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}


	public Date getLastUpdated() {
		return lastUpdated;
	}


	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Network getNetwork() {
		return network;
	}


	public void setNetwork(Network network) {
		this.network = network;
	}

	public List<ServiceModel> getServices() {
		return services;
	}

	public void setServices(List<ServiceModel> services) {
		this.services = services;
	}
	
	public ServiceModel addServiceModeltoNode(ServiceModel service) {
		if(services == null) {
			services = new ArrayList<>();
		}
		
		services.add(service);
		service.setNode(this);
		
		return service;
	}
	
	public ServiceModel addServiceModeltoHomingNode(ServiceModel service) {
		if(homingServices == null) {
			homingServices = new ArrayList<>();
		}
		
		homingServices.add(service);
		service.setHomingNode(this);
		
		return service;
	}
	


	public List<ServiceModel> getHomingServices() {
		return homingServices;
	}

	public void setHomingServices(List<ServiceModel> homingServices) {
		this.homingServices = homingServices;
	}

	public List<Link> getIncomingLinks() {
		return incomingLinks;
	}


	public void setIncomingLinks(List<Link> incomingLinks) {
		this.incomingLinks = incomingLinks;
	}

	public List<Link> getOutgoingLinks() {
		return outgoingLinks;
	}


	public void setOutgoingLinks(List<Link> outgoingLinks) {
		this.outgoingLinks = outgoingLinks;
	}
	
	
	public int getLinkCount() {
		return linkCount;
	}
	

	public List<Node> getNeighbors() {
		return neighbors;
	}


	public void setNeighbors(List<Node> neighbors) {
		this.neighbors = neighbors;
	}

	
	public void addNeighbor(Node node) {
		if(this.neighbors == null) {
			this.neighbors = new ArrayList<>();
		}
		
		if(node.neighbors == null) {
			node.neighbors = new ArrayList<>();
		}
		
		this.neighbors.add(node);
		
		node.neighbors.add(this);
	}

	
	public Node getParentNode() {
		return parentNode;
	}


	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}

	public boolean isVisited() {
		return visited;
	}


	public void setVisited(boolean visited) {
		this.visited = visited;
	}


	//helper methods
	public void addIncomingLink(Link link) {
		if(this.incomingLinks == null) {
			this.incomingLinks = new ArrayList<>();
		}
		
		link.setTarget(this);
		this.incomingLinks.add(link);
	}
	
	public void addOutgoingLink(Link link) {
		if(this.outgoingLinks == null) {
			outgoingLinks = new ArrayList<>();
		}
		
		link.setSource(this);
		this.outgoingLinks.add(link);
	}
	
	
	public void removeIncomingLink(int id) {
		for(Link link:incomingLinks) {
			if(link.getId() == id) {
				incomingLinks.remove(link);
				return;
			}
		}	
	}
	
	
	public void removeOutgoignLink(int id) {
		for(Link link:outgoingLinks) {
			if(link.getId() == id) {
				outgoingLinks.remove(link);
				return;
			}
		}	
	}
	
	
	@PostLoad
	private void postLoad() {
		this.linkCount = this.getOutgoingLinks().size() + this.getIncomingLinks().size();
	}


	@Override
	public String toString() {
		return "Node [id=" + id + ", name=" + name+ "]";
	}	
	
}
