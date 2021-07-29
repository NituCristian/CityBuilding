package data_access_layer;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.business.Amenity;

public class AmenityDAO implements IGenericDAO<Amenity>{

	@Override
	public Amenity findById(int amenityId) {
		SessionFactory sessionFactory = SessionManager.config();
		Session session = null;
		Amenity amenity = null;
	       
		try {
			session = sessionFactory.openSession();
			amenity = (Amenity)session.get(Amenity.class, amenityId);
		} catch (Exception e) {
			e.printStackTrace();	
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return amenity;
	}
	
	/**
	 * give the amenity with the specified name
	 * @param name the name of the amenity
	 * @return the amenity wished if it exists, otherwise null
	 */
	public Amenity findByName(String name) {
		SessionFactory sessionFactory = SessionManager.config();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Amenity> cr = cb.createQuery(Amenity.class);
        Root<Amenity> root = cr.from(Amenity.class);
        cr.select(root).where(cb.equal(root.get("name"), name)); 

        Query<Amenity> query = session.createQuery(cr);
        query.setMaxResults(1);
        
        List<Amenity> result = query.getResultList();
        
        session.close();
        
        if(result.size() != 0) {
        	return result.get(0);
        }
        
        return null;
	}
	
	@Override
	public List<Amenity> findAll() {
		SessionFactory sessionFactory = SessionManager.config();
		Session session = null;
		Transaction tx = null;
		List<Amenity> amenities = null;
		
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			amenities = session.createQuery("FROM Amenity").list(); 
	        tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	     
		return amenities;
	}

	@Override
	public void create(Amenity amenity) {
		SessionFactory sessionFactory = SessionManager.config();
		Session session = null;
		Transaction tx = null;
	 
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(amenity);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	@Override
	public void deleteById(int amenityId) {
		SessionFactory sessionFactory = SessionManager.config();
		Session session = null;
		Transaction tx = null;
	      
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Amenity amenity = (Amenity)session.get(Amenity.class, amenityId); 
			session.delete(amenity); 
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	@Override
	public void update(int amenityId, Amenity amenity) {
		Session session = null;
		Transaction tx = null;
	 
		try {
			session = SessionManager.config().openSession();
			Amenity existentAmenity = (Amenity)session.get(Amenity.class, amenityId);
			 
			existentAmenity.setPrestige(amenity.getPrestige());
			existentAmenity.setAvailable(amenity.isAvailable());
			existentAmenity.setPermanentlyClosed(amenity.isPermanentlyClosed());
			existentAmenity.setName(amenity.getName());
			existentAmenity.setDescription(amenity.getDescription());
	         
			tx = session.beginTransaction();
			session.save(existentAmenity);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	     
	}
	
	/**
	 * save the amenity in the database
	 * @param amenity the amenity we want to save
	 */
	public void saveAmenity(Amenity amenity) {
		SessionFactory sessionFactory = SessionManager.config();
		Session session = null;
		Transaction tx = null;
	 
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.update(amenity);
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
