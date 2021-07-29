package presentation.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainView extends JFrame{

	private JButton cityDisplay = new JButton("CityDisplay");
	private JButton neighboudhood = new JButton("Neighbourhood");
	private JButton amenity = new JButton("Amenity");
	private JButton contact = new JButton("Contact");
	private JButton photo = new JButton("Photo");
	private JButton type = new JButton("Type");
	private JButton cityManagement = new JButton("City Management");
		
	public MainView() {
		cityDisplay.setPreferredSize(new Dimension(500, 50));
		neighboudhood.setPreferredSize(new Dimension(500, 50));
		amenity.setPreferredSize(new Dimension(500, 50));
		contact.setPreferredSize(new Dimension(500, 50));
		photo.setPreferredSize(new Dimension(500, 50));
		type.setPreferredSize(new Dimension(500, 50));
		cityManagement.setPreferredSize(new Dimension(500, 50));
		
		JPanel panel1 = new JPanel();   
		JPanel panel2 = new JPanel();   
		JPanel panel3 = new JPanel();  
		JPanel panel4 = new JPanel();   
		JPanel panel5 = new JPanel();   
		JPanel panel6 = new JPanel();  
		JPanel panel7 = new JPanel();  
		
		panel1.add(cityDisplay);
		panel2.add(neighboudhood);
		panel3.add(amenity);
		panel4.add(contact);
		panel5.add(photo);
		panel6.add(type);
		panel7.add(cityManagement);
		
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
		this.setTitle("Main");  
		  
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void addCityDisplayListener(ActionListener listener) {
		cityDisplay.addActionListener(listener);
	}
	
	public void addNeighbourhoodListener(ActionListener listener) {
		neighboudhood.addActionListener(listener);
	}
	
	public void addAmenityListener(ActionListener listener) {
		amenity.addActionListener(listener);
	}
	public void addPhotoDisplayListener(ActionListener listener) {
		photo.addActionListener(listener);
	}
	
	public void addContactListener(ActionListener listener) {
		contact.addActionListener(listener);
	}
	
	public void addTypeListener(ActionListener listener) {
		type.addActionListener(listener);
	}
	
	public void addCityManagementListener(ActionListener listener) {
		cityManagement.addActionListener(listener);
	}
	
}
