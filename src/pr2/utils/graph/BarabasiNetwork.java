package pr2.utils.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BarabasiNetwork extends Graph<Integer> {
	private int numInitBonds;
	private int numSteps;
	
	public BarabasiNetwork(int numInitBonds, int numSteps) {
		super();
		this.numInitBonds = numInitBonds;
		this.numSteps = numSteps;
		regenerateNetwork();
	}
	
	/**
	 * Generates the network again as a Barabasi network
	 */
	public void regenerateNetwork() {
		int sumNetworkDegrees = 0;
		
		this.g.clear();
		// Initial number of nodes: m_0 = m + 1, where m is the number of bonds a new node enter in the network with.
		// igraph.getNodeList().size = m_0 + t, where t is the number of the steps (i.e. the number of nodes added).
		
		// Create initial nodes
		for (int i = 0; i < this.numInitBonds + 1; i++) { // Add numInitNodes nodes (the "object" used for the node is an integer, so nodes will be: 0, 1, 2, 3, ...)
			nodeInsert(i);
		}
		
		// At the beginning, all nodes are connected to all nodes once (as the graph is undirected)
		List<Integer> tmpInitNodes = new ArrayList<Integer>(); // Keep a list of nodes already used
		for (Integer n1 : getNodeList()) { // For each node...
			for (Integer n2 : getNodeList()) { // For each node again...
				if (!tmpInitNodes.contains(n2) && n1 != n2) {
					nodeConnect(n1, n2); // Add a connection between the two nodes
					sumNetworkDegrees += 2;
				}
			}
			tmpInitNodes.add(n1); // We have already used this node, lets add it here.
		}
		
		Integer n = this.numInitBonds + 1;
		
		// Add a new node in each step and connect it to m other different nodes using preferential attachment
		Random ranProb = new Random();
		int m;
		for (int i = 0; i < this.numSteps; i++) {
			nodeInsert(n);
			List<Integer> tmpNodes = new ArrayList<Integer>(); // Keep a list of nodes already used
			for (int j = 0; j < this.numInitBonds; j++) {	
				do {
					int prob = ranProb.nextInt(sumNetworkDegrees - 1); // Select probability randomly
					int select = this.getConnectionsOfNode(0); // Keep current probability beggining from node 0
					m = 1;
					
					while (select < prob) { // If current probability has not reached the wanted one...
						select += this.getConnectionsOfNode(m);
						m++;
					}
					
				} while (tmpNodes.contains(m-1) || n == m-1); // If selected node has already been conected to new node
				
				nodeConnect(n, m-1);
				
				tmpNodes.add(m-1); // We have already used this node, let's add it here.
			}
			sumNetworkDegrees += this.numInitBonds * 2; // Update total network degree
			n++;
		}
	}
	
	@Override
	public double getNodeClusteringCoefficient(Integer n) {
		if (!nodeExists(n)) {
			return -1;
		}
		return (this.numSteps / 8.0) * (Math.pow(Math.log(this.numInitBonds), 2)) / this.numInitBonds;
	}
	
	@Override
	public String toString() {
		return "Barabasi Network\n"+super.toString();
	}
}
