package pr2.controller;

import pr2.utils.graph.BarabasiNetwork;
import pr2.utils.graph.Graph;
import pr2.utils.graph.GraphDirected;
import pr2.utils.graph.RandomNetwork;
import pr2.utils.graph.SocialDynamicsNetwork;

public class GeneratorController {
	
	public GeneratorController() {
		
	}
	
	public Graph<Integer> generateRandomNetwork(int numInitNodes, double bondsProb) {
		Graph<Integer> network = new RandomNetwork(numInitNodes, bondsProb);
		return network;
	}
	
	public Graph<Integer> generateBarabasiNetwork(int numInitBonds, int numSteps) {
		Graph<Integer> network = new BarabasiNetwork(numInitBonds, numSteps);
		return network;
	}
	
	public GraphDirected<Integer> generateSocialDynamicsNetwork(int socialInitNodes, int duration) {
		GraphDirected<Integer> network = new SocialDynamicsNetwork(socialInitNodes, duration);
		return network;
	}
}