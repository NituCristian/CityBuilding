package model.service;

import data_access_layer.CityLayerDAO;
import model.business.CityLayer;

public class CityLayerService {

	/**
	 * get the path of the layer we want to get
	 * @param id the id of the layer
	 * @return the image path
	 */
	public String getImagePath(int id) {
		CityLayerDAO cityLayerDAO = new CityLayerDAO();
		CityLayer cityLayer = cityLayerDAO.findById(id);
		
		return cityLayer.getImagePath();
	}
}
