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

public class PhotoController {

	private PhotoView photoView;
	private NLPManagement nlpManagement = new NLPManagement();
	
	public PhotoController() {
		
	}
	
	public PhotoController(PhotoView photoView) {
		this.photoView = photoView;
	
		photoView.addShowCommandsListener(new ShowCommandsListener());
		photoView.addSendIntentListener(new PhotoSendIntentListener());
		photoView.addMainPanelListener(new PhotoBackToMainPanel());
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
	        rowData[1] = "add a photo";
	        model.addRow(rowData);
	        
	        rowData[0] = 2;
	        rowData[1] = "update the photo";
	        model.addRow(rowData);
	        
	        rowData[0] = 3;
	        rowData[1] = "delete the photo";
	        model.addRow(rowData);
	        
	        rowData[0] = 4;
	        rowData[1] = "show the photos";
	        model.addRow(rowData);
	        
	        JOptionPane.showMessageDialog(null, new JScrollPane(jTable));
	      
		}
	
	}
	
	class PhotoSendIntentListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			Socket socket = ClientConnection.getSocket();
			ObjectOutputStream outputStream = ClientConnection.getOutputStream();
			BufferedReader response = ClientConnection.getResponse();
			
			String enteredIntent = photoView.getCommand().getText();
			Intent intent = nlpManagement.createIntent(enteredIntent);
			
			System.out.println("Action: " + intent.getAction() + " entity: " + intent.getEntity());
			
			String requestType = null;
			
			int photoId = -1;
			int amenityId = -1;
			int width = -1;
			int height = -1;
			String path = null;
			
			JSONObject intentJson = new JSONObject();
			intentJson.put("action", intent.getAction());
			intentJson.put("entity", intent.getEntity());
			
			JSONObject parameters = new JSONObject();
			
			parameters.put("intentObject", intentJson);
			
			if(!photoView.getPhotoId().getText().isEmpty()) {
				photoId = Integer.parseInt(photoView.getPhotoId().getText());
				parameters.put("photoId", new Integer(photoId));
			}
			
			if(!photoView.getAmenityId().getText().isEmpty()) {
				amenityId = Integer.parseInt(photoView.getAmenityId().getText());
				parameters.put("amenityId", new Integer(amenityId));
			}
			
			if(!photoView.getPhotoWidth().getText().isEmpty()) {
				width = Integer.parseInt(photoView.getPhotoWidth().getText());
				parameters.put("width", new Integer(width));
			}
			
			if(!photoView.getPhotoHeight().getText().isEmpty()) {
				height = Integer.parseInt(photoView.getPhotoHeight().getText());
				parameters.put("height", new Integer(height));
			}
			
			if(!photoView.getPhotoPath().getText().isEmpty()) {
				path = photoView.getPhotoPath().getText();
				parameters.put("path", path);
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

				photoView.getPhotoInfos().setText(responseText);
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
			photoView.getPhotoId().setText("");
			photoView.getAmenityId().setText("");
			photoView.getPhotoWidth().setText("");
			photoView.getPhotoHeight().setText("");
			photoView.getPhotoPath().setText("");
			photoView.getCommand().setText("");
		}
	}
	
	class PhotoBackToMainPanel implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			MainView mainView = new MainView();
			MainController mainController = new MainController(mainView);
			
			mainView.setVisible(true);
			photoView.setVisible(false);
		}
	}
	
	
	
}
