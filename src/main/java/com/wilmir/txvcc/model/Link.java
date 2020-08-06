package com.wilmir.txvcc.model;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonView;
import com.wilmir.txvcc.view.Views;


@Entity
@Table(name = "link")
public class Link {

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

	@JsonView(Views.Public.class)
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name = "source_id")
	private Node source;

	@JsonView(Views.Public.class)
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name = "target_id")
	private Node target;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.DETACH, CascadeType.REFRESH},
			fetch = FetchType.LAZY)
	@JoinColumn(name = "network_id")
	private Network network;
	
	
	@JsonView(Views.Public.class)
	@Transient
	private double utilization;

	@JsonView(Views.Public.class)
	@Transient
	private List<Node> servicedNodes;
	
	
	public Link() {

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

	public Node getSource() {
		return source;
	}

	public void setSource(Node source) {
		this.source = source;
	}

	public Node getTarget() {
		return target;
	}

	public void setTarget(Node target) {
		this.target = target;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}
	
	public double getUtilization() {
		return utilization;
	}

	public void setUtilization(Double utilization) {
		this.utilization = utilization;
	}

	public List<Node> getServicedNodes() {
		return servicedNodes;
	}

	public void setServicedNodes(List<Node> servicedNodes) {
		this.servicedNodes = servicedNodes;
	}
	
	public void addServicedNode(Node node) {
		if(this.servicedNodes == null) {
			servicedNodes = new ArrayList<>();
		}
		
		servicedNodes.add(node);
	}

	@Override
	public String toString() {
		return "Link [id=" + id + ", capacity=" + capacity + ", type=" + type + ", source=" + source.getId() + ", target=" + target.getId() + ", network=" + network.getId() + "]";

	}

}
