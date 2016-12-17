package pr2.utils.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a Graph.<br>
 * The class is parameterized. The parameter represents the type of the nodes.<br>
 * Nodes should be comparable.
 *
 * @param <T>
 */
public abstract class Graph<T> {
	protected HashMap<T, HashMap<T, Integer>> g;
	
	public Graph() {
		g = new HashMap<T, HashMap<T, Integer>>();
		regenerateNetwork();
	}
	
	/**
	 * Generates the network again (clear)
	 */
	public void regenerateNetwork() {
		g.clear();
	}
	
	/**
	 * Checks if a node exists.
	 * 
	 * @param node
	 * @return if exists
	 */
	public boolean nodeExists(T node) {
		return this.g.containsKey(node);
	}
	
	/**
	 * Inserts a new node (if it does not exists).
	 * 
	 * @param node new node to be inserted
	 */
	public void nodeInsert(T node) {
		if (!nodeExists(node)) {
			this.g.put(node, new HashMap<T, Integer>());
		}
	}
	
	/**
	 * Removes a node from the graph.<br>
	 * All connections with the nodes connected to this node will also be removed.
	 * 
	 * @param node node to remove
	 */
	public void nodeRemove(T node) {
		if (nodeExists(node)) {
			for (Map.Entry<T, Integer> connection : this.g.get(node).entrySet()) {
				this.g.get(connection.getKey()).remove(node);
			}
			this.g.remove(node);
		}
	}
	
	/**
	 * Checks if two nodes are connected.
	 * 
	 * @param n1 One of the nodes in the graph
	 * @param n2 One of the nodes in the graph
	 * @return true if both nodes are in the graph, are different, and are connected. false otherwise.
	 */
	public boolean nodeConnection(T n1, T n2) {
		if (n1.equals(n2)) return false; // ?
		if (!nodeExists(n1) || !nodeExists(n2)) {
			return false; // ?
		}
		return (this.g.get(n1).containsKey(n2) && this.g.get(n1).get(n2) > 0) || (this.g.get(n2).containsKey(n1) && this.g.get(n2).get(n1) > 0);
	}
	
	/**
	 * Connects two nodes.<br>
	 * If already connected, the weight of the connection will be increased by one.
	 * 
	 * @param n1 One of the nodes in the graph
	 * @param n2 One of the nodes in the graph
	 */
	public void nodeConnect(T n1, T n2) {
		if (!nodeConnection(n1, n2)) {
			nodeConnectSetWeight(n1, n2, 1);
		} else {
			nodeConnectSetWeight(n1, n2, nodeConnectionGetWeight(n1, n2) + 1);
		}
	}
	
	/**
	 * Disconnects two nodes if they are connected.
	 * 
	 * @param n1 One of the nodes in the graph
	 * @param n2 One of the nodes in the graph
	 */
	public void nodeDisconnect(T n1, T n2) {
		if (nodeConnection(n1, n2)) {
			nodeConnectSetWeight(n1, n2, 0);
		}
	}
	
	/**
	 * Gets the weight of the connection between two nodes.
	 * 
	 * @param n1 One of the nodes in the graph
	 * @param n2 One of the nodes in the graph
	 * @return the weight of a connection between 2 nodes, or 0 if the nodes are disconnected
	 */
	public int nodeConnectionGetWeight(T n1, T n2) {
		if (!nodeConnection(n1, n2)) {
			return 0;
		} else {
			return this.g.get(n1).get(n2);
		}
	}
	
	/**
	 * Gets a List of all nodes in the graph.<br>
	 * The List is parameterized with the same parameter as this class.
	 * 
	 * @return list of nodes
	 */
	public List<T> getNodeList() {
		return new ArrayList<T>(this.g.keySet());
	}
	
	/**
	 * Gets a List of all nodes connected to the specified node in the graph.<br>
	 * The List is parameterized with the same parameter as this class.
	 * 
	 * @param n One of the nodes in the graph
	 * @return list of nodes connected to the specified node
	 */
	public List<T> getConnectionList(T n) {
		return new ArrayList<T>(this.g.get(n).keySet());
	}
	
	/**
	 * Gets The number of nodes connected to the specified node.
	 * 
	 * @param n One of the nodes in the graph
	 * @return number of nodes connected to the specified node
	 */
	public int getConnectionsOfNode(T n) {
		return this.g.get(n).size();
	}
	
	/**
	 * Gets the number of nodes in the graph.
	 * 
	 * @return the number of nodes
	 */
	public int getNumberOfNodes() {
		return this.g.size();
	}
	
	/**
	 * Gets the number of nodes connected to the specified node in the graph.
	 * 
	 * @param n specified node
	 * @return number of connections of specified node
	 */
	public int getNumberOfConnectionsOfNode(T n) {
		return this.g.get(n).size();
	}
	
	/**
	 * Sets the weight of a connection between two given nodes.
	 * 
	 * @param n1 One of the nodes in the graph
	 * @param n2 One of the nodes in the graph
	 * @param w the new weight between the nodes. If set to 0, the connection will be removed.
	 */
	public void nodeConnectSetWeight(T n1, T n2, int w) {
		if (w == 0) {
			this.g.get(n1).remove(n2);
			this.g.get(n2).remove(n1);
		} else {
			this.g.get(n1).put(n2, w);
			this.g.get(n2).put(n1, w);
		}
	}
	
	/**
	 * Gets the density of a graph.
	 * 
	 * @return the density
	 */
	public double getGraphDensity() {
		double potentialConnections = 0;
		double totalConnections = 0;
		
        for (int i = getNumberOfNodes() - 1; i > 0; i--) { 
        	potentialConnections += i;
        }
		
		List<T> tmpNodes = new ArrayList<T>();
		for (Map.Entry<T, HashMap<T, Integer>> node : this.g.entrySet()) {
			for (Map.Entry<T, Integer> connections : node.getValue().entrySet()) {
				if (!tmpNodes.contains(connections.getKey())) { // Avoid duplicates
					totalConnections++;
				}
			}
			tmpNodes.add(node.getKey());
		}
		return totalConnections / potentialConnections;
	}
	
	/**
	 * Gets the biggest hub: the node with the greatest amount of connections.
	 * 
	 * @return the node of the biggest hub, or null if there are no nodes
	 */
	public T getBiggestHub() {
		if (getNumberOfNodes() == 0) return null;
		T n = getNodeList().get(0);
		for (Map.Entry<T, HashMap<T, Integer>> node : this.g.entrySet()) {
			if (getNumberOfConnectionsOfNode(node.getKey()) > getNumberOfConnectionsOfNode(n)) {
				n = node.getKey();
			}
		}
		return n;
	}
	
	/**
	 * Gets the number of connections of the biggest hub.
	 * 
	 * @return the size of the biggest hub (0 when there are no nodes)
	 */
	public int getBiggestHubSize() {
		T n = getBiggestHub();
		if (n == null) return 0;
		return getNumberOfConnectionsOfNode(n);
	}
	
	/**
	 * Gets the shortest distance between nodes
	 * 
	 * @param n1 One of the nodes in the graph
	 * @param n2 One of the nodes in the graph
	 * @return shortest distance between nodes if both nodes exist in the graph and there is a path between them, -1 otherwise
	 */
	public int getPathDistance(T n1, T n2) {
		if (n1.equals(n2)) return 0;
		if (!nodeExists(n1) || !nodeExists(n2)) {
			return -1; // ?
		}
		
		int distance = 0;
		List<T> lastStep = new ArrayList<T>();
		List<T> tmpLastStep = new ArrayList<T>();
		List<T> oldSteps = new ArrayList<T>();
		lastStep.add(n1);
		oldSteps.add(n1);
		
		while (!lastStep.isEmpty() && distance < this.getNumberOfNodes()) {
			distance++;
			tmpLastStep.clear();
			for (T n : lastStep) {
				for (Map.Entry<T, Integer> connection : this.g.get(n).entrySet()) {
					if (connection.getKey().equals(n2)) {
						return distance;
					}
					if (!oldSteps.contains(connection.getKey())) {
						tmpLastStep.add(connection.getKey());
					}
				}
			}
			lastStep.clear();
			lastStep.addAll(tmpLastStep);
			oldSteps.addAll(lastStep);
		}
		return -1;
	}
	
	/**
	 * Gets clustering coefficient for a node
	 * 
	 * @return the clustering coefficient for a node
	 */
	public double getNodeClusteringCoefficient(T n) {
		if (!nodeExists(n)) {
			return -1;
		}
		//return (2 * ?) / (getConnectionsOfNode(n) * (getConnectionsOfNode(n)-1));
		return 0.0;
	}
	
	/**
	 * Gets the medium clustering coefficient for the network
	 * 
	 * @return the medium clustering coefficient for the network
	 */
	public double getClusteringCoefficient() {
		double cc = 0;
		for (Map.Entry<T, HashMap<T, Integer>> node : this.g.entrySet()) {
			cc += getNodeClusteringCoefficient(node.getKey());
		}
		return cc / getNumberOfNodes();
	}
	
	/**
	 * Returns a string with all the nodes and all their connections.
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Map.Entry<T, HashMap<T, Integer>> node : this.g.entrySet()) {
			s.append("Node: "+node.getKey()+"\n\tConnected to: ");
			for (Map.Entry<T, Integer> connections : node.getValue().entrySet()) {
				s.append(connections.getKey()+"("+connections.getValue()+")    ");
			}
			s.append("\n");
		}
		return s.toString();
	}
	
	/**
	 * Returns a string with all the nodes, in CVS format.
	 */
	public String nodesToCSVstring() { // This prints connected nodes in pairs. This would be like "nodes.csv".
		StringBuilder s = new StringBuilder();
		s.append("Id;Label\n");
		for (Map.Entry<T, HashMap<T, Integer>> node : this.g.entrySet()) {
			s.append(node.getKey() + ";" + node.getKey() + "\n");
		}
		return s.toString();
	}
	
	/**
	 * Returns a string with all the edges, in CVS format.
	 */
	public String edgesToCSVstring() { // This prints connected nodes in pairs. This would be like "edges.csv".
		List<T> tmpNodes = new ArrayList<T>();
		StringBuilder s = new StringBuilder();
		s.append("Source;Target;Weight;Type\n");
		for (Map.Entry<T, HashMap<T, Integer>> node : this.g.entrySet()) {
			for (Map.Entry<T, Integer> connections : node.getValue().entrySet()) {
				if (!tmpNodes.contains(connections.getKey())) { // Avoid duplicates
					s.append(node.getKey() + ";" + connections.getKey()+";"+connections.getValue()+";Undirected\n");
				}
			}
			tmpNodes.add(node.getKey());
		}
		return s.toString();
	}
}

