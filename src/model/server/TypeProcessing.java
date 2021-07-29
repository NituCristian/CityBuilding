package model.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONObject;

import model.business.Type;
import model.service.TypeService;

public class TypeProcessing {

	private TypeService typeService = new TypeService();
	
	/**
	 * create a type with received infos, if they are valid
	 * send a message to the client about the operation result
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void typeInsertProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {
		
		String typeFields = (String) inputStream.readObject();
		
		JSONObject obj = new JSONObject(typeFields);
		
		String name = obj.getString("name");
		String description = obj.getString("description");
		String icon = obj.getString("icon");
		
		Type type = new Type(description, icon, name);
		
		if (typeService.createType(type)) {
			output.println("The type was added successfully");
		}
		
		else {
			output.println("Failed to add type");
		}
	}
	
	
	/**
	 * update an existing type if the infos are valid
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void typeUpdateProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {
		
		String typeFields = (String) inputStream.readObject();
		
		JSONObject obj = new JSONObject(typeFields);
		
		String name = "";
		String description = "";
		String icon = "";
		
		int typeId = obj.getInt("typeId");
		
		if (obj.has("name")) {
			name = obj.getString("name");	
		}
		
		if (obj.has("description")) {
			description = obj.getString("description");	
		}
		
		if (obj.has("icon")) {
			icon = obj.getString("icon");
		}
		
		Type initialType = typeService.getTypeById(typeId);
		
		if (name.equals("")) {
			name = initialType.getName();
		}
		if (description.equals("")) {
			description = initialType.getDescription();
		}
		
		if (icon.equals("")) {
			icon = initialType.getIcon();
		}
		
		Type type = new Type(name, description, icon);
		
		if (typeService.updateType(typeId, type)) {
			output.println("The type was updated successfully");
		}
		
		else {
			output.println("Failed to update type");
		}
	}
	
	
	/**
	 * delete an existing type
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void typeDeleteProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {
		
		String typeFields = (String) inputStream.readObject();
		
		JSONObject obj = new JSONObject(typeFields);
	
		int typeId = obj.getInt("typeId");
		
		if (typeService.deleteType(typeId)) {
			output.println("The type was deleted successfully");
		}
	
		else {
			output.println("Type does not exist");
		}
	}
	
	/**
	 * draw a JTable containing all the types
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void showTypesProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {

		String typeFields = (String) inputStream.readObject();
		
		typeService.drawTable();
		output.println("Types are here!");
		
	}
	
}
