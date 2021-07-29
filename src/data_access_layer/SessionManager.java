package data_access_layer;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.business.Amenity;
import model.business.City;
import model.business.CityLayer;
import model.business.Contact;
import model.business.Neighbourhood;
import model.business.Photo;
import model.business.Type;

public class SessionManager {
	 private static SessionFactory factory; 
	 
	 static SessionFactory config() {
		 try {
			 factory = new Configuration().
					 configure().
					 addPackage("model"). //add package if used.
		             addAnnotatedClass(City.class).
		             addAnnotatedClass(Neighbourhood.class).
		             addAnnotatedClass(CityLayer.class).
		             addAnnotatedClass(Amenity.class).
		             addAnnotatedClass(Photo.class).
		             addAnnotatedClass(Contact.class).
		             addAnnotatedClass(Type.class).
		             buildSessionFactory();
		             return factory;
		     
		 } catch (Throwable ex) { 
			 System.err.println("Failed to create sessionFactory object." + ex);
		     throw new ExceptionInInitializerError(ex); 
		 }

	 }
	 
	 
}

