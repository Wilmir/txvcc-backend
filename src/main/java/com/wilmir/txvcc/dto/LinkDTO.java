package com.wilmir.txvcc.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class LinkDTO {
	private int source_id;
	private int target_id;
	private double capacity;
	private String type;
	
	public int getSourceId() {
		return source_id;
	}
	
	public void setSourceId(int sourceId) {
		this.source_id = sourceId;
	}
	
	public int getTargetId() {
		return target_id;
	}
	
	public void setTargetId(int targetId) {
		this.target_id = targetId;
	}
	
	public double getCapacity() {
		return capacity;
	}
	
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
