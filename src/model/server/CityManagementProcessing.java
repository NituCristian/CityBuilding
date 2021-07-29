package model.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONObject;

import model.business.Amenity;
import model.business.City;
import model.service.AmenityService;
import model.service.CityService;

public class CityManagementProcessing {

	private CityService cityService = new CityService();
	private AmenityService amenityService = new AmenityService();
	
	/**
	 * add a new complaint an user made to the existent ones
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void addComplaintProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {

		String parameters = (String) inputStream.readObject();
		JSONObject obj = new JSONObject(parameters);
		
		String complaintDescription = obj.getString("complaintDescription");
		
		cityService.addComplaint(complaintDescription);
		
		output.println("New complaint was added!");	
	}
	
	
	
	/**
	 * resolve a complaint from the existent ones
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void removeComplaintProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {

		String parameters = (String) inputStream.readObject();
		JSONObject obj = new JSONObject(parameters);
		
		int complaintId = obj.getInt("complaintId");
		
		cityService.removeComplaint(complaintId);
		
		output.println("Complaint was resolved!");	
	}
	
	
	/**
	 * draw a JTable containing the existent complaints in the town
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void showComplaintsProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {

		String parameters = (String) inputStream.readObject();
		
		cityService.drawComplaintsTable();
		
		output.println("Complaints are here!");	
	}
	
	
	/**
	 * search a place address, giving its name (if it is a valid one)
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void findPlaceProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {

		String parameters = (String) inputStream.readObject();
		JSONObject obj = new JSONObject(parameters);
		
		String placeName = obj.getString("place");
		
		Amenity amenity = amenityService.getAmenityByName(placeName);
		
		if (amenity == null) {
			output.println("The place you are looking for does not exist");
		}
		
		else {
			output.println("The address is: " + amenity.getAddress());
		}
	}
	
	/**
	 * go to a place specified by its name, if it is a valid one
	 * if the place is a water_park, one can go there just if the weathere permits it
	 * the temperature should be over 20 degrees, and it should be no rain or snow at that moment
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void goToPlaceProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {

		String parameters = (String) inputStream.readObject();
		JSONObject obj = new JSONObject(parameters);
		
		String placeName = obj.getString("place");
		
		Amenity amenity = amenityService.getAmenityByName(placeName);

		if (amenity == null) {
			output.println("The place you are looking for does not exist");
		}
		
		else {
			String type = amenity.getType().getName();
			
			City city = cityService.getCity();
			
			
			if (type.equals("water_park")) {
				String cityWeatherInfos = WeatherProcessing.getCityWeatherInfos(city.getLat(), city.getLong());
				
				String weather = WeatherProcessing.getWeather(cityWeatherInfos);
				double temp = WeatherProcessing.getTemperature(cityWeatherInfos);

				if (weather.equals("Rain") || weather.equals("Snow") || temp < 20) {
					output.println("You cannot go to " + placeName + " because of the weather: (" + weather + "; " + temp + ")");
					amenityService.updateAmenityAvailability(amenity, false);
					return;
				}
				
				else {
					amenityService.updateAmenityAvailability(amenity, true);
					output.println("You can go to " + placeName + " because weather is fine"); 
				}
			}
			
			else {
				output.println("You can go to " + placeName); 
			}
		}
		
	}
}
