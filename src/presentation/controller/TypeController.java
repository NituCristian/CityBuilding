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
import presentation.view.PhotoView;
import presentation.view.TypeView;

public class TypeController {

	private TypeView typeView;
	private NLPManagement nlpManagement = new NLPManagement();
	
	public TypeController() {
		
	}
	
	public TypeController(TypeView typeView) {
		this.typeView = typeView;
	
		typeView.addShowCommandsListener(new ShowCommandsListener());
		typeView.addSendIntentListener(new TypeSendIntentListener());
		typeView.addMainPanelListener(new TypeBackToMainPanel());
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
	        rowData[1] = "add a type";
	        model.addRow(rowData);
	        
	        rowData[0] = 2;
	        rowData[1] = "update the type";
	        model.addRow(rowData);
	        
	        rowData[0] = 3;
	        rowData[1] = "delete the type";
	        model.addRow(rowData);
	        
	        rowData[0] = 4;
	        rowData[1] = "show the types";
	        model.addRow(rowData);
	        
	        JOptionPane.showMessageDialog(null, new JScrollPane(jTable));
	      
		}
	
	}
	
	class TypeSendIntentListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			Socket socket = ClientConnection.getSocket();
			ObjectOutputStream outputStream = ClientConnection.getOutputStream();
			BufferedReader response = ClientConnection.getResponse();
			
			String enteredIntent = typeView.getCommand().getText();
			Intent intent = nlpManagement.createIntent(enteredIntent);
			
			System.out.println("Action: " + intent.getAction() + " entity: " + intent.getEntity());
			
			String requestType = null;
			
			int typeId = -1;
			String name = null;
			String description = null;
			String icon = null;
			
			JSONObject intentJson = new JSONObject();
			intentJson.put("action", intent.getAction());
			intentJson.put("entity", intent.getEntity());
			
			JSONObject parameters = new JSONObject();
			
			parameters.put("intentObject", intentJson);
			
			if(!typeView.getTypeId().getText().isEmpty()) {
				typeId = Integer.parseInt(typeView.getTypeId().getText());
				parameters.put("typeId", new Integer(typeId));
			}
			
			if(!typeView.getGiveName().getText().isEmpty()) {
				name = typeView.getGiveName().getText();
				parameters.put("name", name);
			}
			
			if(!typeView.getDescription().getText().isEmpty()) {
				description = typeView.getDescription().getText();
				parameters.put("description", description);
			}
			
			if(!typeView.getIcon().getText().isEmpty()) {
				icon = typeView.getIcon().getText();
				parameters.put("icon", icon);
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
			
			try {
				StringWriter out = new StringWriter();
				parameters.writeJSONString(out);	

				String body = out.toString();
				
				outputStream.writeObject(header);
				outputStream.writeObject(body);
		
				outputStream.flush();
				
				String responseText = response.readLine();
				typeView.getInfos().setText(responseText);
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			typeView.getTypeId().setText("");
			typeView.getGiveName().setText("");
			typeView.getDescription().setText("");
			typeView.getIcon().setText("");
			typeView.getCommand().setText("");
		}
	}
	
	class TypeBackToMainPanel implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			MainView mainView = new MainView();
			MainController mainController = new MainController(mainView);
			
			mainView.setVisible(true);
			typeView.setVisible(false);
		}
	}
	
	
	
}
