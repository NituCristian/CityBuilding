package data_access_layer;

import java.util.List;

public interface IGenericDAO<T> {
	
	/**
	 * find the object of type T with the id specified
	 * @param id the id of the object with type T
	 * @return the object of type T with the specified id
	 */
	 T findById(final int id);
	 
	 /**
	  * gives all the objects of type T
	  * @return a List of objects with the type T
	  */
	 List<T> findAll();
	 
	 /**
	  * create a new object of the specified type and insert it in the db
	  * @param entity the object of type T we want to create
	  */
	 void create(final T entity);
	 
	 /**
	  * update an object of type T
	  * default implementation in case we want to use this method of update
	  * @param id the id of object
	  * @param entity update the object
	  */
	 default public void update(final int id, T entity) {
		 System.out.println("Default implementation of update");
	 }
	 
	 /**
	  * update a string field of an object with a type
	  * default implementation in case we don't want to update just a string field from the object
	  * @param id the id of the object
	  * @param fieldToChange a string field from the object
	  */
	 default public void update(final int id, String fieldToChange) {
		 System.out.println("Default implementation of string field update");
	 }
	  
	 /**
	  * delete an object with an id
	  * @param id the id of the object we want to delete
	  */
	 public void deleteById(final int id);
	 
}
