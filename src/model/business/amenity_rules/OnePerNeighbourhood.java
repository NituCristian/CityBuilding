package model.business.amenity_rules;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import data_access_layer.NeighbourhoodDAO;
import model.business.Amenity;
import model.business.Neighbourhood;

public class OnePerNeighbourhood extends Rule{

	private static final Logger logger = Logger.getLogger("MyLog");  
	
	
	public OnePerNeighbourhood(String description) {
		super(description);
	}
	
	/**
	 * log whether the amenity broke the onePerNeighbourhood rule
	 * (there can be just a water_park or police_station in every neighbourhood)
	 * gives suggestions on how to modify amenity for being able to build it
	 * and log those suggestions in a log file 
	 * @param amenity the amenity we want to build
	 */
	public void logOnePerNeighbourhoodAmenity(Amenity amenity) {		  
		 FileHandler fh; 
		 StringBuilder builder = new StringBuilder();
		 
		 try {   
			 fh = new FileHandler("logs\\FailLogger.txt", true);  
			 logger.addHandler(fh);
			 SimpleFormatter formatter = new SimpleFormatter();  
			 fh.setFormatter(formatter);  

			 builder = getDefaultBuilder(amenity);
			 String first = super.getDescription().split(" ")[0];
			 builder.append(" broke the rule with max a " + first +  " per neighbourhood. Suggestions: ");
			
			 builder.append(" build the " + first + " in another neighbourhood ");
	
			// super.setDescription(new StringBuilder().append(super.getDescription()).append(" rule").toString());
			 logger.info(builder.toString());  
			 

		 } catch (SecurityException e) {  
			 e.printStackTrace();  
		 } catch (IOException e) {  
			 e.printStackTrace();  
		 }  

	 } 

	/**
	 * check whether we can build the newAmenity if it has type "water_park" or "police_station".
	 * if there is another amenity with this type in the neighbourhood, we cannot build it
	 * @param newAmenity the amenity we want to build
	 * @return true whether we can build the amenity
	 */
	public boolean canBuildAmenity(Amenity newAmenity) {
	
		Neighbourhood neighbourhood = new NeighbourhoodDAO().findById(newAmenity.getNeighbourhoodId());
		String newType = newAmenity.getType().getName();
		
		if (newType.equals(super.getDescription().split(" ")[0])) {
			
			System.out.println(super.getDescription());
			
			for (Amenity amenity: neighbourhood.getAmenities()) {
				if (amenity.getType().getName().equalsIgnoreCase(newType)) {
					logOnePerNeighbourhoodAmenity(newAmenity);
					
					return false;
				}
			}
			
		}
			
		return true;
	}
	
}
