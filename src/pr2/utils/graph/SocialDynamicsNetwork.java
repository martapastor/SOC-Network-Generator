package pr2.utils.graph;

public class SocialDynamicsNetwork extends Graph<Integer> {

	public SocialDynamicsNetwork() {
		super();
		regenerateNetwork();
	}
	
	/**
	 * Generates the network again as a Social Dynamics network
	 */
	public void regenerateNetwork() {
		// We use the timestamp
	}
	
	@Override
	public double getNodeClusteringCoefficient(Integer n) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String toString() {
		return "Social Dynamics Network\n"+super.toString();
	}
}
