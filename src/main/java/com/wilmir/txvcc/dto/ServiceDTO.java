package com.wilmir.txvcc.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ServiceDTO {
	private String type;
	private int node_id;
	private int homing_node_id;
	private double capacity;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public int getNodeId() {
		return node_id;
	}
	
	public void setNodeId(int nodeId) {
		this.node_id = nodeId;
	}
	
	public int getHomingNodeId() {
		return homing_node_id;
	}
	
	public void setHomingNodeId(int homingNodeId) {
		this.homing_node_id = homingNodeId;
	}
	
	public double getCapacity() {
		return capacity;
	}
	
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

}
