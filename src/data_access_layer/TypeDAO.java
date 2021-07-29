package data_access_layer;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.business.Type;

public class TypeDAO implements IGenericDAO<Type>{

	@Override
	public Type findById(int typeId) {
		SessionFactory sessionFactory = SessionManager.config();
		Session session = null;
		Type type = null;
	       
		try {
			session = sessionFactory.openSession();
			type = (Type)session.get(Type.class, typeId);
		} catch (Exception e) {
			e.printStackTrace();	
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return type;
	}

	@Override
	public List<Type> findAll() {
		SessionFactory sessionFactory = SessionManager.config();
		Session session = null;
		Transaction tx = null;
		List<Type> types = null;
		
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			types = session.createQuery("FROM Type").list(); 
	        tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	     
		return types;
	}

	@Override
	public void create(Type type) {
		SessionFactory sessionFactory = SessionManager.config();
		Session session = null;
		Transaction tx = null;
	 
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(type);
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
	public void deleteById(int typeId) {
		SessionFactory sessionFactory = SessionManager.config();
		Session session = null;
		Transaction tx = null;
	      
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Type type = (Type)session.get(Type.class, typeId); 
			session.delete(type); 
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
	public void update(int typeId, Type type) {
		Session session = null;
		Transaction tx = null;
	 
		try {
			session = SessionManager.config().openSession();
			Type existentType = (Type)session.get(Type.class, typeId);
			 
			existentType.setDescription(type.getDescription());
			existentType.setName(type.getName());
			existentType.setIcon(type.getIcon());

			tx = session.beginTransaction();
			session.save(existentType);
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
