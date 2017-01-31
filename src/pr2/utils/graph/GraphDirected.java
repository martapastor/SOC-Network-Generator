package pr2.utils.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Directed Dynamic Graph.<br>
 * The class is parameterized. The parameter represents the type of the nodes.<br>
 * Nodes should be comparable.
 *
 * @param <T>
 */
public class GraphDirected<NodeType> {
	private static int totalNumberOfNodes = 0;
	protected List<NodeType> nodes = new ArrayList<NodeType>();
	protected List<Edge<NodeType>> edges = new ArrayList<Edge<NodeType>>();
	
	private int firstTimestamp = -1;
	private int lastTimestamp = -1;
	
	class Edge<N extends NodeType> {
		private int id;
		protected N node1;
		protected N node2;
		protected int timestampCreation;
		protected int timestampDeletion;
		protected int weight;
		
		public Edge(N node1, N node2, int timestamp, int weight) {
			this.id = totalNumberOfNodes++;
			this.node1 = node1;
			this.node2 = node2;
			this.timestampCreation = timestamp;
			this.timestampDeletion = -1;
			this.weight = weight;
		}
		
		public Edge(N node1, N node2, int timestamp) {
			this(node1, node2, timestamp, 1);
		}
		
		public Edge(N node1, N node2) {
			this(node1, node2, 0, 1);
		}
		
		public void setDeletionTimestamp(int timestampDeletion) {
			this.timestampDeletion = timestampDeletion;
		}
		
		@SuppressWarnings("rawtypes")
		public boolean equals(Object o){
			if(o == null) return false;
			if(!(o instanceof Edge)) return false;
			return this.id == ((Edge)o).id;
		}
		
		public int hashCode(){
			return this.id;
		}
	}
	
	public void updateTimestampRange(int timestamp) {
		if (firstTimestamp == -1 || timestamp < firstTimestamp) {
			firstTimestamp = timestamp;
		}
		if (lastTimestamp == -1  || timestamp > lastTimestamp) {
			lastTimestamp = timestamp;
		}
	}
	
	public void insertNode(NodeType n) {
		nodes.add(n);
	}
	
	public void insertEdge(NodeType n1, NodeType n2, int timestamp) {
		Edge<NodeType> e = new Edge<NodeType>(n1, n2, timestamp);
		edges.add(e);
		updateTimestampRange(timestamp);
	}
	
	public int getAttackConnectionsOfNode(NodeType n) {
		int connections = 0;
		for (int i = 0; i < this.edges.size(); i++) {
			if (this.edges.get(i).node2 == n) {
				connections++;
			}
		}
		
		return connections;
	}
	
	public NodeType getTargetNode(NodeType n) {
		NodeType targetNode = null;
		for (int i = 0; i < this.edges.size(); i++) {
			if (this.edges.get(i).node1 == n) {
				targetNode = this.edges.get(i).node2;
			}
		}
		
		return targetNode;
	}
	
	public void setDeletionTimestamp(NodeType n1, NodeType n2, int timestamp) {
		for (Edge<NodeType> edge : this.edges) {
			if (edge.node1.equals(n1) && edge.node2.equals(n2) && (edge.timestampDeletion == -1)) {
				edge.setDeletionTimestamp(timestamp);
				break;
			}
		}
	}
	
	public int getFirstTimestamp() {
		return this.firstTimestamp;
	}
	
	public int getLastTimestamp() {
		return this.lastTimestamp;
	}
	
	/**
	 * Returns a string with all the nodes and all their connections.
	 */
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		
		s.append(nodes.size() + " nodes have been added to the network. \n \n");
		int duration = getLastTimestamp() - getFirstTimestamp();
		s.append(edges.size() + " edges have been generated between them for " + duration + " seconds. \n \n");
		/*
		for (Map.Entry<NodeType, HashMap<NodeType, Integer>> node : this.edges.entrySet()) {
			s.append("Node: "+node.getKey()+"\n\tConnected to: ");
			for (Map.Entry<NodeType, Integer> connections : node.getValue().entrySet()) {
				s.append(connections.getKey()+"("+connections.getValue()+")    ");
			}
			s.append("\n");
		}
		*/
		return s.toString();
	}
	
	
	public String nodesToCSVstring() {
		StringBuilder s = new StringBuilder();
		s.append("Id;Label\n");
		for (NodeType node : this.nodes) {
			s.append(node + ";" + node + "\n");
		}
		return s.toString();
	}
	
	public String edgesToCSVstring() { 
		StringBuilder s = new StringBuilder();
		s.append("Source;Target;Type;timeset;weight\n");
		for (Edge<NodeType> edge : this.edges) {
			int endTimestamp = (edge.timestampDeletion == -1) ? this.lastTimestamp : edge.timestampDeletion;
			s.append(edge.node1 + ";" + edge.node2 + ";Directed;<[" + edge.timestampCreation + "000, "+ endTimestamp + "000]>;" + edge.weight + "\n");
		}
		return s.toString();
	}
	
}

