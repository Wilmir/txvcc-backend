package com.wilmir.txvcc.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.wilmir.txvcc.model.Link;
import com.wilmir.txvcc.model.Node;
import com.wilmir.txvcc.model.User;
import com.wilmir.txvcc.view.Views;


public class NetworkDTO {
	
	@JsonView(Views.Public.class)
	private int id;
	
	@JsonView(Views.Public.class)
	private String networkName;

	@JsonView(Views.Public.class)
	private String description;
	
	@JsonView(Views.Public.class)
	private Date dateCreated;
	
	@JsonView(Views.Public.class)
	private Date lastUpdated;
	
	@JsonView(Views.Public.class)
	private List<Node> nodes;
	
	@JsonView(Views.Public.class)
	private List<Link> links;
	
	private User user;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNetworkName() {
		return networkName;
	}
	
	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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
	
	public List<Node> getNodes() {
		return nodes;
	}
	
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	public List<Link> getLinks() {
		return links;
	}
	
	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
