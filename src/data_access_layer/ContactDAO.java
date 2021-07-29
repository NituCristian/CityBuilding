package data_access_layer;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.business.Contact;

public class ContactDAO implements IGenericDAO<Contact>{

	@Override
	public Contact findById(int contactId) {
		SessionFactory sessionFactory = SessionManager.config();
		Session session = null;
		Contact contact = null;
	       
		try {
			session = sessionFactory.openSession();
			contact = (Contact)session.get(Contact.class, contactId);
		} catch (Exception e) {
			e.printStackTrace();	
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return contact;
	}

	@Override
	public List<Contact> findAll() {
		SessionFactory sessionFactory = SessionManager.config();
		Session session = null;
		Transaction tx = null;
		List<Contact> contacts = null;
		
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			contacts = session.createQuery("FROM Contact").list(); 
	        tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	     
		return contacts;
	}

	@Override
	public void create(Contact contact) {
		SessionFactory sessionFactory = SessionManager.config();
		Session session = null;
		Transaction tx = null;
	 
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(contact);
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
	public void deleteById(int contactId) {
		SessionFactory sessionFactory = SessionManager.config();
		Session session = null;
		Transaction tx = null;
	      
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Contact contact = (Contact)session.get(Contact.class, contactId); 
			session.delete(contact); 
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
	 * update the contact with the id contactId
	 */
	public void update(int contactId, Contact contact) {
		Session session = null;
		Transaction tx = null;
	 
		try {
			session = SessionManager.config().openSession();
			Contact existentContact = (Contact)session.get(Contact.class, contactId);
			 
			existentContact.setPhone(contact.getPhone());
			existentContact.setMail(contact.getMail());

			tx = session.beginTransaction();
			session.save(existentContact);
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
