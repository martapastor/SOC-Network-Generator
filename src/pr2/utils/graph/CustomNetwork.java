package pr2.utils.graph;

import java.io.File;
import java.util.HashMap;

public class CustomNetwork<T> extends Graph<T> {
	
	public CustomNetwork() {
		super();
		g.clear();
	}
	
	@SuppressWarnings("unchecked")
	public CustomNetwork(Graph<T> n) {
		this();
		g = (HashMap<T, HashMap<T, Integer>>) n.g.clone();
	}
	
	public CustomNetwork(File csvNodes, File csvEdges) {
		this();
		// TODO Read files and load data into graph
	}
	
}
