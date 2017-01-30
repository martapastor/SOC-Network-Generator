package pr2.utils.graph;

import java.util.ArrayList;
import java.util.List;

public class RandomNetwork extends Graph<Integer> {

	private int numInitNodes;
	private double bondsProb;

	public RandomNetwork(int numInitNodes, double bondsProb) {
		super();
		this.numInitNodes = numInitNodes;
		this.bondsProb = bondsProb;
		regenerateNetwork();
	}

	/**
	 * Generates the network again as a Random network
	 */
	public void regenerateNetwork() {
		this.g.clear();
		for (int i = 0; i < this.numInitNodes; i++) { // Add numInitNodes nodes (the "object" used for the node is an integer, so nodes will be: 0, 1, 2, 3, ...)
			nodeInsert(i);
		}

		for (Integer n1 : getNodeList()) { // For each node...
			for (Integer n2 = n1 + 1; n2 < this.numInitNodes; n2++) { // For each node again...
				if (Math.random() < this.bondsProb) { 
					nodeConnect(n1, n2); // Add a connection between the two nodes
				}
			}
		}
	}

	@Override
	public double getNodeClusteringCoefficient(Integer n) {
		if (!nodeExists(n)) {
			return -1;
		}
		return this.bondsProb;
	}

	@Override
	public String toString() {
		return "Random Network\n"+super.toString();
	}
}
