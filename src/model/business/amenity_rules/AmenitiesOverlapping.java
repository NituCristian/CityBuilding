package model.business.amenity_rules;

import java.util.logging.Logger;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

import data_access_layer.AmenityDAO;
import model.business.Amenity;

public class AmenitiesOverlapping extends Rule{

	private static final Logger logger = Logger.getLogger("MyLog");  
	
	/**
	 * invoke superclass constructor
	 * @param description rule description
	 */
	public AmenitiesOverlapping(String description) {
		super(description);
	}
	
	/**
	 * log whether an amenity broke the amenity overlapped rule 
	 * (2 amenities cannot overlap each other)
	 * gives suggestions on how to modify amenity for being able to build it
	 * and log those suggestions in a log file 
	 * @param newAmenity the amenity we want to build
	 * @param overlappedAmenity the amenity it is overlapping
	 * @param caseOfFailure	determine the case that was violated
	 */
	public void logOverlappedAmenity(Amenity newAmenity, Amenity overlappedAmenity, int caseOfFailure) {		  
		 FileHandler fh; 
		 StringBuilder builder = new StringBuilder();
		 
		 int bottomRightX = newAmenity.getTopLeftX() + 
				 2 * (newAmenity.getCenterX() - newAmenity.getTopLeftX());
		 int bottomRightY = newAmenity.getTopLeftY() +
				 2 * (newAmenity.getCenterY() - newAmenity.getTopLeftY());
		 		
		 int overlappedBottomRightX = overlappedAmenity.getTopLeftX() + 
				 2 * (overlappedAmenity.getCenterX() - overlappedAmenity.getTopLeftX());
		 
		 int overlappedBottomRightY = overlappedAmenity.getTopLeftY() + 
				 2 * (overlappedAmenity.getCenterY() - overlappedAmenity.getTopLeftY());
		 
		 try {   
			 fh = new FileHandler("logs\\FailLogger.txt", true);  
			 logger.addHandler(fh);
			 SimpleFormatter formatter = new SimpleFormatter();  
			 fh.setFormatter(formatter);  

			 builder = getDefaultBuilder(newAmenity);
			 
			 builder.append(" broke the rule: " + super.getDescription() + ". Suggestions: ");
			 
			 if (caseOfFailure == Rule.Failure.TOP_LEFT_NOT_OK.ordinal()) {
				 int differenceTopX = overlappedBottomRightX - newAmenity.getTopLeftX();
				 int differenceTopY = overlappedBottomRightY - newAmenity.getTopLeftY();
				 
				 builder.append(" increase topLeftX and centerX with at least ");
				 builder.append(differenceTopX);
				 builder.append(" or increase topLeftY and centerY with at least ");
				 builder.append(differenceTopY);
			 }
			 
			 else if (caseOfFailure == Rule.Failure.BOTTOM_RIGHT_NOT_OK.ordinal()) {
				 int differenceBottomX = bottomRightX - overlappedAmenity.getTopLeftX();
				 int differenceBottomY = bottomRightY - overlappedAmenity.getTopLeftY();
				 
				 builder.append(" decrease topLeftX and centerX with at least ");
				 builder.append(differenceBottomX);
				 builder.append(" or decrease topLeftY and centerY with at least ");
				 builder.append(differenceBottomY);
				 
			 }
			 
			 else if (caseOfFailure == Rule.Failure.CONTAINS.ordinal()) {
				
				builder.append(" the amenity contains an existent amenity. ");
				builder.append("Try to build it in a different place. ");
				 
			 }
			
			 logger.info(builder.toString());  

		 } catch (SecurityException e) {  
			 e.printStackTrace();  
		 } catch (IOException e) {  
			 e.printStackTrace();  
		 }  

	 } 
	
	/**
	 * determine whether the amenity we want to build does not overlap with another existent one
	 * we traverse the list of existing amenities to check whether it overlaps with newAmenity
	 * @param newAmenity the amenity we want to build
	 * @return true whether we can build the newAmenity
	 */
	
	public boolean canBuildAmenity(Amenity newAmenity) {
		Collection<Amenity> copyList;
		
		List<Amenity> existentAmenities = new AmenityDAO().findAll();
		
		int newTopLeftX = newAmenity.getTopLeftX();
		int newTopLeftY = newAmenity.getTopLeftY();
		int newCenterX = newAmenity.getCenterX();
		int newCenterY = newAmenity.getCenterY();
		
		int newBottomRightX = newTopLeftX + 2 * (newCenterX - newTopLeftX);
		int newBottomRightY = newTopLeftY + 2 * (newCenterY - newTopLeftY);		
		
		copyList = Collections.unmodifiableCollection(existentAmenities);	
					
		for (Amenity amenity: copyList) {
			int amenityTopLeftX = amenity.getTopLeftX();
			int amenityTopLeftY = amenity.getTopLeftY();
			int amenityCenterX = amenity.getCenterX();
			int amenityCenterY = amenity.getCenterY();
			
			int amenityBottomRightX = amenityTopLeftX + 2 * (amenityCenterX - amenityTopLeftX);
			int amenityBottomRightY = amenityTopLeftY + 2 * (amenityCenterY - amenityTopLeftY);
						
			if (newTopLeftX > amenityTopLeftX && newTopLeftX < amenityBottomRightX &&
				newTopLeftY > amenityTopLeftY && newTopLeftY < amenityBottomRightY) {
				
				logOverlappedAmenity(newAmenity, amenity, Rule.Failure.TOP_LEFT_NOT_OK.ordinal());
				return false;
			}
			
			else if	(newBottomRightX > amenityTopLeftX && newBottomRightX < amenityBottomRightX	&& 
				newBottomRightY > amenityTopLeftY && newBottomRightY < amenityBottomRightY)
			 {
				logOverlappedAmenity(newAmenity, amenity, Rule.Failure.BOTTOM_RIGHT_NOT_OK.ordinal());
				return false;
				
			}
			
			else if ((newTopLeftX < amenityTopLeftX && newBottomRightX > amenityBottomRightX) &&
					(newTopLeftY < amenityTopLeftY && newBottomRightY > amenityBottomRightY)) {
				
				logOverlappedAmenity(newAmenity, amenity, Rule.Failure.CONTAINS.ordinal());
				return false;
			}
			
		}//2 buildings cannot touch each other
				
		return true;
	}
	
}
