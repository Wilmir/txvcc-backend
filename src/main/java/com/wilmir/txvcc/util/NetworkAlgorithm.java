package com.wilmir.txvcc.util;

import java.util.LinkedList;
import java.util.List;

import com.wilmir.txvcc.model.Link;
import com.wilmir.txvcc.model.Network;
import com.wilmir.txvcc.model.Node;

public class NetworkAlgorithm {

	public static void calculateUtilization(Network network) {		
		Node homingNode = null;
		
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
				homingNode = currentNode;
			}
		}
		
		//Determine the shortest path from all nodes to the homing node
		if(homingNode != null)
			bfs(homingNode, network.getNodes());
	}
	
	
	private static void bfs(Node homingNode, List<Node> nodes) {
		LinkedList<Node> queue = new LinkedList<>();
		
		queue.add(homingNode);
		
		while(!queue.isEmpty()){
			Node currentNode = queue.remove();
			
			currentNode.setVisited(true);
			
			System.out.println("Printing path for node " + currentNode.getName());
			
			pathPrint(currentNode);
			
			System.out.println("\n");
			
			for(Node neighbor : currentNode.getNeighbors()){
				if(!neighbor.isVisited()) {
					queue.add(neighbor);
					neighbor.setVisited(true);
					neighbor.setParentNode(currentNode);
				}
				
			}
			
		}
	}


	private static void pathPrint(Node node) {
		if(node.getParentNode() != null)
			pathPrint(node.getParentNode());
		
		System.out.print(node.getName() + " ");
	}
	
	
}
