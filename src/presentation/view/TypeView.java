package presentation.view;

import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TypeView extends JFrame{

	private JTextField giveTypeId = new JTextField(50);
	private JTextField giveName = new JTextField(50);
	private JTextField giveDescription = new JTextField(50);
	private JTextField giveIcon = new JTextField(50);
	private JTextField giveInfos = new JTextField(50);
	private JTextField enterCommand = new JTextField(50);
	
	private JButton command = new JButton("Send intent");
	private JButton showCommands = new JButton("Show possible intents");
	private JButton main = new JButton("Back to main panel");
	
	public TypeView() {
		JPanel panel1 = new JPanel();   
		JPanel panel2 = new JPanel();   
		JPanel panel3 = new JPanel();   
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();
		JPanel panel6 = new JPanel();
		JPanel panel7 = new JPanel();
		
		panel1.add(new JLabel("Type id"));
		panel1.add(giveTypeId);
		
		panel2.add(new JLabel("Name"));
		panel2.add(giveName);
		
		panel3.add(new JLabel("Description"));
		panel3.add(giveDescription);
		
		panel4.add(new JLabel("Icon"));
		panel4.add(giveIcon);
		
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
		this.setTitle("Type");  
		  
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public JTextField getTypeId() {
		return giveTypeId;
	}

	public void setTypeId(JTextField giveTypeId) {
		this.giveTypeId = giveTypeId;
	}
	
	public JTextField getGiveName() {
		return giveName;
	}
	
	public void setName(JTextField giveName) {
		this.giveName = giveName;
	}

	public JTextField getDescription() {
		return giveDescription;
	}

	public void setDescription(JTextField giveDescription) {
		this.giveDescription = giveDescription;
	}
	
	public JTextField getIcon() {
		return giveIcon;
	}

	public void setIcon(JTextField giveIcon) {
		this.giveIcon = giveIcon;
	}

	public JTextField getInfos() {
		return giveInfos;
	}

	public void setInfos(JTextField giveInfos) {
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
