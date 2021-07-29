package presentation.view;


import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ContactView extends JFrame{

	private JTextField giveContactId = new JTextField(50);
	private JTextField giveAmenityId = new JTextField(50);
	private JTextField givePhone = new JTextField(50);
	private JTextField giveMail = new JTextField(50);
	private JTextField giveInfos = new JTextField(50);
	private JTextField enterCommand = new JTextField(50);
	
	private JButton command = new JButton("Send intent");
	private JButton showCommands = new JButton("Show possible intents");
	private JButton main = new JButton("Back to main panel");
	
	public ContactView() {
		JPanel panel1 = new JPanel();   
		JPanel panel2 = new JPanel();   
		JPanel panel3 = new JPanel();   
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();
		JPanel panel6 = new JPanel();
		JPanel panel7 = new JPanel();
		
		panel1.add(new JLabel("Contact id"));
		panel1.add(giveContactId);
		
		panel2.add(new JLabel("Amenity id"));
		panel2.add(giveAmenityId);
		
		panel3.add(new JLabel("Phone"));
		panel3.add(givePhone);
		
		panel4.add(new JLabel("Mail"));
		panel4.add(giveMail);
		
		panel5.add(new JLabel("Infos"));
		panel5.add(giveInfos);
		
		panel6.add(new JLabel("Type intent"));
		panel6.add(enterCommand);
		
		panel7.add(command);
		panel7.add(showCommands);
		panel7.add(main);
	
		JPanel p = new JPanel();  
		p.add(panel1);   
		p.add(panel2); 
		p.add(panel3);
		p.add(panel4);
		p.add(panel5);
		p.add(panel6);
		p.add(panel7);
		
		p.setLayout(new BoxLayout(p, BoxLayout. Y_AXIS ));  
		this.setContentPane(p);
		
		this.pack();               
		this.setTitle("Contact");  
		  
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public JTextField getContactId() {
		return giveContactId;
	}

	public void setContactId(JTextField giveContactId) {
		this.giveContactId = giveContactId;
	}
	
	public JTextField getAmenityId() {
		return giveAmenityId;
	}

	public void setAmenityId(JTextField giveAmenityId) {
		this.giveAmenityId = giveAmenityId;
	}
	
	public JTextField getContactPhone() {
		return givePhone;
	}
	
	public void setContactPhone(JTextField givePhone) {
		this.givePhone = givePhone;
	}
	
	public JTextField getContactMail() {
		return giveMail;
	}

	public void setContactMail(JTextField giveMail) {
		this.giveMail = giveMail;
	}
	
	public JTextField getContactInfos() {
		return giveInfos;
	}

	public void setContactInfos(JTextField giveInfos) {
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


}
