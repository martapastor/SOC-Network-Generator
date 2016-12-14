package pr2.utils.graph;

import java.util.ArrayList;
import java.util.List;

public class RandomNetwork extends Graph<Integer> {
	
	private int numInitNodes;
	private int bondsProb;
	
	public RandomNetwork(int numInitNodes, int bondsProb) {
		super();
		this.numInitNodes = numInitNodes;
		this.bondsProb = bondsProb;
		regenerateNetwork();
	}
	
	/**
	 * Generates the network again as a Random network
	 */
	public void regenerateNetwork() {
		for (int i = 0; i < this.numInitNodes; i++) { // Add numInitNodes nodes (the "object" used for the node is an integer, so nodes will be: 0, 1, 2, 3, ...)
			nodeInsert(i);
		}
		
		List<Integer> tmpNodes = new ArrayList<Integer>(); // Keep a list of nodes already used
		for (Integer n1 : getNodeList()) { // For each node...
			for (Integer n2 : getNodeList()) { // For each node again...
				if (!tmpNodes.contains(n2)) { // If we haven't used this node yet
					if (Math.random() < (this.bondsProb / 100.0)) { // 10% probability
						nodeConnect(n1, n2); // Add a connection between the two nodes
					}
				}
			}
			tmpNodes.add(n1); // We have already used this node, lets add it here.
		}
		
		/*
		 * tmpNodes is there to avoid adding a connection between 10 and 20, and then adding another connection between 20 and 10.
		 * To see what happens when tmpNodes is not used, comment out the line "tmpNodes.add(n1);".
		 */
	}
	
	public String toString() {
		return "Random Network\n"+super.toString();
	}
}
