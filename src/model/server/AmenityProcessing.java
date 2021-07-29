package model.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONObject;

import data_access_layer.TypeDAO;
import model.business.Amenity;
import model.business.City;
import model.business.Type;
import model.service.AmenityService;
import model.service.CityService;
import model.service.Intent;

public class AmenityProcessing {

	private CityService cityService = new CityService();
	private TypeDAO typeDAO = new TypeDAO();
	private AmenityService amenityService = new AmenityService();
	
	/**
	 * check whether we can build a new amenity
	 * it must pass all the rules for building
	 * another condition is the wheather 
	 * there should be less than 40 degrees for the builders to work
	 * send a message to the client regarding the possibility of building it
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void amenityInsertProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {
			
		String amenityFields = (String) inputStream.readObject();
		
		JSONObject obj = new JSONObject(amenityFields);
		
		
		int typeId = obj.getInt("typeId");
		int topLeftX = obj.getInt("topLeftX");
		int topLeftY = obj.getInt("topLeftY");
		int centerX = obj.getInt("centerX");
		int centerY = obj.getInt("centerY");
		float prestige = obj.getFloat("prestige");
		String address = obj.getString("address");
		boolean isAvailable = obj.getBoolean("isAvailable");
		boolean permanentlyClosed = obj.getBoolean("permanentlyClosed");
		String name = obj.getString("name");
		String description = obj.getString("description");
		
		Type type = typeDAO.findById(typeId);
		
		int neighbourhoodId = amenityService.getNeighbourhood(topLeftX, topLeftY);
		
		Amenity amenity = new Amenity(neighbourhoodId, type, topLeftX, topLeftY,
				centerX, centerY, prestige, address, isAvailable,
				permanentlyClosed, name, description);
		
		boolean canBuild = amenityService.canBuildAmenity(amenity);
		
		if (true == canBuild) {
			
			City city = cityService.getCity();
			
			String cityWeatherInfos = WeatherProcessing.getCityWeatherInfos(city.getLat(), city.getLong());
			
			double temp = WeatherProcessing.getTemperature(cityWeatherInfos);
			
			if (temp < 40) {
				output.println("The amenity was build successfully; temperature is: " + temp + "C");
				amenityService.createAmenity(amenity);
			}
			
			else {
				output.println("Temperature is too high to build a new building (" + temp + "C)");
				return;
			}
			
		}
		
		else {
			output.println("Failed to build amenity");
		}
	}
	
	/**
	 * get the fields the client wish to update and validate the modified amenity
	 * send a message regarding the succes/failure of operation
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void amenityUpdateProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {
		
		String amenityFields = (String) inputStream.readObject();
		
		JSONObject obj = new JSONObject(amenityFields);
		
		float prestige = -1.0f;
		boolean isAvailable = false;
		boolean permanentlyClosed = false;
		String name = "";
		String description = "";
		
		int amenityId = obj.getInt("amenityId");
		
		if (obj.has("prestige")) {
			prestige = obj.getFloat("prestige");
		}
		
		if (obj.has("isAvailable")) {
			isAvailable = obj.getBoolean("isAvailable");
		}
		
		if (obj.has("permanentlyClosed")) {
			permanentlyClosed = obj.getBoolean("permanentlyClosed");
		}
		
		if (obj.has("name")) {
			name = obj.getString("name");
		}
		
		if (obj.has("description")) {
			description = obj.getString("description");
		}
		
		Amenity initialAmenity = amenityService.getAmenityById(amenityId);
		
		if (prestige == -1.0f) {
			prestige = initialAmenity.getPrestige();
		}
		
		if (isAvailable == false) {
			isAvailable = initialAmenity.isAvailable();
		}
		
		if (permanentlyClosed == false) {
			permanentlyClosed = initialAmenity.isPermanentlyClosed();
		}
		
		if (name.equals("")) {
			name = initialAmenity.getName();
		}
		
		if (description.equals("")) {
			description = initialAmenity.getDescription();
		}
		
		Amenity amenity = new Amenity(prestige, isAvailable, permanentlyClosed, name, description);
		
		if (amenityService.updateAmenity(amenityId, amenity)) {
			output.println("The amenity was updated successfully");
		}
		
		else {
			output.println("Failed to update amenity");
		}
	}
	
	/**
	 * check whether the amenity the client want to delete exists
	 * if so, try to delete it
	 * the temperature must be ok for the builders to destroy it
	 * (for the sake of this, we could have stopped the destroying if it was raining) 
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void amenityDeleteProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {
		
		String amenityFields = (String) inputStream.readObject();
		
		JSONObject obj = new JSONObject(amenityFields);
	
		int amenityId = obj.getInt("amenityId");
		
		City city = cityService.getCity();
		
		String cityWeatherInfos = WeatherProcessing.getCityWeatherInfos(city.getLat(), city.getLong());
		
		double temp = WeatherProcessing.getTemperature(cityWeatherInfos);
		
		if (temp < 40) {
			if (amenityService.deleteAmenity(amenityId)) {
				output.println("The amenity was deleted successfully");
				return;
			}
			
			else {
				output.println("Amenity does not exist");
				return;
			}
			
		}
		
		else {
			output.println("Temperature is too high to destroy an existing building (" + temp + "C)");
			return;
		}
		
	}

	/**
	 * draw a JTable containing the amenities in the city
	 *@param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void showAmenitiesProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {

		String amenityFields = (String) inputStream.readObject();
		
		amenityService.drawTable();
		output.println("Done");
		
	}
	
	/**
	 * draw a JTable containing the existing rules in the city
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void showImplementedRulesProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {

		String amenityFields = (String) inputStream.readObject();
		amenityService.drawUnimplementedRulesTable();
		
		amenityService.drawRulesTable();
		output.println("Done");	
	}
	
	
	
	/**
	 * propose a new rule to be implemented
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void addRuleProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {

		String amenityFields = (String) inputStream.readObject();
		
		JSONObject obj = new JSONObject(amenityFields);
		
		String ruleDescription = obj.getString("ruleDescription");
		
		amenityService.addRule(ruleDescription);
		
		output.println("Rule added to be implemented");	
	}
	
	
	/**
	 * remove an already proposed rule for implementation
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void removeUnimplementedProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {

		String amenityFields = (String) inputStream.readObject();
		
		JSONObject obj = new JSONObject(amenityFields);
	
		int ruleId = obj.getInt("ruleId");
		
		amenityService.removeRule(ruleId);
		
		output.println("Unimplemented rule deleted");	
	}
	
	/**
	 * draw a JTable containing the unimplemented rules existing in town
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 */
	public void showUnimplementedRulesProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException {

		amenityService.drawUnimplementedRulesTable();
		output.println("Done");	
	}
}
