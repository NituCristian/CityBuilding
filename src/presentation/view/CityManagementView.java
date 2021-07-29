package presentation.view;

import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CityManagementView extends JFrame{

	private JTextField complaintId = new JTextField(50);
	private JTextField complaintDescription = new JTextField(50);	
	private JTextField infos = new JTextField(50);	
	private JTextField placeName = new JTextField(50);
	private JTextField enterCommand = new JTextField(50);

	private JButton command = new JButton("Send intent");
	private JButton showCommands = new JButton("Show possible intents");
	private JButton main = new JButton("Back to main panel");
	
	public CityManagementView() {
		JPanel panel1 = new JPanel();   
		JPanel panel2 = new JPanel();   
		JPanel panel3 = new JPanel();   
		JPanel panel4 = new JPanel(); 
		JPanel panel5 = new JPanel();  
		JPanel panel6 = new JPanel();  
			
    	panel1.add(new JLabel("Complaint id"));
    	panel1.add(complaintId);
    	
    	panel2.add(new JLabel("Complaint description"));
    	panel2.add(complaintDescription);
    	
    	panel3.add(new JLabel("Enter amenity..."));
    	panel3.add(placeName);
    	
    	panel4.add(new JLabel("Infos"));
    	panel4.add(infos);
    	
    	panel5.add(new JLabel("Type intent"));
		panel5.add(enterCommand);
    	
		panel6.add(command);
		panel6.add(showCommands);
		panel6.add(main); 	
    	
		JPanel p = new JPanel();  
		p.add(panel1);   
		p.add(panel2); 
		p.add(panel3);
		p.add(panel4);
		p.add(panel5); 
		p.add(panel6); 
		
		p.setLayout(new BoxLayout(p, BoxLayout. Y_AXIS ));  
		this.setContentPane(p);
		this.pack();               
		this.setTitle("City management");  
		  
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public JTextField getComplaintId() {
		return complaintId;
	}
	
	public void setComplaintId(JTextField complaintId) {
		this.complaintId = complaintId;
	}
	
	public JTextField getComplaintDescription() {
		return complaintDescription;
	}
	
	public void setComplaintDescription(JTextField complaintDescription) {
		this.complaintDescription = complaintDescription;
	}
	
	public JTextField getPlaceName() {
		return placeName;
	}
	
	public void setPlaceName(JTextField placeName) {
		this.placeName = placeName;
	}
	
	public JTextField getInfos() {
		return infos;
	}
	
	public void setInfos(JTextField infos) {
		this.infos = infos;
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
		CityManagementView cityManagementView = new CityManagementView();
		cityManagementView.setVisible(true);
	}
}
