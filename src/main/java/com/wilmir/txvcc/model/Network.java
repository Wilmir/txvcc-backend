package com.wilmir.txvcc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonView;
import com.wilmir.txvcc.view.Views;


@Entity
@Table(name = "network")
public class Network {
	
	@JsonView(Views.Public.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@JsonView(Views.Public.class)
	@Column(name = "network_name")
	private String networkName;
	
	@JsonView(Views.Public.class)
	@Column(name = "description")
	private String description;
	
	
	@JsonView(Views.Public.class)
	@Column(name = "date_created")
	@CreationTimestamp
	private Date dateCreated;
	
	@JsonView(Views.Public.class)
	@Column(name = "last_updated")
	@UpdateTimestamp
	private Date lastUpdated;
	
	@JsonView(Views.Public.class)
	@OneToMany(mappedBy="network", cascade = CascadeType.ALL)
	private List<Node> nodes;
	
	@JsonView(Views.Public.class)
	@OneToMany(mappedBy="network", cascade = CascadeType.ALL)
	private List<Link> links;
	

	public Network() {
		
	}
	
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
	
	public Node addNode(Node node) {
		if(nodes == null) {
			nodes = new ArrayList<>();
		}
		
		nodes.add(node);
		
		node.setNetwork(this);
		
		return node;
	}
	
	public Link addLink(Link link) {
		if(links == null) {
			links = new ArrayList<>();
		}

		links.add(link);
		
		link.setNetwork(this);
		
		return link;
		
	}
	
	public void removeLink(int id) {
		for(Link link : links) {
			if(link.getId() == id) {
				links.remove(id);
				return;
			}
		}
	}


	@Override
	public String toString() {
		return "Network [id=" + id + ", networkName=" + networkName + ", dateCreated=" + dateCreated + ", lastUpdated="
				+ lastUpdated + ", stations=" + nodes + ", links=" + links + "]";
	}
	
	
	@PostLoad
	public void calculateUtilization() {
		System.out.println("Calculating Utilization for: " + this.getNetworkName());
		 calculateUtilization(this);
	}


	private void calculateUtilization(Network network) {
			List<Node> homingNodes = new ArrayList<>();
			System.out.println("Initial size of homing nodes: " + homingNodes.size());
			
			// Set the neighbor of all nodes and identify the homingNode in the process
			for(Node currentNode : network.getNodes()) {
				List<Link> incomingLinks = currentNode.getIncomingLinks();
				
				for(Link link : incomingLinks) {
					Node source = link.getSource();
					
					//the addNeighbor method also adds the currentNode to the source's neighbors
					currentNode.addNeighbor(source);
					
				}
				
				//find the homingNode
				if(currentNode.isHoming()) {
					homingNodes.add(currentNode);
				}
			}
			
			//Determine the shortest path from all nodes to the homing node
			if(network.getLinks().size() > 0 && network.getNodes().size() > 0 && homingNodes.size() > 0) {
				System.out.println("Number of homing nodes found in" + network.getNetworkName()+  " is: " + homingNodes.size());
				for(Node homingNode: homingNodes) {
					System.out.println("Tracing for Homing Node: " + homingNode.getName());
					bfs(homingNode, network.getNodes());
				}
			}
		
	}
	
	
	private void bfs(Node homingNode, List<Node> nodes) {
		
		LinkedList<Node> queue = new LinkedList<>();
		
		for(Node node:nodes) {
			node.setParentNode(null);
			node.setVisited(false);
		}
		
		queue.add(homingNode);
		
		while(!queue.isEmpty()){
			Node currentNode = queue.remove();
			currentNode.setVisited(true);
						
			addServicetoLinksAlongThePath(currentNode, currentNode, homingNode);
			
			for(Node neighbor : currentNode.getNeighbors()){
				if(!neighbor.isVisited()) {
					queue.add(neighbor);
					neighbor.setVisited(true);
					neighbor.setParentNode(currentNode);
				}
			}
			
		}
	}

	private void addServicetoLinksAlongThePath(Node node, Node currentNode, Node homingNode) {
		if(currentNode.getParentNode() != null) {
			//check the corresponding link between the node and its parent
			for(Link link : currentNode.getIncomingLinks()) {
				if(link.getSource() == currentNode.getParentNode()) {
					
					//add node to the list of nodes served by the link
					for(ServiceModel service: node.getServices()) {
						if(service.getHomingNode() == homingNode) {
							link.addService(service);
							addServiceCapacityToLink(service.getCapacity(), link);
							
						}
					}
					
				}
					
			}
			
			for(Link link : currentNode.getOutgoingLinks()) {
				if(link.getTarget() == currentNode.getParentNode()) {
					

					//add node to the list of nodes served by the link
					for(ServiceModel service: node.getServices()) {
						if(service.getHomingNode() == homingNode) {
							link.addService(service);
							addServiceCapacityToLink(service.getCapacity(), link);
							
						}
					}
				}
					
			}
			
			//perform a recursive call to links along the path
			addServicetoLinksAlongThePath(node, currentNode.getParentNode(), homingNode);

		}
	}


	private void addServiceCapacityToLink(double capacity, Link link) {
			link.setUtilization(link.getUtilization() + capacity);
	}

	
}
