package model.service;

import java.util.List;

import data_access_layer.PhotoDAO;
import model.business.Photo;


public class PhotoService {
	
	private PhotoDAO photoDAO = new PhotoDAO();

	/**
	 * create a new photo
	 * @param photo the photo we would like to create 
	 * @return true if we manage to create the photo
	 */
	public boolean createPhoto(Photo photo) {
		
		if(photo.validatePhoto()) {
			photoDAO.create(photo);
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * delete a photo with an id
	 * @param photoId the id of the photo
	 * @return true if we deleted it 
	 */
	public boolean deletePhoto(int photoId) {
		Photo photo = photoDAO.findById(photoId);
		
		if(photo == null) {
			return false;
		}
		
		photoDAO.deleteById(photoId);
		return true;
	}
	
	/**
	 * create a table of the existing photos
	 */
	public void drawTable() {
		List<Photo> photos = photoDAO.findAll();
		
		Photo.createTable(photos);
	}
	
	/**
	 * get a photo with an id
	 * @param photoId the id of the photo
	 * @return an object of type photo
	 */
	public Photo getPhotoById(int photoId) {
		return photoDAO.findById(photoId);
	}
	
	/**
	 * update an existing photo
	 * @param photoId the id of the photo we would like to update
	 * @param path the path of the photo
	 * @return true if we updated it
	 */
	public boolean updatePhoto(int photoId, String path) {
		
		Photo photo = photoDAO.findById(photoId);
		if(photo.validatePhoto()) {
			photoDAO.update(photoId, path);
			return true;
		}
		
		return false;
		
	}
}
