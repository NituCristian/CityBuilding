package presentation.view;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class PhotoView extends JFrame{

	private JTextField givePhotoId = new JTextField(50);
	private JTextField giveAmenityId = new JTextField(50);
	private JTextField giveWidth = new JTextField(50);
	private JTextField giveHeight = new JTextField(50);
	private JTextField givePath = new JTextField(50);
	private JTextField giveInfos = new JTextField(50);
	private JTextField enterCommand = new JTextField(50);
	
	private JButton command = new JButton("Send intent");
	private JButton showCommands = new JButton("Show possible intents");
	private JButton main = new JButton("Back to main panel");
	
	public PhotoView() {
		JPanel panel1 = new JPanel();   
		JPanel panel2 = new JPanel();   
		JPanel panel3 = new JPanel();   
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();
		JPanel panel6 = new JPanel();
		JPanel panel7 = new JPanel();
		JPanel panel8 = new JPanel();		
		
		panel1.add(new JLabel("Photo id"));
		panel1.add(givePhotoId);
		
		panel2.add(new JLabel("Amenity id"));
		panel2.add(giveAmenityId);
		
		panel3.add(new JLabel("Width"));
		panel3.add(giveWidth);
		
		panel4.add(new JLabel("Height"));
		panel4.add(giveHeight);
		
		panel5.add(new JLabel("Path"));
		panel5.add(givePath);
		
		panel6.add(new JLabel("Infos"));
		panel6.add(giveInfos);
		
		panel7.add(new JLabel("Type intent"));
		panel7.add(enterCommand);
		
		panel8.add(command);
		panel8.add(showCommands);
		panel8.add(main);
		
		JPanel p = new JPanel();  
		p.add(panel1);   
		p.add(panel2); 
		p.add(panel3);
		p.add(panel4);
		p.add(panel5);
		p.add(panel6);
		p.add(panel7);
		p.add(panel8);
		
		p.setLayout(new BoxLayout(p, BoxLayout. Y_AXIS ));  
		this.setContentPane(p);
		
		this.pack();               
		this.setTitle("Photo");  
		  
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public JTextField getPhotoId() {
		return givePhotoId;
	}

	public void setPhotoId(JTextField givePhotoId) {
		this.givePhotoId = givePhotoId;
	}
	
	public JTextField getAmenityId() {
		return giveAmenityId;
	}

	public void setAmenityId(JTextField giveAmenityId) {
		this.giveAmenityId = giveAmenityId;
	}
	
	public JTextField getPhotoWidth() {
		return giveWidth;
	}
	
	public void setWidth(JTextField giveWidth) {
		this.giveWidth = giveWidth;
	}
	
	public JTextField getPhotoHeight() {
		return giveHeight;
	}

	public void setHeight(JTextField giveHeight) {
		this.giveHeight = giveHeight;
	}
	
	public JTextField getPhotoPath() {
		return givePath;
	}
	
	public void setPath(JTextField givePath) {
		this.givePath = givePath;
	}
	
	public JTextField getPhotoInfos() {
		return giveInfos;
	}
	
	public void setPhotoInfos(JTextField giveInfos) {
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
