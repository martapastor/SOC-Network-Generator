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
	
	public void regenerateNetwork() {
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
				}
			}
			tmpInitNodes.add(n1); // We have already used this node, lets add it here.
		}
		
		Integer n = this.numInitBonds + 1;
		
		// Add a new node in each step and connect it to m other different nodes
		for (int i = 0; i < this.numSteps; i++) {
			nodeInsert(n);
			List<Integer> tmpNodes = new ArrayList<Integer>(); // Keep a list of nodes already used
			for (int j = 0; j < this.numInitBonds; j++) {
				Random ran = new Random();
				int m = ran.nextInt(getNodeList().size());
				
				while (tmpNodes.contains(m) || n == m) { // If we haven't used this node yet
					m = ran.nextInt(this.numInitBonds);
				}
				
				nodeConnect(n, m);
				
				tmpNodes.add(m); // We have already used this node, let's add it here.
			}
			
			n += 1;
		}
	}
	
	public String toString() {
		return "Barabasi Network\n"+super.toString();
	}
}
