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
import presentation.view.AmenityView;
import presentation.view.ContactView;
import presentation.view.MainView;

public class AmenityController {

	private AmenityView amenityView;
	private NLPManagement nlpManagement = new NLPManagement();
	
	public AmenityController() {
		
	}
	
	public AmenityController(AmenityView amenityView) {
		this.amenityView = amenityView;
	
		amenityView.addShowCommandsListener(new ShowCommandsListener());
		amenityView.addSendIntentListener(new AmenitySendIntentListener());
		amenityView.addMainPanelListener(new AmenityBackToMainPanel());
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
	        rowData[1] = "build an amenity";
	        model.addRow(rowData);
	        
	        rowData[0] = 2;
	        rowData[1] = "update the amenity";
	        model.addRow(rowData);
	        
	        rowData[0] = 3;
	        rowData[1] = "delete the amenity";
	        model.addRow(rowData);
	        
	        rowData[0] = 4;
	        rowData[1] = "show the amenities";
	        model.addRow(rowData);
	        
	        rowData[0] = 5;
	        rowData[1] = "show the rules";
	        model.addRow(rowData);
	        
	        rowData[0] = 6;
	        rowData[1] = "propose a rule";
	        model.addRow(rowData);
	        
	        rowData[0] = 7;
	        rowData[1] = "unpropose the rule";
	        model.addRow(rowData);
	        
	        JOptionPane.showMessageDialog(null, new JScrollPane(jTable));
	      
		}
	
	}
	
	class AmenitySendIntentListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			Socket socket = ClientConnection.getSocket();
			ObjectOutputStream outputStream = ClientConnection.getOutputStream();
			BufferedReader response = ClientConnection.getResponse();
			
			//get the user command and create an intent
			String enteredIntent = amenityView.getCommand().getText();
			Intent intent = nlpManagement.createIntent(enteredIntent);
			
			String requestType = null; //the endpoint
			
			int amenityId = -1;
			int typeId = -1;
			int topLeftX = -1;
			int topLeftY = -1;
			int centerX = -1;
			int centerY = -1;
			float prestige = -1.0f;
			String address = null;
			boolean isAvailable = false;
			boolean permanentlyClosed = false;
			String name = null;
			String description = null;
			String ruleDescription = null;
			int ruleId = -1;

			JSONObject intentJson = new JSONObject();
			intentJson.put("action", intent.getAction());
			intentJson.put("entity", intent.getEntity());
			
			JSONObject parameters = new JSONObject();
			
			parameters.put("intentObject", intentJson);
			
			if(!amenityView.getAmenityId().getText().isEmpty()) {
				amenityId = Integer.parseInt(amenityView.getAmenityId().getText());
				parameters.put("amenityId", new Integer(amenityId));
			}
			
			if(!amenityView.getTypeId().getText().isEmpty()) {
				typeId = Integer.parseInt(amenityView.getTypeId().getText());
				parameters.put("typeId", new Integer(typeId));
			}
			
			if(!amenityView.getTopLeftX().getText().isEmpty()) {
				topLeftX = Integer.parseInt(amenityView.getTopLeftX().getText());
				parameters.put("topLeftX", new Integer(topLeftX));
			}
			
			if(!amenityView.getTopLeftX().getText().isEmpty()) {
				topLeftY = Integer.parseInt(amenityView.getTopLeftY().getText());
				parameters.put("topLeftY", new Integer(topLeftY));
			}
			
			if(!amenityView.getCenterX().getText().isEmpty()) {
				centerX = Integer.parseInt(amenityView.getCenterX().getText());
				parameters.put("centerX", new Integer(centerX));
			}
			
			if(!amenityView.getCenterY().getText().isEmpty()) {
				centerY = Integer.parseInt(amenityView.getCenterY().getText());
				parameters.put("centerY", new Integer(centerY));
			}
			
			if(!amenityView.getPrestige().getText().isEmpty()) {
				prestige = Float.parseFloat(amenityView.getPrestige().getText());
				parameters.put("prestige", new Float(prestige));
			}
			
			if(!amenityView.getAddress().getText().isEmpty()) {
				address = amenityView.getAddress().getText();
				parameters.put("address", address);
			}
			
			if(!amenityView.getIsAvailable().getText().isEmpty()) {
				isAvailable = Boolean.parseBoolean(amenityView.getIsAvailable().getText());
				parameters.put("isAvailable", new Boolean(isAvailable));
			}
			
			if(!amenityView.getPermanentlyClosed().getText().isEmpty()) {
				permanentlyClosed = Boolean.parseBoolean(amenityView.getPermanentlyClosed().getText());
				parameters.put("permanentlyClosed", new Boolean(permanentlyClosed));
			}
			
			if(!amenityView.getNameField().getText().isEmpty()) {
				name = amenityView.getNameField().getText();
				parameters.put("name", name);
			}
			
			if(!amenityView.getDescription().getText().isEmpty()) {
				description = amenityView.getDescription().getText();
				parameters.put("description", description);
			}
			
			if(!amenityView.getUnimplementedRuleDescription().getText().isEmpty()) {
				ruleDescription = amenityView.getUnimplementedRuleDescription().getText();
				parameters.put("ruleDescription", ruleDescription);
			}
			
			if(!amenityView.getUnimplementedRuleId().getText().isEmpty()) {
				ruleId = Integer.parseInt(amenityView.getUnimplementedRuleId().getText());
				parameters.put("ruleId", ruleId);
			}
			
			String operationById = "";	//used for queries made on a single row
			
			if(intent.getAction() != null 
					&& (intent.getAction().equalsIgnoreCase("build") 
							|| intent.getAction().equalsIgnoreCase("propose")) ) {
				requestType = "POST";
			}
			
			else if(intent.getAction() != null 
					&& intent.getAction().equalsIgnoreCase("update")) {
				requestType = "POST";
				operationById = "/:id";
			}
			
			else if(intent.getAction() != null 
					&& (intent.getAction().equalsIgnoreCase("delete")
							|| intent.getAction().equalsIgnoreCase("unpropose")) ) {
				requestType = "DELETE";
				operationById = "/:id";
			}
			
			else if(intent.getAction() != null 
					&& intent.getAction().equalsIgnoreCase("show")) {
				requestType = "GET";
			}
		
			//construct the header of the request which has the endpoint, entity and an id for update and delete
			StringBuilder constructRequest = new StringBuilder();
			constructRequest.append("/");
			constructRequest.append(requestType);
			constructRequest.append("/");
			constructRequest.append(intent.getEntity());
			constructRequest.append(operationById);
			
			String header = constructRequest.toString();
	
			try {
				StringWriter out = new StringWriter();
				parameters.writeJSONString(out);	

				String body = out.toString();
				
				//send the header and the body of the request
				outputStream.writeObject(header);
				outputStream.writeObject(body);
		
				outputStream.flush();
				
				String responseText = response.readLine();
				amenityView.getInfo().setText(responseText);
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			amenityView.getCommand().setText("");
			amenityView.getAmenityId().setText("");
			amenityView.getNeighbouhoodId().setText("");
			amenityView.getTypeId().setText("");
			amenityView.getTopLeftX().setText("");
			amenityView.getTopLeftY().setText("");
			amenityView.getCenterX().setText("");
			amenityView.getCenterY().setText("");
			amenityView.getPrestige().setText("");
			amenityView.getAddress().setText("");
			amenityView.getIsAvailable().setText("");
			amenityView.getPermanentlyClosed().setText("");
			amenityView.getNameField().setText("");
			amenityView.getDescription().setText("");
			amenityView.getUnimplementedRuleDescription().setText("");
			amenityView.getUnimplementedRuleId().setText("");
			
		}
	}
	
	class AmenityBackToMainPanel implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			MainView mainView = new MainView();
			MainController mainController = new MainController(mainView);
			
			mainView.setVisible(true);
			amenityView.setVisible(false);
		}
	}
	
	
	
}
