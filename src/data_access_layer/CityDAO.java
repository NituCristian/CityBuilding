package data_access_layer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.business.City;

public class CityDAO {
  
	/**
	 * return the city with the specified id
	 * @param cityId the id of the wished city
	 * @return a city object
	 */
   public City getCityById(int cityId) {
	   SessionFactory sessionFactory = SessionManager.config();
	   
       Session session = null;
       City city = null;
       
       try {
           session = sessionFactory.openSession();
           city = (City)session.get(City.class, cityId);
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           if (session != null && session.isOpen()) {
               session.close();
           }
       }
       return city;
   }
   
}