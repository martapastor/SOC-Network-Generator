package pr2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pr2.utils.graph.Graph;

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
					if (Math.random() < (bondsProb / 100.0)) { // 10% probability
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
	
		// Initial number of nodes: m_0 = m + 1, where m is the number of bonds a new node enter in the network with.
		// igraph.getNodeList().size = m_0 + t, where t is the number of the steps (i.e. the number of nodes added).
		
		// Create initial nodes
		for (int i = 0; i < numInitBonds + 1; i++) { // Add numInitNodes nodes (the "object" used for the node is an integer, so nodes will be: 0, 1, 2, 3, ...)
			igraph.nodeInsert(i);
		}
		
		// At the beginning, all nodes are connected to all nodes once (as the graph is undirected)
		List<Integer> tmpInitNodes = new ArrayList<Integer>(); // Keep a list of nodes already used
		for (Integer n1 : igraph.getNodeList()) { // For each node...
			for (Integer n2 : igraph.getNodeList()) { // For each node again...
				if (!tmpInitNodes.contains(n2) && n1 != n2) {
					igraph.nodeConnect(n1, n2); // Add a connection between the two nodes
				}
			}
			tmpInitNodes.add(n1); // We have already used this node, lets add it here.
		}
		
		Integer n = numInitBonds + 1;
		
		// Add a new node in each step and connect it to m other different nodes
		for (int i = 0; i < numSteps; i++) {
			igraph.nodeInsert(n);
			List<Integer> tmpNodes = new ArrayList<Integer>(); // Keep a list of nodes already used
			for (int j = 0; j < numInitBonds; j++) {
				Random ran = new Random();
				int m = ran.nextInt(igraph.getNodeList().size());
				
				while (tmpNodes.contains(m) || n == m) { // If we haven't used this node yet
					m = ran.nextInt(numInitBonds);
				}
				
				igraph.nodeConnect(n, m);
				
				tmpNodes.add(m); // We have already used this node, let's add it here.
			}
			
			n += 1;
		}
		
		return igraph;
	}
}