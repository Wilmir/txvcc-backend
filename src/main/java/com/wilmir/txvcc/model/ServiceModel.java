package com.wilmir.txvcc.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonView;
import com.wilmir.txvcc.view.Views;

@Entity
@Table(name = "service")
public class ServiceModel {
	
	@JsonView(Views.Public.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@JsonView(Views.Public.class)
	@Column(name = "type")
	private String type;
	
	@JsonView(Views.Public.class)
	@Column(name = "capacity")
	private double capacity;
	
	@JsonView(Views.Public.class)
	@CreationTimestamp
	@Column(name = "date_created")
	private Date dateCreated;
	
	@JsonView(Views.Public.class)
	@UpdateTimestamp
	@Column(name = "last_updated")
	private Date lastUpdated;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
			  CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name = "node_id")
	private Node node;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
			  CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name = "homing_node_id")
	private Node homingNode;
	
	public ServiceModel() {
		
	}


	public ServiceModel(String type, double capacity, Date dateCreated, Date lastUpdated, Node node, Node homingNode) {
		this.type = type;
		this.capacity = capacity;
		this.dateCreated = dateCreated;
		this.lastUpdated = lastUpdated;
		this.node = node;
		this.homingNode = homingNode;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public double getCapacity() {
		return capacity;
	}


	public void setCapacity(double capacity) {
		this.capacity = capacity;
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

	public Node getNode() {
		return node;
	}


	public void setNode(Node node) {
		this.node = node;
	}

	public Node getHomingNode() {
		return homingNode;
	}


	public void setHomingNode(Node homingNode) {
		this.homingNode = homingNode;
	}


	@Override
	public String toString() {
		return "ServiceModel [id=" + id + ", type=" + type + ", capacity=" + capacity + ", dateCreated=" + dateCreated
				+ ", lastUpdated=" + lastUpdated + ", node=" + node.toString() + ", homingNode=" + homingNode.toString() + "]";
	}


	
	
	
}
