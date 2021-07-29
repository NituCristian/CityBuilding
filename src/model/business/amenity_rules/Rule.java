package model.business.amenity_rules;

import model.business.Amenity;

public abstract class Rule {

	public String description;
	
	public static enum Failure {
		  TOP_LEFT_NOT_OK,		//top left corner is not valid
		  BOTTOM_RIGHT_NOT_OK,  //bottom right corner of the amenity is not valid
		  CONTAINS,				//an amenity is build over something
		  WATER_AMENITY			//amenity is of type water
	}
	
	public Rule() {
		
	}
	
	public Rule(String description) {
		this.description = description;
	}
	
	/**
	 * construct a default string builder where we specify the amenity position and type
	 * @param amenity default builder for an amenity
	 * @return	a stringBuilder object
	 */
	public StringBuilder getDefaultBuilder(Amenity amenity) {
		 StringBuilder builder = new StringBuilder();
		 
		 int bottomRightX = amenity.getTopLeftX() + 2 * (amenity.getCenterX() - amenity.getTopLeftX());
		 int bottomRightY = amenity.getTopLeftY() + 2 * (amenity.getCenterY() - amenity.getTopLeftY());
		 
		 builder.append("The amenity with coordinates top left: (");
		 builder.append(amenity.getTopLeftX());
		 builder.append(", ");
		 builder.append(amenity.getTopLeftY());
		 builder.append("), and bottom right:(");
		 builder.append(bottomRightX);
		 builder.append(", ");
		 builder.append(bottomRightY);
		 builder.append(") and type: ");
		 builder.append(amenity.getType().getName());
		 
		 return builder;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * abstract method for checking amenity validity upon a rule
	 * @param newAmenity the amenity we want to check 
	 * @return true whether the amenity pass the rule, otherwise false
	 */
	public abstract boolean canBuildAmenity(Amenity newAmenity);
	
}
