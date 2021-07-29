package model.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONObject;

import model.business.Photo;
import model.service.PhotoService;

public class PhotoProcessing {

	private PhotoService photoService = new PhotoService();

	/**
	 * insert a new photo given the fields send by the client
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void photoInsertProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {
		
		System.out.println("Server 2");
		
		String photoFields = (String) inputStream.readObject();
		
		JSONObject obj = new JSONObject(photoFields);
		
		int amenityId = obj.getInt("amenityId");
		int width = obj.getInt("width");
		int height = obj.getInt("height");
		String path = obj.getString("path");
		
		Photo photo = new Photo(amenityId, width, height, path);
		
		if (photoService.createPhoto(photo)) {
			output.println("The photo was added successfully");
		}
		
		else {
			output.println("Failed to add photo");
		}
	}
	
	/**
	 * update the path of an existing photo specified by the client
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void photoUpdateProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {
		
		String photoFields = (String) inputStream.readObject();
		
		JSONObject obj = new JSONObject(photoFields);
		
		int photoId = obj.getInt("photoId");
		String path = obj.getString("path");
		
		if (photoService.updatePhoto(photoId, path)) {
			output.println("The photo path was updated successfully");
		}
		
		else {
			output.println("Failed to update photo path");
		}
	}
	
	
	/**
	 * delete a photo 
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void photoDeleteProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {
		

		String photoFields = (String) inputStream.readObject();
		
		JSONObject obj = new JSONObject(photoFields);
	
		int photoId = obj.getInt("photoId");
				
		if (photoService.deletePhoto(photoId)) {
			output.println("The photo was deleted successfully");
		}
	
		else {
			output.println("Photo does not exist");
		}
	}	
	
	/**
	 * create a JTable containing all the photos
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void showPhotosProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {

		String photoFields = (String) inputStream.readObject();
		
		photoService.drawTable();
		output.println("Photos are here!");
		
	}
}
