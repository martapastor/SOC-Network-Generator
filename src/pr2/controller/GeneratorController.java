package pr2.controller;

import pr2.utils.Graph;

import java.util.ArrayList;
import java.util.List;

public class GeneratorController {
	
	public GeneratorController() {
		
	}
	
	public Graph<Integer> generateRandomNetwork(Integer numInitNodes, Integer bondsProb) {
		Graph<Integer> igraph = new Graph<Integer>();
		
		for (int i = 0; i < numInitNodes; i++) { // Add numInitNodes nodes (the "object" used for the node is an integer, so nodes will be: 0, 1, 2, 3, ...)
			igraph.nodeInsert(i);
		}
		
		List<Integer> tmpNodes = new ArrayList<Integer>(); // Keep a list of nodes already used
		for (Integer n1 : igraph.getNodeList()) { // For each node...
			for (Integer n2 : igraph.getNodeList()) { // For each node again...
				if (!tmpNodes.contains(n2)) { // If we haven't used this node yet
					if (Math.random() < bondsProb) { // 10% probability
						igraph.nodeConnect(n1, n2); // Add a connection between the two nodes
					}
				}
			}
			tmpNodes.add(n1); // We have already used this node, lets add it here.
		}
		
		/*
		 * tmpNodes is there to avoid adding a connection between 10 and 20, and then adding another connection between 20 and 10.
		 * To see what happens when tmpNodes is not used, comment out the line "tmpNodes.add(n1);".
		 */
		
		return igraph;
	}
	
	public Graph<Integer> generateBarabasiNetwork(Integer numInitBonds, Integer numSteps) {
		Graph<Integer> igraph = new Graph<Integer>();
		
		// Barabasi code goes here
		
		return igraph;
	}
}