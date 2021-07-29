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

import model.service.CityService;
import model.service.Intent;
import model.service.NLPManagement;
import model.service.NeighbourhoodService;
import presentation.client_management.ClientConnection;
import presentation.controller.ContactController.ContactBackToMainPanel;
import presentation.controller.ContactController.ContactSendIntentListener;
import presentation.controller.ContactController.ShowCommandsListener;
import presentation.view.MainView;
import presentation.view.NeighbourhoodView;

public class NeighbourhoodController {


	private NLPManagement nlpManagement = new NLPManagement();
	private NeighbourhoodView neighbourhoodView;
	
	public NeighbourhoodController() {
		
	}
	
	public NeighbourhoodController(NeighbourhoodView neighbourhoodView) {
		this.neighbourhoodView = neighbourhoodView;

		neighbourhoodView.addShowCommandsListener(new ShowCommandsListener());
		neighbourhoodView.addSendIntentListener(new NeighbourhoodSendIntentListener());
		neighbourhoodView.addMainPanelListener(new NeighbourhoodBackToMainPanel());
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
	        rowData[1] = "evaluate the city";
	        model.addRow(rowData);
	        
	        rowData[0] = 2;
	        rowData[1] = "evaluate the neighbourhood";
	        model.addRow(rowData);
	        
	        rowData[0] = 3;
	        rowData[1] = "show the neighbourhoods";
	        model.addRow(rowData);
	     
	        JOptionPane.showMessageDialog(null, new JScrollPane(jTable));
	      
		}
			
	}
	
	class NeighbourhoodSendIntentListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			Socket socket = ClientConnection.getSocket();
			ObjectOutputStream outputStream = ClientConnection.getOutputStream();
			BufferedReader response = ClientConnection.getResponse();
			
			String enteredIntent = neighbourhoodView.getCommand().getText();
			Intent intent = nlpManagement.createIntent(enteredIntent);
			
			System.out.println("Action: " + intent.getAction() + " entity: " + intent.getEntity());
			
			int neighbourhoodId = -1;
			
			JSONObject intentJson = new JSONObject();
			intentJson.put("action", intent.getAction());
			intentJson.put("entity", intent.getEntity());
			
			JSONObject parameters = new JSONObject();
			
			parameters.put("intentObject", intentJson);
			
			
			if(!neighbourhoodView.getNeighbourhoodId().getText().isEmpty()) {
				neighbourhoodId = 
						Integer.parseInt(neighbourhoodView.getNeighbourhoodId().getText());
				
				parameters.put("neighbourhoodId", neighbourhoodId);
			}
			
			String requestType = "";
			String field = "";
			String operationById = "";
			
			if(intent.getAction() != null 
					&& intent.getAction().equalsIgnoreCase("show")) {
				requestType = "GET";
			}
			
			else if(intent.getAction() != null 
					&& intent.getAction().equalsIgnoreCase("evaluate")) {
				requestType = "GET";
				field = "/:id";
				operationById = "/rating";
			}

			StringBuilder constructRequest = new StringBuilder();
			constructRequest.append("/");
			constructRequest.append(requestType);
			constructRequest.append("/");
			constructRequest.append(intent.getEntity());
			constructRequest.append(field);
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
				neighbourhoodView.getNeighboudhoodInfos().setText(responseText);
				
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			neighbourhoodView.getNeighbourhoodId().setText("");
			neighbourhoodView.getCommand().setText("");
			
		}
			
	}

	class NeighbourhoodBackToMainPanel implements ActionListener{
	
		public void actionPerformed(ActionEvent e) {
			MainView mainView = new MainView();
			MainController mainController = new MainController(mainView);
		
			mainView.setVisible(true);
			neighbourhoodView.setVisible(false);
		}
		
	}
		
	
}
