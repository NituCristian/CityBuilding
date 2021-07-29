package model.business.amenity_rules;

import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import data_access_layer.AmenityDAO;
import model.business.Amenity;

public class OnePerCity extends Rule{

	private static final Logger logger = Logger.getLogger("MyLog");  
	
	
	public OnePerCity(String description) {
		super(description);
	}
	

	/**
	 * log whether the amenity broke the onePerCity rule
	 * (there can be just a town_hall per city)
	 * @param amenity the amenity we want to build
	 */
	public void logOnePerCityAmenity(Amenity amenity) {		  
		 FileHandler fh; 
		 StringBuilder builder = new StringBuilder();
		 
		 try {   
			 fh = new FileHandler("logs\\FailLogger.txt", true);  
			 logger.addHandler(fh);
			 SimpleFormatter formatter = new SimpleFormatter();  
			 fh.setFormatter(formatter);  

			 builder = getDefaultBuilder(amenity);
			 String first = super.getDescription().split(" ")[0];
			 builder.append(" broke the rule with max a " + first +  " per neighbourhood.");
			 builder.append("You cannot build a new townhall.");
			 
			 logger.info(builder.toString());  
			 

		 } catch (SecurityException e) {  
			 e.printStackTrace();  
		 } catch (IOException e) {  
			 e.printStackTrace();  
		 }  

	 } 
	
	/**
	 * check whether we can build the newAmenity if it has type "town_hall" in the city
	 * if there is another amenity with this type in the city, we cannot build it
	 * @param newAmenity the amenity we want to build
	 * @return true whether we can build the amenity
	 */
	public boolean canBuildAmenity(Amenity newAmenity) {
		
		List<Amenity> amenities = new AmenityDAO().findAll();
		
		String newType = newAmenity.getType().getName();
		
		System.out.println("New: " + newType);
		
		if (newType.equals(super.getDescription().split(" ")[0])) {
			
			for (Amenity amenity: amenities) {
				if (amenity.getType().getName().equals(newType)) {
					logOnePerCityAmenity(newAmenity);
					
					return false;
				}
			}
			
		}
		
		return true;
	}

}
