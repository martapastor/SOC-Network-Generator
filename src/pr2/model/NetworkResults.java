package pr2.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class NetworkResults {
	
	public NetworkResults() {
		// If results folder does not exist, we must create it.
		
		 File directory = new File("results");
		    if (!directory.exists()) {
		        directory.mkdir();
		    }
	}
	
	public PrintStream readResults(String type) {
		PrintStream out = null;
		
		return out;
	}
	
	public PrintStream saveNodesResults(String type, Integer param1, double param2) {
		PrintStream out = null;
		try {
			out = new PrintStream(new FileOutputStream("results/" + type + "_" + param1 + "_" + param2 + "_nodes.csv"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		
		return out;
	}
	
	public PrintStream saveEdgesResults(String type, Integer param1, double param2) {
		PrintStream out = null;
		try {
			out = new PrintStream(new FileOutputStream("results/" + type + "_" + param1 + "_" + param2 + "_edges.csv"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		
		return out;
	}
}