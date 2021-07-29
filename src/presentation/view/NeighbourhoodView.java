package presentation.view;

import java.awt.event.ActionListener;
import javax.swing.*;

public class NeighbourhoodView extends JFrame{

	private JTextField giveNeighbourhoodId = new JTextField(50);
	private JTextField giveInfos = new JTextField(50);
	private JTextField enterCommand = new JTextField(50);
	
	private JButton command = new JButton("Send intent");
	private JButton showCommands = new JButton("Show possible intents");
	private JButton main = new JButton("Back to main panel");
	
	public NeighbourhoodView() {
		JPanel panel1 = new JPanel();   
		JPanel panel2 = new JPanel();   
		JPanel panel3 = new JPanel();   
		JPanel panel4 = new JPanel();
		
		panel1.add(new JLabel("Neighbourhood id"));
		panel1.add(giveNeighbourhoodId);
		
		panel2.add(new JLabel("Infos"));
		panel2.add(giveInfos);
		
		panel3.add(new JLabel("Type intent"));
		panel3.add(enterCommand);
		
		panel4.add(command);
		panel4.add(showCommands);
		panel4.add(main);
	
		JPanel p = new JPanel();  
		p.add(panel1);   
		p.add(panel2); 
		p.add(panel3);
		p.add(panel4);
		
		p.setLayout(new BoxLayout(p, BoxLayout. Y_AXIS ));  
		this.setContentPane(p);
		
		this.pack();               
		this.setTitle("Neighbourhoods");  
		  
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public JTextField getNeighbourhoodId() {
		return giveNeighbourhoodId;
	}
	
	public void setNeighbourhoodId(JTextField neighbourhoodId) {
		giveNeighbourhoodId = neighbourhoodId;
	}
	
	public JTextField getNeighboudhoodInfos() {
		return giveInfos;
	}

	public void setNeighbourhoodInfos(JTextField giveInfos) {
		this.giveInfos = giveInfos;
	}
	
	public JTextField getCommand() {
		return enterCommand;
	}

	public void setCommand(JTextField enterCommand) {
		this.enterCommand = enterCommand;
	}
	
	public void addSendIntentListener(ActionListener listener) {
		command.addActionListener(listener);
	}
	
	public void addShowCommandsListener(ActionListener listener) {
		showCommands.addActionListener(listener);
	}
	
	public void addMainPanelListener(ActionListener listener) {
		main.addActionListener(listener);
	}

	public static void main(String args[]) {
		NeighbourhoodView neighbourhoodView = new NeighbourhoodView();
		neighbourhoodView.setVisible(true);
	}
	
}
