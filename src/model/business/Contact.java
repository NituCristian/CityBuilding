package model.business;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@Entity
@Table(name = "Contact")
public class Contact implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	@Column(name = "AmenityId", nullable = false)
	private int amenityId;
	
	@Column(name = "Phone", nullable = false, length = 255)
	private String phone;
	
	@Column(name = "Mail", nullable = false, length = 255)
	private String mail;
	
	public Contact() {
		
	}

	/**
	 * constructor
	 * @param amenityId the amenity id the contact corresponds
	 * @param phone the phone of the contact
	 * @param mail the mail of the contact
	 */
	public Contact(int amenityId, String phone, String mail) {
		this.amenityId = amenityId;
		this.phone = phone;
		this.mail = mail;
	}
	
	public Contact(String phone, String mail) {
		this.phone = phone;
		this.mail = mail;
	}

	public int getId() {
		return id;
	}
	

	public int getAmenityId() {
		return amenityId;
	}


	public String getPhone() {
		return phone;
	}

	public String getMail() {
		return mail;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setAmenityId(int amenityId) {
		this.amenityId = amenityId;
	}
	
	/**
	 * create a jtable of existing contacts
	 * @param contacts the contacts of the amenities from the neighbourhood
	 */
	public static void createTable(List<Contact> contacts) {
		JTable jTable1 = new javax.swing.JTable();

		jTable1.setModel(new javax.swing.table.DefaultTableModel(
				new Object [][] {

	            },
	            new String [] {
	                "Id", "AmenityId", "Phone", "Mail"
	            }
	     ));
		
		DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        Object rowData[] = new Object[4];
      
        for (Contact contact: contacts) {
        	rowData[0] = contact.getId();
        	rowData[1] = contact.getAmenityId();
        	rowData[2] = contact.getPhone();
        	rowData[3] = contact.getMail();
      
        	model.addRow(rowData);
        }
        
        JOptionPane.showMessageDialog(null, new JScrollPane(jTable1));
      
	}
	
	/**
	 * check whether a contact is valid
	 * @return true whether the contact is valid
	 */
	public boolean validateContact() {

		if (this.getPhone().length() > 15 || this.getMail().length() > 255) {
			 return false;
		}

		return true;
		
	}
}
	