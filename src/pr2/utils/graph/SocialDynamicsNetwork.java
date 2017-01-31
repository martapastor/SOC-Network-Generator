package pr2.utils.graph;

import java.util.Random;

public class SocialDynamicsNetwork extends GraphDirected<Integer> {
	private int numInitNodes = 56;
	private int duration = 20;
	
	public SocialDynamicsNetwork(int numInitNodes, int duration) {
		super();
		this.numInitNodes = numInitNodes;
		this.duration = duration;
		regenerateNetwork();
	}
	
	/**
	 * Generates the network again as a Social Dynamics network
	 */
	public void regenerateNetwork() {
		this.nodes.clear();
		this.edges.clear();
		
		// Create initial nodes
		for (int i = 0; i < this.numInitNodes; i++) { // Add numInitNodes nodes (the "object" used for the node is an integer, so nodes will be: 0, 1, 2, 3, ...)
			insertNode(i);
		}
		
		Random rand = new Random();
		
		// Create (numInit / 2) initial random bonds
		int initBonds = numInitNodes / 2;
		for (int j = 0; j < initBonds; j++) {
			int nodeOrigin = rand.nextInt(numInitNodes - 1);
			int nodeTarget = rand.nextInt(numInitNodes - 1);
			
			insertEdge(nodeOrigin, nodeTarget, 0);
		}
		
		// Every random seconds, nodes may change their behaviour if the network changes it.
		
		for (int k = 0; k < duration; k++) {
			int range = rand.nextInt(duration / 3);
			
			int nodeOrigin = rand.nextInt(numInitNodes - 1);
			int newNodeTarget = rand.nextInt(numInitNodes - 1);
			int currentNodeTarget = 0;
			
			if (this.getTargetNode(nodeOrigin) != null) {
				currentNodeTarget = this.getTargetNode(nodeOrigin);
			}
			
			if (this.getAttackConnectionsOfNode(newNodeTarget) >= this.getAttackConnectionsOfNode(currentNodeTarget)) {
				int randRange = rand.nextInt(duration - range);
				Edge<Integer> newEdge = new Edge<Integer>(nodeOrigin, newNodeTarget);
				
				if (this.edges.contains(newEdge)) {
					setDeletionTimestamp(nodeOrigin, newNodeTarget, k + randRange);
				}
				else {
					setDeletionTimestamp(nodeOrigin, currentNodeTarget, k);
					
					insertEdge(nodeOrigin, newNodeTarget, k);
					setDeletionTimestamp(nodeOrigin, newNodeTarget, k + randRange);
				}
			}
		}
		
		//System.out.println(this.nodesToCSVstring());
		//System.out.println(this.edgesToCSVstring());
	}

	@Override
	public String toString() {
		return "Social Dynamics Network\n" + super.toString();
	}
}
