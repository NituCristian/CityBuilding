package model.service;

import java.util.Set;

import data_access_layer.NeighbourhoodDAO;
import model.business.Neighbourhood;

public class NeighbourhoodService {

	private NeighbourhoodDAO neighbourhoodDAO = new NeighbourhoodDAO();
	
	/**
	 * get a neighbourhood with a specified id
	 * @param neighbourhoodId the id of the neighbourhood
	 * @return an object of type neighbourhood
	 */
	public Neighbourhood getNeighbourhoodById(int neighbourhoodId) {
		return neighbourhoodDAO.findById(neighbourhoodId);
	}
	
	/**
	 * create an image containing the neighbourhoods
	 * @param path the path of the image
	 * @param cityId the id of the city we want to draw
	 */
	public void createCityWithNeighbourhoods(String path, int cityId) {
		Set<Neighbourhood> neighbourhoods = null;
		neighbourhoods = new CityService().neighbourhoodsFromCity(cityId);
		Neighbourhood.diplayCityWithNeighbourhoods(path, neighbourhoods);
	}
	
	/**
	 * determine the rating of a neighbourhood and save it in the db
	 * @param neighbourhoodId the id of the neighbourhood
	 */
	public void calculateNeighbourhoodRating(int neighbourhoodId) {
		Neighbourhood neighbourhood = this.getNeighbourhoodById(neighbourhoodId);
		neighbourhood.modifyRating();
		
		neighbourhoodDAO.saveNeighbourhood(neighbourhood);
	}
	
	/**
	 * JTable containing all the neighbourhoods
	 */
	public void drawTable() {
		Set<Neighbourhood> neighbourhoods = null;
		neighbourhoods = new CityService().neighbourhoodsFromCity(1);
		new Neighbourhood().createTable(neighbourhoods);
	}
}
