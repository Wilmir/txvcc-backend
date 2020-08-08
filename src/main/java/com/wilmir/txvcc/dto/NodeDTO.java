package com.wilmir.txvcc.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wilmir.txvcc.model.Network;
import com.wilmir.txvcc.model.ServiceModel;

@JsonIgnoreProperties(ignoreUnknown=true)
public class NodeDTO {
	private int id;
	private String name;
	private boolean isHoming;
	private Date dateCreated;
	private Date lastUpdated;
	private List<ServiceModel> services;
	private int linkCount;
	private Network network;
	
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
	
	public List<ServiceModel> getServices() {
		return services;
	}
	
	public void setServices(List<ServiceModel> services) {
		this.services = services;
	}
	
	public int getLinkCount() {
		return linkCount;
	}
	
	public void setLinkCount(int linkCount) {
		this.linkCount = linkCount;
	}
	
	public Network getNetwork() {
		return network;
	}
	
	public void setNetwork(Network network) {
		this.network = network;
	}
}
