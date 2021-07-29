package presentation.view;


import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class AmenityView extends JFrame{

	private JTextField giveAmenityId = new JTextField(50);
	private JTextField giveNeighbourhoodId = new JTextField(50);
	private JTextField giveTypeId = new JTextField(50);
	private JTextField topLeftX = new JTextField(50);
	private JTextField topLeftY = new JTextField(50);
	private JTextField centerX = new JTextField(50);
	private JTextField centerY = new JTextField(50);
	private JTextField prestige = new JTextField(50);
	private JTextField address = new JTextField(50);
	private JTextField isAvailable = new JTextField(50);
	private JTextField permanentlyClosed = new JTextField(50);
	private JTextField name = new JTextField(50);
	private JTextField description = new JTextField(50);
	private JTextField infos = new JTextField(50);
	private JTextField unimplementedRuleId = new JTextField(50);
	private JTextField unimplementedRuleDescription = new JTextField(50);
	private JTextField enterCommand = new JTextField(50);
	
	private JButton command = new JButton("Send intent");
	private JButton showCommands = new JButton("Show possible intents");
	private JButton main = new JButton("Back to main panel");
	
	
	public AmenityView() {
		JPanel panel1 = new JPanel();   
		JPanel panel2 = new JPanel();   
		JPanel panel3 = new JPanel();   
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();
		JPanel panel6 = new JPanel();
		JPanel panel7 = new JPanel();
		JPanel panel8 = new JPanel();   
		JPanel panel9 = new JPanel();
		JPanel panel10 = new JPanel();
		JPanel panel11 = new JPanel();
		JPanel panel12 = new JPanel();
		JPanel panel13 = new JPanel();
		JPanel panel14 = new JPanel();
		JPanel panel15 = new JPanel();
		JPanel panel16 = new JPanel();
		JPanel panel17 = new JPanel();
		JPanel panel18 = new JPanel();
		
		panel1.add(new JLabel("Amenity id"));
		panel1.add(giveAmenityId);
		
		panel2.add(new JLabel("Neighbourhood id"));
		panel2.add(giveNeighbourhoodId);
		
		panel3.add(new JLabel("Type id"));
		panel3.add(giveTypeId);
		
		panel4.add(new JLabel("Top Left X"));
		panel4.add(topLeftX);
		
		panel5.add(new JLabel("Top Left Y"));
		panel5.add(topLeftY);
		
		panel6.add(new JLabel("Center X"));
		panel6.add(centerX);
		
		panel7.add(new JLabel("Center Y"));
		panel7.add(centerY);
		
		panel8.add(new JLabel("Prestige"));
		panel8.add(prestige);
		
		panel9.add(new JLabel("Address"));
		panel9.add(address);
		
		panel10.add(new JLabel("Is available"));
		panel10.add(isAvailable);
	
		panel11.add(new JLabel("Permanently closed"));
		panel11.add(permanentlyClosed);
		
		panel12.add(new JLabel("Name"));
		panel12.add(name);
		
		panel13.add(new JLabel("Description"));
		panel13.add(description);
		
		panel14.add(new JLabel("Unimplemented rule id to be deleted"));
		panel14.add(unimplementedRuleId);
		
		panel15.add(new JLabel("Unimplemented rule description"));
		panel15.add(unimplementedRuleDescription);
		
		panel16.add(new JLabel("Infos"));
		panel16.add(infos);
		
		panel17.add(new JLabel("Type intent"));
		panel17.add(enterCommand);
		
		panel18.add(command);
		panel18.add(showCommands);
		panel18.add(main);
		
		JPanel p = new JPanel();  
		p.add(panel1);   
		p.add(panel2); 
		p.add(panel3);
		p.add(panel4);
		p.add(panel5);
		p.add(panel6);
		p.add(panel7);
		p.add(panel8);   
		p.add(panel9); 
		p.add(panel10);
		p.add(panel11);
		p.add(panel12);
		p.add(panel13);
		p.add(panel14);
		p.add(panel15);
		p.add(panel16);
		p.add(panel17);
		p.add(panel18);
		
		p.setLayout(new BoxLayout(p, BoxLayout. Y_AXIS ));  
		this.setContentPane(p);
		
		this.pack();               
		this.setTitle("Amenity");  
		  
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public JTextField getAmenityId() {
		return giveAmenityId;
	}

	public void setAmenityId(JTextField giveAmenityId) {
		this.giveAmenityId = giveAmenityId;
	}
	
	public JTextField getNeighbouhoodId() {
		return giveNeighbourhoodId;
	}

	public void setNeighbourhoodId(JTextField giveNeighbourhoodId) {
		this.giveNeighbourhoodId = giveNeighbourhoodId;
	}
	
	public JTextField getTypeId() {
		return giveTypeId;
	}

	public void setTypeId(JTextField typeId) {
		this.giveTypeId = typeId;
	}
	
	
	public JTextField getTopLeftX() {
		return topLeftX;
	}
	
	public void setTopLeftX(JTextField topLeftX) {
		this.topLeftX = topLeftX;
	}
	
	public JTextField getTopLeftY() {
		return topLeftY;
	}

	public void setTopLeftY(JTextField topLeftY) {
		this.topLeftY = topLeftY;
	}
	
	public JTextField getCenterX() {
		return centerX;
	}
	
	public void setCenterX(JTextField centerX) {
		this.centerX = centerX;
	}
	
	public JTextField getCenterY() {
		return centerY;
	}

	public void setCenterY(JTextField centerY) {
		this.centerY = centerY;
	}
	
	public JTextField getPrestige() {
		return prestige;
	}
	
	public void setPrestige(JTextField prestige) {
		this.prestige = prestige;
	}
	
	public JTextField getAddress() {
		return address;
	}
	
	public void setAddress(JTextField address) {
		this.address = address;
	}
	
	public JTextField getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(JTextField isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	public JTextField getPermanentlyClosed() {
		return permanentlyClosed;
	}
	
	public void setPermanentlyClosed(JTextField permanentlyClosed) {
		this.permanentlyClosed = permanentlyClosed;
	}
	
	public JTextField getNameField() {
		return name;
	}

	public void setName(JTextField name) {
		this.name = name;
	}
	
	public JTextField getDescription() {
		return description;
	}
	
	public void setDescription(JTextField description) {
		this.description = description;
	}
	
	public JTextField getInfo() {
		return infos;
	}
	
	public void setInfos(JTextField infos) {
		this.infos = infos;
	}
	
	public JTextField getUnimplementedRuleId() {
		return unimplementedRuleId;
	}
	
	public void setRuleId(JTextField ruleId) {
		this.unimplementedRuleId = ruleId;
	}
	
	public JTextField getUnimplementedRuleDescription() {
		return unimplementedRuleDescription;
	}
	
	public void setRuleDescription(JTextField ruleDescription) {
		this.unimplementedRuleDescription = ruleDescription;
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

