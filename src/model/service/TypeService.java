package model.service;

import java.util.List;
import data_access_layer.TypeDAO;
import model.business.Type;

public class TypeService {
	
	TypeDAO typeDAO = new TypeDAO();
	
	/**
	 * create a new type
	 * @param type the type we would like to create
	 * @return true if the type is valid
	 */
	public boolean createType(Type type) {
		
		if(type.validateType()) {
			typeDAO.create(type);
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * the type we would like to delete
	 * @param typeId the id of the type
	 * @return true if deleted
	 */
	public boolean deleteType(int typeId) {
		Type type = typeDAO.findById(typeId);
		
		if(type != null) {
			typeDAO.deleteById(typeId);
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * create a JTable of the existing types
	 */
	public void drawTable() {
		List<Type> types = typeDAO.findAll();
		
		Type.createTable(types);
	}
	
	public List<Type> getTypes(){
		return typeDAO.findAll();
	}
	
	/**
	 * a type with an id
	 * @param typeId the id of the type
	 * @return a new type
	 */
	public Type getTypeById(int typeId) {
		return typeDAO.findById(typeId);
	}
	
	/**
	 * update a type
	 * @param typeId the id of the type
	 * @param type an object of type Type
	 * @return true if updated
	 */
	public boolean updateType(int typeId, Type type) {
		
		if(type.validateType()) {
			typeDAO.update(typeId, type);
			return true;
		}
		
		return false;
		
	}
	
}
