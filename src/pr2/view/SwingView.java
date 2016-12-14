package pr2.view;

import pr2.controller.GeneratorController;
import pr2.model.NetworkResults;
import pr2.utils.Graph;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

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
	
	private static Integer numInitBonds = 1000;
	
	private static Integer numSteps = 4;
	
	private static Boolean error = false;
	
	// Model and controller objects
	
		private GeneratorController ctrl = new GeneratorController();
		
		private NetworkResults model = new NetworkResults();
	
	public SwingView() {	
		
		initWindow();
	}
	
	private void initWindow() {
		//ImageIcon icon = new ImageIcon("icon.png");
		//this.setIconImage(icon.getImage());

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 400);
		JPanel window = new JPanel(new BorderLayout());
		
		// Default title panel available in every view.
		showTitlePanel(window);
		
		JPanel selectNetworkPanel = new JPanel();
		selectNetworkPanel.setPreferredSize(new Dimension(600, 180));
		
		JButton randomButton = new JButton("Random network");
		randomButton.setPreferredSize(new Dimension(200, 30));
		randomButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				type = "random";
				paramsWindow();
			}
		});
		selectNetworkPanel.add(randomButton);
		
		JButton barabasiButton = new JButton("Barabasi network");
		barabasiButton.setPreferredSize(new Dimension(200, 30));
		barabasiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				type = "barabasi";
				paramsWindow();
			}
		});
		selectNetworkPanel.add(barabasiButton);

		selectNetworkPanel.setVisible(true);
		window.add(selectNetworkPanel, BorderLayout.SOUTH);
		
		this.setLocationRelativeTo(null);
		this.setContentPane(window);
		this.setVisible(true);
	}
	
	private void paramsWindow() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 400);
		JPanel window = new JPanel(new BorderLayout());
		
		// Default title panel available in every view.
		showTitlePanel(window);
		showParamsPanel(window);
		
		this.setLocationRelativeTo(null);
		this.setContentPane(window);
		this.setVisible(true);
	}
	
	private void resultsWindow(Graph<Integer> results, Boolean error) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 400);
		JPanel window = new JPanel(new BorderLayout());
		
		// Default title panel available in every view.
		showTitlePanel(window);
		
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
				initWindow();
			}
		});
		buttonsPanel.add(backButton);
		
		JButton saveButton = new JButton("Save");
		
		if (error) {
			saveButton.setEnabled(false);
		}
		
		saveButton.setPreferredSize(new Dimension(120, 30));
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PrintStream nodesOut, edgesOut;
				
				//
				
				if (type == "random") {
					nodesOut = model.saveNodesResults(type, numInitNodes, bondsProb);
					edgesOut = model.saveEdgesResults(type, numInitNodes, bondsProb);
					textArea.append("CSV file succesfully saved in the folder results. Done. \n\n");
				}
				else if (type == "barabasi") {
					nodesOut = model.saveNodesResults(type, numInitBonds, numSteps);
					edgesOut = model.saveEdgesResults(type, numInitBonds, numSteps);
					textArea.append("CSV file succesfully saved in the folder results. Done. \n\n");
				}
				else {
					nodesOut = null;
					edgesOut = null;
					System.out.println("Something went wrong...\n\n");
				}
				
				//
				
				if (nodesOut != null && edgesOut != null) {
					System.setOut(nodesOut);
					System.out.println(results.nodesToCSVstring());	
					
					System.setOut(edgesOut);
					System.out.println(results.edgesToCSVstring());	
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
	
	private void showTitlePanel(JPanel window) {
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
	}
	
	private void showParamsPanel(JPanel window) {
		JPanel paramsPanel = new JPanel();
		paramsPanel.setPreferredSize(new Dimension(600, 140));
		
		// Initial nodes / initial bonds
			JTextField param1TextBox = new JTextField();
			param1TextBox.setPreferredSize(new Dimension(120, 30));
			paramsPanel.add(param1TextBox, BorderLayout.NORTH);
		
		
		// Bonds probability / number of steps	
			JTextField param2TextBox = new JTextField();
			param2TextBox.setPreferredSize(new Dimension(120, 30));
			paramsPanel.add(param2TextBox, BorderLayout.SOUTH);
			
		window.add(paramsPanel, BorderLayout.CENTER);
		
		// Generates the network
		JPanel generatePanel = new JPanel();
		generatePanel.setPreferredSize(new Dimension(600, 60));
		
		JButton generateButton = new JButton("GENERATE");
		generateButton.setSize(80, 30);
		generateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Graph<Integer> igraph = new Graph<Integer>(); // Create a graph
				
				if (!param1TextBox.getText().isEmpty() || !param2TextBox.getText().isEmpty()) {
					if (type == "random") {
						numInitNodes = Integer.parseInt(param1TextBox.getText());
						bondsProb = Integer.parseInt(param2TextBox.getText());
					}
					else if (type == "barabasi") {
						numInitBonds = Integer.parseInt(param1TextBox.getText());
						numSteps = Integer.parseInt(param2TextBox.getText());
					}
				}
				
				//
				
				if (type == "random") {
					igraph = ctrl.generateRandomNetwork(numInitNodes, bondsProb);
				}
				else if (type == "barabasi") {
					igraph = ctrl.generateBarabasiNetwork(numInitBonds, numSteps);
					
				}
				else {
					System.out.println("Something went wrong...");
				}
				
				resultsWindow(igraph, error);
			}
		});
		generatePanel.add(generateButton);

		generatePanel.setVisible(true);
		window.add(generatePanel, BorderLayout.SOUTH);
	}
}