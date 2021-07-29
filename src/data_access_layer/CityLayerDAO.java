package data_access_layer;

import org.hibernate.Session;
import model.business.CityLayer;

public class CityLayerDAO{
	
	/**
	 * gives the city layer with the specified id
	 * @param cityLayerId the if of the layer
	 * @return an object of type CityLayer
	 */
	public CityLayer findById(int cityLayerId) {
		Session session = null;
		CityLayer cityLayer = null;
	       
		try {
			session = SessionManager.config().openSession();
			cityLayer = (CityLayer)session.get(CityLayer.class, cityLayerId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return cityLayer;
	}
	 
}

