package presentation.controller;

import org.json.JSONObject;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import model.service.CityLayerService;
import model.service.CityService;
import model.service.Intent;
import model.service.NLPManagement;
import presentation.client_management.ClientConnection;
import presentation.view.ImageView;
import presentation.view.MainView;


public class ImageController {

	private ImageView imageView;
	CityLayerService cityLayerService = new CityLayerService();
	CityService cityService = new CityService();
	private NLPManagement nlpManagement = new NLPManagement();
	
	public ImageController() {
		
	}
	
	public ImageController(ImageView imageView) {
		this.imageView = imageView;
		
		imageView.addShowCommandsListener(new ShowCommandsListener());
		imageView.addSendIntentListener(new ImageSendIntentListener());
		imageView.addMainPanelListener(new ImageBackToMainPanel());
		imageView.addMouseListener(new MouseControllerListener());
		
	}
	
	class MouseControllerListener implements MouseListener{
		
		public void mouseClicked(MouseEvent e) {
			PointerInfo a = MouseInfo.getPointerInfo();
	 		Point point = new Point(a.getLocation());
	 		SwingUtilities.convertPointFromScreen(point, e.getComponent());
	 		int x = (int) point.getX();
	 		int y = (int) point.getY();
	 		
	 		int cityPosX = x - 72;
	 		int cityPosY = y - 95;
	 		
	 		Socket socket = ClientConnection.getSocket();
			ObjectOutputStream outputStream= ClientConnection.getOutputStream();
			BufferedReader response = ClientConnection.getResponse();
		
			Intent intent = nlpManagement.createIntent("show the click");
			String header = "/GET/cityLayer/imagePath/position";

			try {
				
				JSONObject ob3 = new JSONObject();
				
				JSONObject intentJson = new JSONObject();
				intentJson.put("action", intent.getAction());
				intentJson.put("entity", intent.getEntity());
				
				JSONObject parameters = new JSONObject();
				
				parameters.put("intentObject", intentJson);
				
				parameters.put("cityPosX", new Integer(cityPosX));
				parameters.put("cityPosY", new Integer(cityPosY));
				StringWriter out = new StringWriter();
	     		parameters.write(out);	

				String body = out.toString();
				
				outputStream.writeObject(header);
				outputStream.writeObject(body);
				
				outputStream.flush();
				System.out.println("Client 1");
				
				String infos = response.readLine();
				
				if(infos.equals("No amenity")) {
					imageView.getAmenityInfo().setText(
			 				"Pos: (" + cityPosX + "," + cityPosY + ");  " 
			 				+ infos);
					
					imageView.getNeighbourhoodInfo().setText("");
				
				}
				
				else {

					JSONObject obj = new JSONObject(infos);
					String type = obj.getString("type");
					String amenityName = obj.getString("amenity");
					String neighbourhoodName = obj.getString("neighbourhood");
					float neighbourhoodRating = obj.getFloat("rating");
				
					imageView.getAmenityInfo().setText(
			 				"Pos: (" + cityPosX + "," + cityPosY + ");  " 
			 				+ "type: " +  type + " name " 
			 				+ amenityName );
			 		
			 		imageView.getNeighbourhoodInfo().setText(
			 				"Neighbourhood " + neighbourhoodName + 
			 				" has rating " + neighbourhoodRating);
				}
				
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
	 		
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	class ImageSendIntentListener implements ActionListener{
	
		public void actionPerformed(ActionEvent e) {

			Socket socket = ClientConnection.getSocket();
			ObjectOutputStream outputStream = ClientConnection.getOutputStream();
			BufferedReader response = ClientConnection.getResponse();
			
			String enteredIntent = imageView.getCommand().getText();
			Intent intent = nlpManagement.createIntent(enteredIntent);
			
			System.out.println("Action: " + intent.getAction() + " entity: " + intent.getEntity());
			
			String header = "";
			if(intent.equals(new Intent("neighbourhood", "display"))) {
				header = "/GET/cityLayer/imagePath/:2";
			}
			
			JSONObject intentJson = new JSONObject();
			intentJson.put("action", intent.getAction());
			intentJson.put("entity", intent.getEntity());
			
			JSONObject parameters = new JSONObject();
			
			parameters.put("intentObject", intentJson);
			
			StringWriter out = new StringWriter();
     		parameters.write(out);	

			String body = out.toString();
			
			try {
				outputStream.writeObject(header);
				outputStream.writeObject(body);
				outputStream.flush();
				
				String path = response.readLine();
				BufferedImage img = null;
				
				try {
				    img = ImageIO.read(new File(path));
				} catch (IOException e3) {
					System.out.println(e3.getMessage());
				}
				
				imageView.imageLabel.setIcon(new ImageIcon(img));
				
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		}
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
	        rowData[1] = "display the neighbourhoods";
	        model.addRow(rowData);
	      
	        JOptionPane.showMessageDialog(null, new JScrollPane(jTable));
		}
	}
	
	class ImageBackToMainPanel implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			MainView mainView = new MainView();
			MainController mainController = new MainController(mainView);
			
			mainView.setVisible(true);
			imageView.setVisible(false);
		}
	}

}