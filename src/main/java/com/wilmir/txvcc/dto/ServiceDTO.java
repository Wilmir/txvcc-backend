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

	public int getNode_id() {
		return node_id;
	}

	public void setNode_id(int node_id) {
		this.node_id = node_id;
	}

	public int getHoming_node_id() {
		return homing_node_id;
	}

	public void setHoming_node_id(int homing_node_id) {
		this.homing_node_id = homing_node_id;
	}

	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
	
	
	
}
