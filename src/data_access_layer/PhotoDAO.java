package data_access_layer;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.business.Photo;

public class PhotoDAO implements IGenericDAO<Photo>{

	@Override
	public Photo findById(int photoId) {
		SessionFactory sessionFactory = SessionManager.config();
		Session session = null;
		Photo photo = null;
	       
		try {
			session = sessionFactory.openSession();
			photo = (Photo)session.get(Photo.class, photoId);
		} catch (Exception e) {
			e.printStackTrace();	
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return photo;
	}

	@Override
	public List<Photo> findAll() {
		SessionFactory sessionFactory = SessionManager.config();
		Session session = null;
		Transaction tx = null;
		List<Photo> photos = null;
		
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			photos = session.createQuery("FROM Photo").list(); 
	        tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	     
		return photos;
	}

	@Override
	public void create(Photo photo) {
		SessionFactory sessionFactory = SessionManager.config();
		Session session = null;
		Transaction tx = null;
	 
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(photo);
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
	public void deleteById(int photoId) {
		SessionFactory sessionFactory = SessionManager.config();
		Session session = null;
		Transaction tx = null;
	      
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Photo photo = (Photo)session.get(Photo.class, photoId); 
			session.delete(photo); 
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
	public void update(int photoId, String path) {
		Session session = null;
		Transaction tx = null;
	 
		try {
			session = SessionManager.config().openSession();
			Photo existentPhoto = (Photo)session.get(Photo.class, photoId);
			 
			existentPhoto.setPath(path);

			tx = session.beginTransaction();
			session.save(existentPhoto);
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
