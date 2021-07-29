package model.business.amenity_rules;

import java.util.logging.Logger;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

import model.business.Amenity;

public class BuildFactory extends Rule{
	
	private static final Logger logger = Logger.getLogger("MyLog");  
	
	public BuildFactory(String description) {
		super(description);
	}
	
	/**
	 * log whether an amenity broke the factory rule
	 * gives suggestions on how to modify amenity for being able to build it
	 * and log those suggestions in a log file 
	 * an object of type Factory cannot be build nearly the water
	 * (it has to be at a distance of more than 40 pixels from the water)
	 * @param amenity the amenity we want to build
	 * @param caseOfFailure	determine the case that was violated
	 */

	public void logFactoryAmenity(Amenity amenity, int caseOfFailure) {		  
		 FileHandler fh; 
		 StringBuilder builder = new StringBuilder();
		 
		int bottomRightX = amenity.getTopLeftX() + 2 * (amenity.getCenterX() - amenity.getTopLeftX());
		int bottomRightY = amenity.getTopLeftY() + 2 * (amenity.getCenterY() - amenity.getTopLeftY());
				
		try {   
			fh = new FileHandler("logs\\FailLogger.txt", true);  
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
			fh.setFormatter(formatter);  

			builder = getDefaultBuilder(amenity);
			
			builder.append(" broke the " + super.getDescription() + ". Suggestions: ");
			
			if (caseOfFailure == Rule.Failure.TOP_LEFT_NOT_OK.ordinal()) {
				int differenceTopX = 351 - amenity.getTopLeftX();
				int differenceTopY = 381 - amenity.getTopLeftY();
				 
				builder.append(" increase topLeftX and centerX with at least ");
				builder.append(differenceTopX);
				builder.append(" or increase topLeftY and centerY with at least ");
				builder.append(differenceTopY);
			}	
			 
			else if(caseOfFailure == Rule.Failure.BOTTOM_RIGHT_NOT_OK.ordinal()) {
				int differenceBottomX = bottomRightX - 189;
				int differenceBottomY = bottomRightY - 209;
				
				builder.append(" decrease topLeftX and centerX with at least ");
				builder.append(differenceBottomX);
				builder.append(" or decrease topLeftY and centerY with at least ");
				builder.append(differenceBottomY);
				
			}
			 
			 
			else if (caseOfFailure == Rule.Failure.CONTAINS.ordinal()) {
					
				builder.append(" the factory contains the entire river. Modify it's position ");
				
			}
			
			logger.info(builder.toString());  

		} catch (SecurityException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  

	} 
	
	/**
	 * determine whether we can build a factory
	 * @param newAmenity the amenity we want to build
	 * @return true whether we can build the amenity
	 */
	
	public boolean canBuildAmenity(Amenity newAmenity) {
		String newType = newAmenity.getType().getName();
		int newTopLeftX = newAmenity.getTopLeftX();
		int newTopLeftY = newAmenity.getTopLeftY();
		int newCenterX = newAmenity.getCenterX();
		int newCenterY = newAmenity.getCenterY();
		
		int newBottomRightX = newTopLeftX + 2 * (newCenterX - newTopLeftX);
		int newBottomRightY = newTopLeftY + 2 * (newCenterY - newTopLeftY);		
		
		if (newType.equalsIgnoreCase(super.getDescription().split(" ")[0])) {
			
			if (newTopLeftX >= 190 && newTopLeftX <= 350 &&
				newTopLeftY >= 210 && newTopLeftY <= 380) {
				
				logFactoryAmenity(newAmenity, Rule.Failure.TOP_LEFT_NOT_OK.ordinal());
				
				return false;
			}
			
			
			else if (newBottomRightX >= 190 && newBottomRightX <= 350 &&
					newBottomRightY >= 210 && newBottomRightY <= 380) {
				
				logFactoryAmenity(newAmenity, Rule.Failure.BOTTOM_RIGHT_NOT_OK.ordinal());
				
				
				return false;
			}
		
		
			else if ((newTopLeftX < 190 && newBottomRightX > 350) &&
					(newTopLeftY < 210 && newBottomRightY > 380)) {
			
				logFactoryAmenity(newAmenity, Rule.Failure.CONTAINS.ordinal());
				
				return false;
			
			}
		}
		return true;
	}
	
}
