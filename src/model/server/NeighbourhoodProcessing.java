package model.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONObject;

import data_access_layer.NeighbourhoodDAO;
import model.service.CityService;
import model.service.NeighbourhoodService;

public class NeighbourhoodProcessing {

	private CityService cityService = new CityService();
	private NeighbourhoodService neighbourhoodService = new NeighbourhoodService();
	
	/**
	 * compute the rating for the neighbourhood specified by the client
	 * return a float containing the rating
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void neighbourhoodRatingProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {
		
		String neighbourhoodFields = (String) inputStream.readObject();
		
		JSONObject obj = new JSONObject(neighbourhoodFields);
	
		int neighbourhoodId = obj.getInt("neighbourhoodId");
		
    	neighbourhoodService.calculateNeighbourhoodRating(neighbourhoodId);
    	float neighbourhoodRating = new NeighbourhoodDAO().findById(neighbourhoodId).getRating();
        output.println("Neighbourhood with id:" + neighbourhoodId + " has rating: " + neighbourhoodRating);
		
	}
	
	/**
	 * compute and send back the city rating
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void cityRatingProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {
		
		String neighbourhoodFields = (String) inputStream.readObject();
		
        float val =	cityService.calculateCityRating(1);
        output.println("City rating is: " + val);
	}
	
	/**
	 * draw a JTable containing the neighbourhoods in the city
	 * @param socket client socket
	 * @param inputStream socket input stream
	 * @param output socket output stream
	 * @throws IOException IOException object
	 * @throws ClassNotFoundException ClassNotFoundException object
	 */
	public void showNeighbourhoodsProcessing(
			Socket socket,
			ObjectInputStream inputStream,
			PrintWriter output	) throws IOException, ClassNotFoundException {

		String neighbourhoodFields = (String) inputStream.readObject();
		
		neighbourhoodService.drawTable();
		output.println("Here are the neighbourhoods!");	
	}
	
}
