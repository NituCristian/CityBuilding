package model.business.amenity_rules;

import java.util.logging.Logger;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

import model.business.Amenity;

public class BuildOnWater extends Rule{

	private static final Logger logger = Logger.getLogger("MyLog");  
	
	public BuildOnWater(String description) {
		super(description);
	}
	
	/**
	 * log whether an amenity broke the water rule
	 * gives suggestions on how to modify amenity for being able to build it
	 * and log those suggestions in a log file 
	 * only an object of type 'Water' can be built of water
	 * an object of type 'Water' cannot be built exclusively on land
	 * @param amenity the amenity we want to build
	 * @param caseOfFailure	determine the case that was violated
	 */
	public void logWaterAmenity(Amenity amenity, int caseOfFailure) {		  
		
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
				int differenceTopX = 311 - amenity.getTopLeftX();
				int differenceTopY = 341 - amenity.getTopLeftY();
				 
				builder.append(" increase topLeftX and centerX with at least ");
				builder.append(differenceTopX);
				builder.append(" or increase topLeftY and centerY with at least ");
				builder.append(differenceTopY);
			}
			 
			else if (caseOfFailure == Rule.Failure.BOTTOM_RIGHT_NOT_OK.ordinal()) {
				int differenceBottomX = bottomRightX - 229;
				int differenceBottomY = bottomRightY - 249;
				 
				builder.append(" decrease topLeftX and centerX with at least ");
				builder.append(differenceBottomX);
				builder.append(" or decrease topLeftY and centerY with at least ");
				builder.append(differenceBottomY);
			}
			 

			else if (caseOfFailure == Rule.Failure.CONTAINS.ordinal()) {
				builder.append(" the amenity contains the entire river. Modify it's position ");
			}
			 
			else if (caseOfFailure == Rule.Failure.WATER_AMENITY.ordinal()) {	
				builder.append(" modify the coordinates so that the amenity ");
				builder.append("intersects the rectangle made by top left (230, 250), ");				
				builder.append("and bottom right: (310, 340).");
			}
			
			logger.info(builder.toString());  

		} catch (SecurityException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  

	} 
	
	/**
	 * determine whether we can build an amenity on water
	 * case TOP_LEFT_NOT_OK: the amenity is not of type 'Water' and its top left coordinates are in water
	 * case BOTTOM_RIGHT: the amenity is not of type 'Water' and its bottom right coordinates are in water
	 * case CONTAINS: the amenity is not of type 'Water' and its coordinates contains the water
	 * case WATER_AMENITY: the amenity is not of type 'Water', but it has a pixel in water
	 * @param newAmenity the amenity we want to build
	 * @return true if we can build the amenity, false otherwise
	 */
	public boolean canBuildAmenity(Amenity newAmenity) {
		String newType = newAmenity.getType().getName();
		int newTopLeftX = newAmenity.getTopLeftX();
		int newTopLeftY = newAmenity.getTopLeftY();
		int newCenterX = newAmenity.getCenterX();
		int newCenterY = newAmenity.getCenterY();
		
		int newBottomRightX = newTopLeftX + 2 * (newCenterX - newTopLeftX);
		int newBottomRightY = newTopLeftY + 2 * (newCenterY - newTopLeftY);		
		
		if (!newType.equals(super.getDescription().split(" ")[0])){
		
			if (newTopLeftX >= 230 && newTopLeftX <= 310 &&
				newTopLeftY >= 250 && newTopLeftY <= 340) {
				
				logWaterAmenity(newAmenity, Rule.Failure.TOP_LEFT_NOT_OK.ordinal());
				
				return false;
			}
			
			else if (newBottomRightX >= 230 && newBottomRightX <= 310 &&
					newBottomRightY >= 250 && newBottomRightY <= 340) {
				
				logWaterAmenity(newAmenity, Rule.Failure.BOTTOM_RIGHT_NOT_OK.ordinal());
				
				
				return false;
			}
			
			else if ((newTopLeftX < 230 && newBottomRightX > 310) &&
					(newTopLeftY < 250 && newBottomRightY > 340)) {
				
				logWaterAmenity(newAmenity, Rule.Failure.CONTAINS.ordinal());
				
				
				return false;
				
			}
		}
		
		else if (newType.equals(super.getDescription().split(" ")[0])){
			
			if ( (newTopLeftX < 230 && newBottomRightX < 230) ||
				 (newTopLeftX > 310 && newBottomRightX > 310) ) {
				
				if ( (newTopLeftY < 250 && newBottomRightY < 250) ||
						 (newTopLeftY > 340 && newBottomRightY > 340) ) {
					
					logWaterAmenity(newAmenity, Rule.Failure.WATER_AMENITY.ordinal());
							
					return false;
				}
			}
			
		}
		
		return true;
	}

}
