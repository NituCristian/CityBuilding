package presentation.view;

import java.awt.event.ActionListener;

import javax.swing.*;

public class ImageView extends JFrame{

	private JTextField amenityInfo = new JTextField(50);
	private JTextField neighbourhoodInfo = new JTextField(50);
	private JTextField enterCommand = new JTextField(50);
	
	private JButton command = new JButton("Send intent");
	private JButton showCommands = new JButton("Show possible intents");
	private JButton main = new JButton("Back to main panel");
	
	public ImageIcon image;
	public JLabel imageLabel = new JLabel();

	public ImageView(String path) {
		JPanel panel1 = new JPanel();   
		JPanel panel2 = new JPanel();   
		JPanel panel3 = new JPanel();   
		JPanel panel4 = new JPanel(); 
		JPanel panel5 = new JPanel(); 
		
		panel1.add(new JLabel("Amenity infos: "));
		panel1.add(amenityInfo);
		
		panel2.add(new JLabel("Neighbourhood infos: "));
		panel2.add(neighbourhoodInfo);
		 
	
		imageLabel.setIcon(new ImageIcon(path));
		imageLabel.setBounds(0, 0, 512, 512);
    	imageLabel.setVisible(true);
		panel3.add(imageLabel);
		
		panel4.add(new JLabel("Type intent"));
		panel4.add(enterCommand);
		
		panel5.add(command);
		panel5.add(showCommands);
		panel5.add(main);
	
		JPanel p = new JPanel();  
		p.add(panel1);   
		p.add(panel2); 
		p.add(panel3);
		p.add(panel4);
		p.add(panel5);
		
		p.setLayout(new BoxLayout(p, BoxLayout. Y_AXIS ));  
		this.setContentPane(p);
		
		this.pack();               
		this.setTitle("Image");  
		this.setResizable(false);
		  
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	public JTextField getAmenityInfo() {
		return amenityInfo;
	}
	
	public JTextField getNeighbourhoodInfo() {
		return neighbourhoodInfo;
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

}
