package model.server;

import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;

import java.awt.Point;
import model.business.Amenity;
import model.business.Neighbourhood;
import model.service.AmenityService;
import model.service.CityLayerService;
import model.service.ImageService;
import model.service.NeighbourhoodService;

public class CityDisplayProcessing {

	private AmenityService amenityService = new AmenityService();
	private NeighbourhoodService neighbourhoodService = new NeighbourhoodService();
	private CityLayerService cityLayerService = new CityLayerService();
	private ImageService imageService = new ImageService();
	

	/**
	 * check whether there is any amenity on the position clicked by the client
	 * if there is any, send back amenity name, type and neighbourhood name and rating
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void giveAmenityOnClick(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {
		
		String position = (String) inputStream.readObject();
		
		JSONObject positionJson = new JSONObject(position);
		
		int cityPosX = positionJson.getInt("cityPosX");
		int cityPosY = positionJson.getInt("cityPosY");
    	
    	Point point = new Point(cityPosX, cityPosY);
    	
    	Amenity amenity = amenityService.getAmenityPoint(point);
    	
    	if (amenity == null) {
    		output.println("No amenity");
    	}
    	
    	else {
    		String type = amenity.getType().getName();
        	String amenityName = amenity.getName();
        	
        	int neighbourhoodId = amenity.getNeighbourhoodId();
     		Neighbourhood neighbourhood = neighbourhoodService.getNeighbourhoodById(neighbourhoodId);
     		
     		String neighbourhoodName = neighbourhood.getName();
     		float neighbourhoodRating = neighbourhood.getRating();
        	
     		JSONObject obj = new JSONObject();

     		obj.put("type", type);
     		obj.put("amenity", amenityName);
     		obj.put("neighbourhood", neighbourhoodName);
     		obj.put("rating", neighbourhoodRating);

     		StringWriter out = new StringWriter();
     		obj.write(out);
     	      
     		String jsonText = out.toString();
     		
     		output.println(jsonText);
     		System.out.println("Server 3");
    	}
    	
	}
	

	/**
	 * send back to the user the file path for the city display
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void showNeighbourhoods(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {
		
		String position = (String) inputStream.readObject();
		
		String cityWithNeighbourhoods = cityLayerService.getImagePath(2);
		
		neighbourhoodService.createCityWithNeighbourhoods(cityWithNeighbourhoods, 1);
		
		output.println(cityWithNeighbourhoods);

	}
	
}
