package presentation.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.json.simple.JSONObject;

import model.service.Intent;
import model.service.NLPManagement;
import presentation.client_management.ClientConnection;
import presentation.view.ContactView;
import presentation.view.MainView;

public class ContactController {

	private ContactView contactView;
	private NLPManagement nlpManagement = new NLPManagement();
	
	public ContactController() {
		
	}
	
	public ContactController(ContactView contactView) {
		this.contactView = contactView;
	
		contactView.addShowCommandsListener(new ShowCommandsListener());
		contactView.addSendIntentListener(new ContactSendIntentListener());
		contactView.addMainPanelListener(new ContactBackToMainPanel());
	}

	class ShowCommandsListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			JTable jTable = new JTable();

			jTable.setModel(new DefaultTableModel(
					new Object [][] {

		            },
		            new String [] {
		                "Id", "Description"
		            }
		     ));
			
			DefaultTableModel model = (DefaultTableModel) jTable.getModel();
	        Object rowData[] = new Object[2];
	        
	        rowData[0] = 1;
	        rowData[1] = "add a contact";
	        model.addRow(rowData);
	        
	        rowData[0] = 2;
	        rowData[1] = "update the contact";
	        model.addRow(rowData);
	        
	        rowData[0] = 3;
	        rowData[1] = "delete the contact";
	        model.addRow(rowData);
	        
	        rowData[0] = 4;
	        rowData[1] = "show the contacts";
	        model.addRow(rowData);
	        
	        JOptionPane.showMessageDialog(null, new JScrollPane(jTable));
	      
		}
	
	}
	
	class ContactSendIntentListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			Socket socket = ClientConnection.getSocket();
			ObjectOutputStream outputStream = ClientConnection.getOutputStream();
			BufferedReader response = ClientConnection.getResponse();
			
			String enteredIntent = contactView.getCommand().getText();
			Intent intent = nlpManagement.createIntent(enteredIntent);
			
			System.out.println("Action: " + intent.getAction() + " entity: " + intent.getEntity());
			
			String requestType = null;
			
			int contactId = -1;
			int amenityId = -1;
			String phone = null;
			String mail = null;
			
			JSONObject intentJson = new JSONObject();
			intentJson.put("action", intent.getAction());
			intentJson.put("entity", intent.getEntity());
			
			JSONObject parameters = new JSONObject();
			
			parameters.put("intentObject", intentJson);
			
			if(!contactView.getContactId().getText().isEmpty()) {
				contactId = Integer.parseInt(contactView.getContactId().getText());
				parameters.put("contactId", new Integer(contactId));
			}
			
			if(!contactView.getAmenityId().getText().isEmpty()) {
				amenityId = Integer.parseInt(contactView.getAmenityId().getText());
				parameters.put("amenityId", new Integer(amenityId));
			}
			
			if(!contactView.getContactPhone().getText().isEmpty()) {
				phone = contactView.getContactPhone().getText();
				parameters.put("phone", phone);
			}
			
			if(!contactView.getContactMail().getText().isEmpty()) {
				mail = contactView.getContactMail().getText();
				parameters.put("mail", mail);
			}
			
			
			String operationById = "";
			
			if(intent.getAction() != null 
					&& intent.getAction().equalsIgnoreCase("add")) {
				requestType = "POST";
			}
			
			else if(intent.getAction() != null 
					&& intent.getAction().equalsIgnoreCase("update")) {
				requestType = "POST";
				operationById = "/:id";
			}
			
			else if(intent.getAction() != null 
					&& intent.getAction().equalsIgnoreCase("delete")) {
				requestType = "DELETE";
				operationById = "/:id";
			}
			
			else if(intent.getAction() != null 
					&& intent.getAction().equalsIgnoreCase("show")) {
				requestType = "GET";
			}
			
			
			StringBuilder constructRequest = new StringBuilder();
			constructRequest.append("/");
			constructRequest.append(requestType);
			constructRequest.append("/");
			constructRequest.append(intent.getEntity());
			constructRequest.append(operationById);
			
			String header = constructRequest.toString();
			
			StringWriter out = new StringWriter();
			try {
				parameters.writeJSONString(out);
			} catch (IOException e2) {
				System.out.println(e2.getMessage());
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}	

			String body = out.toString();
			
			try {
				outputStream.writeObject(header);
				outputStream.writeObject(body);
				
				outputStream.flush();
				
				String responseText = response.readLine();
				contactView.getContactInfos().setText(responseText);
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
			contactView.getContactId().setText("");
			contactView.getAmenityId().setText("");
			contactView.getContactPhone().setText("");
			contactView.getContactMail().setText("");
			contactView.getCommand().setText("");
		
		}
	}
	
	class ContactBackToMainPanel implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			MainView mainView = new MainView();
			MainController mainController = new MainController(mainView);
			
			mainView.setVisible(true);
			contactView.setVisible(false);
		}
	}
	
	
	
}
