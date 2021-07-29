package data_access_layer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.business.Neighbourhood;

public class NeighbourhoodDAO {

	/**
	 * return a neighbourhood with the id specified
	 * @param neighbourhoodId the id of the neighbourhood 
	 * @return an object of type neighbourhood
	 */
	public Neighbourhood findById(int neighbourhoodId) {
		SessionFactory sessionFactory = SessionManager.config();
		   
		Session session = null;
	    Neighbourhood neighbourhood = null;
	       
	    try {
	    	session = sessionFactory.openSession();
	    	neighbourhood = (Neighbourhood)session.get(Neighbourhood.class, neighbourhoodId);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	if (session != null && session.isOpen()) {
	    		session.close();
	    	}
	    }
	    return neighbourhood;
	}
	
	/**
	 * save an object of type neighbourhood in the database
	 * @param neighbourhood object of type neighbourhood
	 */
	public void saveNeighbourhood(Neighbourhood neighbourhood) {
		SessionFactory sessionFactory = SessionManager.config();
		Session session = null;
		Transaction tx = null;
	 
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.update(neighbourhood);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
}

