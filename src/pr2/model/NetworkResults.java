package pr2.model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class NetworkResults {
	
	public NetworkResults() {
		
	}
	
	public PrintStream readResults(String type) {
		PrintStream out = null;
		
		return out;
	}
	
	public PrintStream saveResults(String type, Integer param1, Integer param2) {
		PrintStream out = null;
		try {
			out = new PrintStream(new FileOutputStream("results/" + type + "_" + param1 + "_" + param2 + ".csv"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return out;
	}
}