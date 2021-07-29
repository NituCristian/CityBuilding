package model.service;

import java.util.List;

import data_access_layer.ContactDAO;
import model.business.Contact;

public class ContactService {

	private ContactDAO contactDAO = new ContactDAO();
	
	/**
	 * create a new contact
	 * @param contact the contact object we would like to create
	 * @return true if the contact is valid
	 */
	public boolean createContact(Contact contact) {
		
		if(contact.validateContact()) {
			contactDAO.create(contact);
			return true;
		}
		
		return false;
	}
	
	/**
	 * delete a contact with an id
	 * @param contactId the id of the contact we would like to delete
	 * @return true if we manage to delete
	 */
	public boolean deleteContact(int contactId) {
		Contact contact = contactDAO.findById(contactId);
		
		if(contact == null) {
			return false;
		}
		
		else {
			contactDAO.deleteById(contactId);
			return true;
		}
		
	}
	
	/**
	 * create a JTable of existing contacts
	 */
	public void drawTable() {
		List<Contact> contacts = contactDAO.findAll();
		Contact.createTable(contacts);
	}
	
	/**
	 * get a contact with a specified id
	 * @param contactId the id of the contact
	 * @return an object of type contact
	 */
	public Contact getContactById(int contactId) {
		return contactDAO.findById(contactId);
	}
	
	/**
	 * update a contact
	 * @param contactId the id of the contact we would like to update
	 * @param contact the contact we would like to update
	 * @return true if we manage to update the contact
	 */
	public boolean updateContact(int contactId, Contact contact) {
	
		if(contact.validateContact()) {
			contactDAO.update(contactId, contact);
			return true;
		}
		
		return false;
	
	}
	
	
}
