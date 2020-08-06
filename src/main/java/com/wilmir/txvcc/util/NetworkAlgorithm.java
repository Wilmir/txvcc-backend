package com.wilmir.txvcc.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.wilmir.txvcc.model.Link;
import com.wilmir.txvcc.model.Network;
import com.wilmir.txvcc.model.Node;
import com.wilmir.txvcc.model.ServiceModel;

public class NetworkAlgorithm {

	public static void calculateUtilization(Network network) {		
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


	private static void bfs(Node homingNode, List<Node> nodes) {

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

	private static void addServicetoLinksAlongThePath(Node node, Node currentNode, Node homingNode) {
		if(currentNode.getParentNode() != null) {
			//check the corresponding link between the node and its parent
			for(Link link : currentNode.getIncomingLinks()) {
				if(link.getSource() == currentNode.getParentNode()) {

					//add services of the node to the link
					for(ServiceModel service: node.getServices()) {
						//only add the service homed to the current homing node
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


	private static void addServiceCapacityToLink(double capacity, Link link) {
		link.setUtilization(link.getUtilization() + capacity);
	}



}
