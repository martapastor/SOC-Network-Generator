package practica2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class SwingView extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private static String type;
	
	private static Integer numInitNodes = 1000;
	
	private static Integer bondsProb = 10;
	
	private static Integer numInitBonds;
	
	private static Integer numSteps;
	
	private static Boolean error = false;
	
	public SwingView() {	
		
		initGUI();
	}
	
	private void initGUI() {
		//ImageIcon icon = new ImageIcon("C:\\hlocal\\Eclipse Neon\\Pr6\\src\\es\\ucm\\fdi\\tp\\assignment6\\icon.png");
		//this.setIconImage(icon.getImage());

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 400);
		JPanel window = new JPanel(new BorderLayout());
		
		// Default title panel available in every view.
			JPanel titlePanel = new JPanel();
			titlePanel.setPreferredSize(new Dimension(600, 200));
			
			ImageIcon UCMlogo = new ImageIcon(new ImageIcon("UCM logo.png").getImage().getScaledInstance(100, 114, Image.SCALE_DEFAULT));
			JLabel UCMlogoLabel = new JLabel("", UCMlogo, JLabel.CENTER);
			UCMlogoLabel.setIcon(UCMlogo);
			titlePanel.add(UCMlogoLabel, BorderLayout.WEST);
			
			JLabel titleText = new JLabel("  NETWORK GENERATOR  "); 
			titleText.setPreferredSize(new Dimension(400, 200));
			titleText.setFont(new Font("Helvetica", Font.PLAIN, 28));
			titlePanel.add(titleText, BorderLayout.CENTER);
			
			titlePanel.setVisible(true);
			window.add(titlePanel, BorderLayout.NORTH);
			
		//
		
		JPanel selectNetworkPanel = new JPanel();
		selectNetworkPanel.setPreferredSize(new Dimension(600, 180));
		
		JButton randomButton = new JButton("Random network");
		randomButton.setPreferredSize(new Dimension(200, 30));
		randomButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				type = "random";
				randomParams();
			}
		});
		selectNetworkPanel.add(randomButton);
		
		JButton barabasiButton = new JButton("Barabasi network");
		barabasiButton.setPreferredSize(new Dimension(200, 30));
		barabasiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				type = "barabasi";
				barabasiParams();
			}
		});
		selectNetworkPanel.add(barabasiButton);

		selectNetworkPanel.setVisible(true);
		window.add(selectNetworkPanel, BorderLayout.SOUTH);
		
		
		this.setLocationRelativeTo(null);
		this.setContentPane(window);
		this.setVisible(true);
	}
	
	public void randomParams() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 400);
		JPanel window = new JPanel(new BorderLayout());
		
		// Default title panel available in every view.
			JPanel titlePanel = new JPanel();
			titlePanel.setPreferredSize(new Dimension(600, 200));
			
			ImageIcon UCMlogo = new ImageIcon(new ImageIcon("UCM logo.png").getImage().getScaledInstance(100, 114, Image.SCALE_DEFAULT));
			JLabel UCMlogoLabel = new JLabel("", UCMlogo, JLabel.CENTER);
			UCMlogoLabel.setIcon(UCMlogo);
			titlePanel.add(UCMlogoLabel, BorderLayout.WEST);
			
			JLabel titleText = new JLabel("  NETWORK GENERATOR  "); 
			titleText.setPreferredSize(new Dimension(400, 200));
			titleText.setFont(new Font("Helvetica", Font.PLAIN, 28));
			titlePanel.add(titleText, BorderLayout.CENTER);
			
			titlePanel.setVisible(true);
			window.add(titlePanel, BorderLayout.NORTH);
		
		//	
			
		numInitNodes = 1000;
			
		bondsProb = 10;	
			
		JPanel randomParamsPanel = new JPanel();
		randomParamsPanel.setPreferredSize(new Dimension(600, 140));
		
		JPanel selectNodesPanel = new JPanel();
		selectNodesPanel.setPreferredSize(new Dimension(600, 60));

		JTextField nodesTextBox = new JTextField();
		nodesTextBox.setPreferredSize(new Dimension(80, 30));
		selectNodesPanel.add(nodesTextBox, BorderLayout.CENTER);

		JButton setNodesButton = new JButton("Set initial nodes");
		setNodesButton.setPreferredSize(new Dimension(160, 30));
		setNodesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// If numInitNodes is not a number, a pop-up window asks us for a correct input.
				
				if (nodesTextBox.getText() != null) {
					numInitNodes = Integer.parseInt(nodesTextBox.getText());
				}
				else {
					// ERROR window appears and asks again for a correct input.
					error = true;
					
					numInitNodes = null;
				}
			}
		});
		selectNodesPanel.add(setNodesButton);

		selectNodesPanel.setVisible(true);
		randomParamsPanel.add(selectNodesPanel, BorderLayout.NORTH);
		
		JPanel selectBondsProbPanel = new JPanel();
		selectBondsProbPanel.setPreferredSize(new Dimension(600, 60));
		
		JTextField bondsProbTextBox = new JTextField();
		bondsProbTextBox.setPreferredSize(new Dimension(80, 30));
		selectBondsProbPanel.add(bondsProbTextBox, BorderLayout.CENTER);

		JButton setBondsProbButton = new JButton("Set probability");
		setBondsProbButton.setPreferredSize(new Dimension(160, 30));
		setBondsProbButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// If bondsProb is not a number, a pop-up window asks us for a correct input.
				
				if (bondsProbTextBox.getText() != null) {
					bondsProb = Integer.parseInt(bondsProbTextBox.getText());
				}
				else {
					// ERROR window appears and asks again for a correct input.
					error = true;
					
					bondsProb = null;
				}
			}
		});
		selectBondsProbPanel.add(setBondsProbButton);

		selectBondsProbPanel.setVisible(true);
		randomParamsPanel.add(selectBondsProbPanel, BorderLayout.SOUTH);
		
		window.add(randomParamsPanel, BorderLayout.CENTER);
		
		JPanel generatePanel = new JPanel();
		generatePanel.setPreferredSize(new Dimension(600, 60));
		
		JButton generateButton = new JButton("GENERATE");
		generateButton.setSize(80, 30);
		generateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Graph<Integer> igraph = new Graph<Integer>(); // Create a graph
				
				for (int i = 0; i < numInitNodes; i++) { // add 100 nodes (the "object" used for the node is an integer, so nodes will be: 0, 1, 2, 3, ...
					igraph.nodeInsert(i);
				}
				
				List<Integer> tmpNodes = new ArrayList<Integer>(); // Keep a list of nodes already used
				for (Integer n1 : igraph.getNodeList()) { // For each node...
					for (Integer n2 : igraph.getNodeList()) { // For each node again...
						if (!tmpNodes.contains(n2)) { // If we haven't used this node yet
							if (Math.random() < (bondsProb / 100.0)) { // 10% probability
								igraph.nodeConnect(n1, n2); // Add a connection between the two nodes
							}
						}
					}
					tmpNodes.add(n1); // We have already used this node, lets add it here.
				}
				
				networkResults(igraph, error);
				/*
				 * tmpNodes is there to avoid adding a connection between 10 and 20, and then adding another connection between 20 and 10.
				 * To see what happens when tmpNodes is not used, comment out the line "tmpNodes.add(n1);".
				 */
			}
		});
		generatePanel.add(generateButton);

		generatePanel.setVisible(true);
		window.add(generatePanel, BorderLayout.SOUTH);
		
		this.setLocationRelativeTo(null);
		this.setContentPane(window);
		this.setVisible(true);
	}
	
	public void barabasiParams() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 400);
		JPanel window = new JPanel(new BorderLayout());
		
		// Default title panel available in every view.
			JPanel titlePanel = new JPanel();
			titlePanel.setPreferredSize(new Dimension(600, 200));
			
			ImageIcon UCMlogo = new ImageIcon(new ImageIcon("UCM logo.png").getImage().getScaledInstance(100, 114, Image.SCALE_DEFAULT));
			JLabel UCMlogoLabel = new JLabel("", UCMlogo, JLabel.CENTER);
			UCMlogoLabel.setIcon(UCMlogo);
			titlePanel.add(UCMlogoLabel, BorderLayout.WEST);
			
			JLabel titleText = new JLabel("  NETWORK GENERATOR  "); 
			titleText.setPreferredSize(new Dimension(400, 200));
			titleText.setFont(new Font("Helvetica", Font.PLAIN, 28));
			titlePanel.add(titleText, BorderLayout.CENTER);
			
			titlePanel.setVisible(true);
			window.add(titlePanel, BorderLayout.NORTH);

		//
		
		this.setLocationRelativeTo(null);
		this.setContentPane(window);
		this.setVisible(true);
		
	}
	
	public void networkResults(Graph<Integer> results, Boolean error) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 400);
		JPanel window = new JPanel(new BorderLayout());
		
		// Default title panel available in every view.
			JPanel titlePanel = new JPanel();
			titlePanel.setPreferredSize(new Dimension(600, 200));
			
			ImageIcon UCMlogo = new ImageIcon(new ImageIcon("UCM logo.png").getImage().getScaledInstance(100, 114, Image.SCALE_DEFAULT));
			JLabel UCMlogoLabel = new JLabel("", UCMlogo, JLabel.CENTER);
			UCMlogoLabel.setIcon(UCMlogo);
			titlePanel.add(UCMlogoLabel, BorderLayout.WEST);
			
			JLabel titleText = new JLabel("  NETWORK GENERATOR  "); 
			titleText.setPreferredSize(new Dimension(400, 200));
			titleText.setFont(new Font("Helvetica", Font.PLAIN, 28));
			titlePanel.add(titleText, BorderLayout.CENTER);
			
			titlePanel.setVisible(true);
			window.add(titlePanel, BorderLayout.NORTH);
		
		//
		
		JPanel leftMarginPanel = new JPanel();	
		leftMarginPanel.setPreferredSize(new Dimension(80, 100));
		
		JPanel rightMarginPanel = new JPanel();	
		rightMarginPanel.setPreferredSize(new Dimension(80, 100));
			
		JPanel textAreaPanel = new JPanel(new BorderLayout(0, 0));
		textAreaPanel.setPreferredSize(new Dimension(400, 200));
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textAreaPanel.add(new JScrollPane(textArea));
		
		window.add(leftMarginPanel, BorderLayout.WEST);
		window.add(textAreaPanel, BorderLayout.CENTER);
		window.add(rightMarginPanel, BorderLayout.EAST);
		
		if (error) {
			textArea.append("ERROR: the network cannot be generated. \n\n");
		}
		else {
			textArea.append(results.toString() + "\n\n");
			
			textArea.append("Network succesfully created. Done. \n\n");
		}
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setPreferredSize(new Dimension(600, 40));
		
		JButton backButton = new JButton("Back");
		backButton.setPreferredSize(new Dimension(120, 30));
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initGUI();
			}
		});
		buttonsPanel.add(backButton);
		
		JButton saveButton = new JButton("Save");
		saveButton.setPreferredSize(new Dimension(120, 30));
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PrintStream out;
				
				if (error) {
					saveButton.setEnabled(false);
				}
				
				try {
					if (type == "random") {
						out = new PrintStream(new FileOutputStream("results/" + type + "_" + numInitNodes + "_" + bondsProb + ".csv"));
						textArea.append("CSV file succesfully saved as " + type + "_" + numInitNodes + "_" + bondsProb + ".csv in the folder results. Done. \n\n");
					}
					else if (type == "barabasi") {
						out = new PrintStream(new FileOutputStream("results/" + type + "_" + numInitBonds + "_" + numSteps + ".csv"));
						textArea.append("CSV file succesfully saved as " + type + "_" + numInitBonds + "_" + numSteps + ".csv in the folder results. Done. \n\n");
					}
					else {
						out = null;
						System.out.println("Something went wrong...\n\n");
					}
					
					//
					
					if (out != null) {
						System.setOut(out);
						System.out.println(results.toCSVstring());
						
					}
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					// e1.printStackTrace();
				}
			}
		});
		buttonsPanel.add(saveButton);
		
		buttonsPanel.setVisible(true);
		window.add(buttonsPanel, BorderLayout.SOUTH);
		
		this.setLocationRelativeTo(null);
		this.setContentPane(window);
		this.setVisible(true);
	}
	
}
