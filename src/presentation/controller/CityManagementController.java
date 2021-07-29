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
import presentation.view.CityManagementView;
import presentation.view.ContactView;
import presentation.view.MainView;

public class CityManagementController {

	private CityManagementView cityManagementView;
	private NLPManagement nlpManagement = new NLPManagement();
	
	public CityManagementController() {
		
	}
	
	public CityManagementController(CityManagementView cityManagementView) {
		this.cityManagementView = cityManagementView;
	
		cityManagementView.addShowCommandsListener(new ShowCommandsListener());
		cityManagementView.addSendIntentListener(new CityManagementSendIntentListener());
		cityManagementView.addMainPanelListener(new CityManagementBackToMainPanel());
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
	        rowData[1] = "make a complaint";
	        model.addRow(rowData);
	        
	        rowData[0] = 2;
	        rowData[1] = "resolve the complaint";
	        model.addRow(rowData);
	        
	        rowData[0] = 3;
	        rowData[1] = "show the complaints";
	        model.addRow(rowData);
	        
	        rowData[0] = 4;
	        rowData[1] = "find the amenity";
	        model.addRow(rowData);
	        
	        rowData[0] = 5;
	        rowData[1] = "go to amenity";
	        model.addRow(rowData);
	        
	        JOptionPane.showMessageDialog(null, new JScrollPane(jTable));
	      
		}
	
	}
	
	class CityManagementSendIntentListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			Socket socket = ClientConnection.getSocket();
			ObjectOutputStream outputStream = ClientConnection.getOutputStream();
			BufferedReader response = ClientConnection.getResponse();
			
			String enteredIntent = cityManagementView.getCommand().getText();
			Intent intent = nlpManagement.createIntent(enteredIntent);
			
			String requestType = null;
			
			String description = null;
			int complaintId = -1;
			String placeName = null;
			
			JSONObject intentJson = new JSONObject();
			intentJson.put("action", intent.getAction());
			intentJson.put("entity", intent.getEntity());
			
			JSONObject parameters = new JSONObject();
			
			parameters.put("intentObject", intentJson);
			
			if(!cityManagementView.getComplaintDescription().getText().isEmpty()) {
				description = cityManagementView.getComplaintDescription().getText();
				parameters.put("complaintDescription", description);
			}
			
			if(!cityManagementView.getComplaintId().getText().isEmpty()) {
				complaintId = Integer.parseInt(cityManagementView.getComplaintId().getText());
				parameters.put("complaintId", complaintId);
			}
			
			if(!cityManagementView.getPlaceName().getText().isEmpty()) {
				placeName = cityManagementView.getPlaceName().getText();
				parameters.put("place", placeName);
			}

			String operationByName = "";
			String action = "";
			
			if(intent.getAction() != null 
					&& intent.getAction().equalsIgnoreCase("make")) {
				requestType = "POST";
			}
			
			
			else if(intent.getAction() != null 
					&& intent.getAction().equalsIgnoreCase("resolve")) {
				requestType = "DELETE";
				operationByName = "/:id";
			}
			
			else if(intent.getAction() != null 
					&& intent.getAction().equalsIgnoreCase("show")) {
				requestType = "GET";
			}
			
			else if(intent.getAction() != null 
					&& intent.getAction().equalsIgnoreCase("find")) {
				requestType = "GET";
				operationByName = "/:name";
				action = intent.getAction();
			}
			
			else if(intent.getAction() != null 
					&& intent.getAction().equalsIgnoreCase("go")) {
				requestType = "GET";
				operationByName = "/:name";
				action = intent.getAction();
			}
			
			
			StringBuilder constructRequest = new StringBuilder();
			constructRequest.append("/");
			constructRequest.append(requestType);
			constructRequest.append("/");
			constructRequest.append(intent.getEntity());
			constructRequest.append("/");
			constructRequest.append(action);	//just to differentiate the search and go operations
			constructRequest.append(operationByName);
			
			String header = constructRequest.toString();
	
			try {
				StringWriter out = new StringWriter();
				parameters.writeJSONString(out);	

				String body = out.toString();
				
				outputStream.writeObject(header);
				outputStream.writeObject(body);
		
				outputStream.flush();
				
				String responseText = response.readLine();
				cityManagementView.getInfos().setText(responseText);
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			cityManagementView.getComplaintDescription().setText("");
			cityManagementView.getComplaintId().setText("");
			cityManagementView.getPlaceName().setText("");
			cityManagementView.getCommand().setText("");
		}
	}
	
	class CityManagementBackToMainPanel implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			MainView mainView = new MainView();
			MainController mainController = new MainController(mainView);
			
			mainView.setVisible(true);
			cityManagementView.setVisible(false);
		}
	}
	
	
	
}
