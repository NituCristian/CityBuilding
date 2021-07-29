package model.service;

import java.util.ArrayList;
import java.util.Set;

import data_access_layer.CityDAO;
import model.business.City;
import model.business.Neighbourhood;

public class CityService {

	private CityDAO cityDAO = new CityDAO();
	
	/**
	 * get the city
	 * @return the city
	 */
	public City getCity() {
		return cityDAO.getCityById(1);
	}
	
	/**
	 * get the neighbourhoods from a city
	 * @param cityId the id of the city 
	 * @return the set of neighbourhoods from the city
	 */
	public Set<Neighbourhood> neighbourhoodsFromCity(int cityId){
		City city = getCity();
			
		Set<Neighbourhood> neighbourhoods = city.getNeighbourhoods();
		
		return neighbourhoods;
	}
	
	/**
	 * compute the city rating
	 * @param cityId the id of the city
	 * @return the city rating
	 */
	public float calculateCityRating(int cityId) {
		City city = getCity();
		float rating = city.calculateRating();
		return rating;
	}
	
	/**
	 * add a new complaint
	 * @param complaint the user complaint
	 */
	public void addComplaint(String complaint) {
		City.addComplaint(complaint);
	}
	
	/**
	 * resolve an existing complaint
	 * @param id the id of the complaint it was resolved
	 */
	public void removeComplaint(int id) {
		City.removeComplaint(id);
	}
	
	/**
	 * draw a JTable containing all the existent complaints in the town
	 */
	public void drawComplaintsTable() {
		ArrayList<String> complaints = City.getCityComplaints();
		City.createComplaintsTable(complaints);
	}
	
}
