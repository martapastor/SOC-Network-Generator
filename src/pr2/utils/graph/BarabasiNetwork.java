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
		int m = 1;
		for (int i = 0; i < this.numSteps; i++) {
			nodeInsert(n);
			List<Integer> tmpNodes = new ArrayList<Integer>(); // Keep a list of nodes already used
			List<Integer> sortedNodes = new ArrayList<Integer>();
			
			for (int k = 0; k < this.getNumberOfNodes(); k++) {
				sortedNodes.add(k, this.getConnectionsOfNode(k));
			}
			
			for (int j = 0; j < this.numInitBonds; j++) {	
				do {	
					quicksort(sortedNodes, 0, this.getNumberOfNodes() - 1);
					
					int prob = ranProb.nextInt(sumNetworkDegrees - 1); // Select probability randomly
					int select = sortedNodes.get(0);
					m = 1;
					
					while (select < prob) { // If current probability has not reached the wanted one...
						select += sortedNodes.get(m);
						m++;
					}
					
				} while (tmpNodes.contains(m-1) || n == m-1); // If selected node has already been conected to new node
				
				nodeConnect(n, m-1);
				
				int tmp = this.getConnectionsOfNode(n);
				sortedNodes.set(n, this.getConnectionsOfNode(m - 1));
				sortedNodes.set(m, tmp);
				
				tmpNodes.add(m-1); // We have already used this node, let's add it here.
			}
			sumNetworkDegrees += this.numInitBonds * 2; // Update total network degree
			n++;
		}
	}
	
	private void quicksort(List<Integer> sortedNodes, int a, int b) {
		if (a <= b) {
			int p = partition(sortedNodes, a, b);
			quicksort(sortedNodes, a, p - 1);
			quicksort(sortedNodes, p + 1, b);
		}
	}
	
	private int partition(List<Integer> sortedNodes, int a, int b) {
		int i, j, p = 0;
		Integer aux;
		
		i = a + 1;
		j = b;
		
		while (i <= j) {
			if ((sortedNodes.get(i) < sortedNodes.get(a)) && (sortedNodes.get(j) > sortedNodes.get(a))) {
				aux = sortedNodes.get(i);
				sortedNodes.set(i, sortedNodes.get(j));
				sortedNodes.set(j, aux);
				
				i++;
				j--;
			}
			else {
				if (sortedNodes.get(i) >= sortedNodes.get(a)) {
					i++;
				}
				if (sortedNodes.get(j) <= sortedNodes.get(a)) {
					j--;
				}
			}
		}
		
		p = j;
		
		aux = sortedNodes.get(a);
		sortedNodes.set(a, sortedNodes.get(p));
		sortedNodes.set(p, aux);
		
		return p;
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
