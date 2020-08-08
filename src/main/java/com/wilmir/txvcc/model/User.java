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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonView;
import com.wilmir.txvcc.view.Views;

@Entity
@Table(name = "user")
public class User {

	@JsonView(Views.Public.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@JsonView(Views.Public.class)
	@Column(name = "name")
	private String name;
	
	@JsonView(Views.Public.class)
	@Column(name = "username")
	private String username;
	
	
	@Column(name = "password")
	private String password;
	
	@JsonView(Views.Public.class)
	@Column(name = "email")
	private String email;
	
	@JsonView(Views.Public.class)
	@CreationTimestamp
	@Column(name = "date_created")
	private Date dateCreated;
	
	@JsonView(Views.Public.class)
	@UpdateTimestamp
	@Column(name = "last_updated")
	private Date lastUpdated;
	
	@JsonView(Views.Public.class)
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Network> networks;
	
	public User() {}

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public List<Network> getNetworks() {
		return networks;
	}

	public void setNetworks(List<Network> networks) {
		this.networks = networks;
	}
	

	public void addNetwork(Network network) {
		if(this.networks == null) {
			this.networks = new ArrayList<>();
		}
		
		networks.add(network);
		network.setUser(this);
	}
	
}
