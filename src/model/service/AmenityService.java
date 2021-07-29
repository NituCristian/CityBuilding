package model.service;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import data_access_layer.AmenityDAO;
import data_access_layer.CityLayerDAO;
import model.business.Amenity;
import model.business.Neighbourhood;

public class AmenityService {
	
	private AmenityDAO amenityDAO = new AmenityDAO();
	private CityLayerDAO cityLayerDAO = new CityLayerDAO();
	
	/**
	 * gives the amenity which contains the point specified, if it exists
	 * @param p the point we want to test (by a mouse click)
	 * @return the amenity object which contains the point
	 */
	public Amenity getAmenityPoint(Point p) { 
		List<Amenity> amenities = new ArrayList<>();
		amenities = amenityDAO.findAll();
		int pos = Amenity.pointInAmenity(p, amenities);
		
		Amenity amenity = amenityDAO.findById(pos);
		return amenity;
	}
	
	/**
	 * get the amenity with an id
	 * @param amenityId the id of the amenity
	 * @return the amenity with the specified id
	 */
	public Amenity getAmenityById(int amenityId) {
		return amenityDAO.findById(amenityId);
	}
	
	/**
	 * create an amenity and log in file, if it is valid
	 * @param amenity the amenity we try to build
	 */
	public void createAmenity(Amenity amenity) {
		
		if(amenity.validateAmenity()) {
			amenityDAO.create(amenity);
			amenity.logAmenityOperation(Amenity.Operation.INSERT.ordinal());
		}
		
	}
	
	/**
	 * delete the amenity with the specified Id, if it exists
	 * @param amenityId the id of the amenity we want to delete
	 * @return true if it exists, false otherwise
	 */
	public boolean deleteAmenity(int amenityId) {
		Amenity amenity = amenityDAO.findById(amenityId);
		if(amenity == null) {
			return false;
		}
		else {
			amenity.logAmenityOperation(Amenity.Operation.DELETE.ordinal());
			amenityDAO.deleteById(amenityId);
			return true;
		}
		
	}
	
	/**
	 * create a JTable with the existing amenities
	 */
	public void drawTable() {
		List<Amenity> amenities = amenityDAO.findAll();
		
		Amenity.createTable(amenities);
	}
	
	/**
	 * create a JTable of implemented rules
	 */
	public void drawRulesTable() {
		Amenity.createRulesTable(Amenity.getRules());
	}
	
	/**
	 * create a JTable of unimplemented rules
	 */
	public void drawUnimplementedRulesTable() {
		Amenity.createUnimplementedRulesTable(Amenity.unimplementedRules);
	}
	
	/**
	 * update an amenity if the fields we want to modify are valid
	 * @param amenityId the id of the amenity
	 * @param amenity the amenity object
	 * @return true whether we manage to update it
	 */
	public boolean updateAmenity(int amenityId, Amenity amenity) {
		
		if(amenity.validateUpdatedAmenity()) {
			amenityDAO.update(amenityId, amenity);
			amenityDAO.findById(amenityId).logAmenityOperation(Amenity.Operation.UPDATE.ordinal());
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * get the neighbourhood of an amenity
	 * @param topLeftX top left x coordinate of the amenity
	 * @param topLeftY top left y coordinate of the amenity
	 * @return the neighbourhood id
	 */
	public int getNeighbourhood(int topLeftX, int topLeftY) {
		return new Amenity().computeNeighbourhood(topLeftX, topLeftY);
	}
	
	
	/**
	 * check whether we can build a new amenity
	 * @param amenity the amenity we want to build
	 * @return true if the amenity violates no rule
	 */
	public boolean canBuildAmenity(Amenity amenity) {
		Map<String, Boolean> mapRulesToAmenity = amenity.getAmenityRulesMap();

		if(amenity.canBuildAmenity(mapRulesToAmenity)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * draw the map containing all the amenities
	 */
	public void drawAmenities() {
		List<Amenity> amenities = this.getAmenitiesFromCity();
		String path = cityLayerDAO.findById(1).getImagePath();
		Amenity.displayCityWithoutNeighbourhoods(path, amenities);
	}
	
	/**
	 * add a new rule in the list of unimplemented rules
	 * @param rule the rule we want to add
	 */
	public void addRule(String rule) {
		Amenity.addUnimplementedRule(rule);
	}
	
	/**
	 * remove a rule from the list of unimplemented rules
	 * @param id the id of the rule we want to remove
	 */
	public void removeRule(int id) {
		Amenity.deleteUnimplementedRule(id);
	}
	
	/**
	 * get all amenities from city
	 * @return list of amenities
	 */
	public List<Amenity> getAmenitiesFromCity(){
		List<Amenity> amenities = new ArrayList<>();
		Set<Neighbourhood> neighbourhoods = new CityService().neighbourhoodsFromCity(1);
		
		for(Neighbourhood n: neighbourhoods) {
			Set<Amenity> amenitie = n.getAmenities();
			for(Amenity o: amenitie) {
				amenities.add(o);
			}
		}
		
		return amenities;
	}
	
	/**
	 * give the amenity with a specified name
	 * @param name of the amenity
	 * @return the amenity wanted
	 */
	public Amenity getAmenityByName(String name) {
		return amenityDAO.findByName(name);
	}

	/**
	 * set whether an amenity is open, looking at the weather
	 * @param amenity the amenity we check
	 * @param value the boolean value specifying if it should be closed or not
	 */
	public void updateAmenityAvailability(Amenity amenity, boolean value) {
		amenity.setAvailable(value);
		amenityDAO.saveAmenity(amenity);
	}
}

