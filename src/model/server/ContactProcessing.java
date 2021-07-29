package model.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONObject;

import model.business.Contact;
import model.service.ContactService;

public class ContactProcessing {

	private ContactService contactService = new ContactService();
	
	/**
	 * insert a new contact for an amenity if the fields are valid
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void contactInsertProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {
		
		String contactFields = (String) inputStream.readObject();
		
		JSONObject obj = new JSONObject(contactFields);
		
		int amenityId = obj.getInt("amenityId");
		String phone = obj.getString("phone");
		String mail = obj.getString("mail");
		
		Contact contact = new Contact(amenityId, phone, mail);
		
		if (contactService.createContact(contact)) {
			output.println("The contact was added successfully");
		}
		
		else {
			output.println("Failed to add contact");
		}
	}
	
	/**
	 * update the specified fields for a contact
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void contactUpdateProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {
		
		String contactFields = (String) inputStream.readObject();

		JSONObject obj = new JSONObject(contactFields);
		
		String phone = "";
		String mail = "";
		
		int contactId = obj.getInt("contactId");
		
		if (obj.has("phone")) {
			phone = obj.getString("phone");
		}
		 
		if (obj.has("mail")) {
			mail = obj.getString("mail");
		}
		
		Contact initialContact = contactService.getContactById(contactId);
		
		if (phone.equals("")) {
			phone = initialContact.getPhone();
		}
		
		if (mail.equals("")) {
			mail = initialContact.getMail();
		}
		
		Contact contact = new Contact(phone, mail);
		
		if (contactService.updateContact(contactId, contact)) {
			output.println("The contact was updated successfully");
		}
		
		else {
			output.println("Failed to update contact");
		}
	}
		
	/**
	 * delete a contact with a specified id
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void contactDeleteProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {
		
		String contactFields = (String) inputStream.readObject();

		JSONObject obj = new JSONObject(contactFields);
	
		int contactId = obj.getInt("contactId");
		
		if (contactService.deleteContact(contactId)) {
			output.println("The contact was deleted successfully");
		}
	
		else {
			output.println("Contact does not exist");
		}
	}
	
	
	/**
	 * draw a JTable containing the existing contacts
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void showContactsProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output
			) throws IOException, ClassNotFoundException {

		String contactFields = (String) inputStream.readObject();
		
		contactService.drawTable();
		output.println("Contacts are here!");
	}
	
}
