package presentation.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

import model.service.AmenityService;
import model.service.CityLayerService;
import model.service.ImageService;
import presentation.view.AmenityView;
import presentation.view.CityManagementView;
import presentation.view.ContactView;
import presentation.view.ImageView;
import presentation.view.MainView;
import presentation.view.NeighbourhoodView;
import presentation.view.PhotoView;
import presentation.view.TypeView;

public class MainController {

	private MainView mainView;
	private ImageService imageService = new ImageService();
	private CityLayerService cityLayerService = new CityLayerService();
	
	public MainController() {
		
	}
	
	public MainController(MainView mainView) {
		this.mainView = mainView;
		
		mainView.addCityDisplayListener(new CityDisplayListener());
		mainView.addNeighbourhoodListener(new MainNeighbourhoodListener());
		mainView.addAmenityListener(new MainAmenityListener());
		mainView.addPhotoDisplayListener(new MainPhotoListener());
		mainView.addContactListener(new MainContactListener());
		mainView.addTypeListener(new MainTypeListener());
		mainView.addCityManagementListener(new CityManagementListener());
	}

	
	class CityDisplayListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			new AmenityService().drawAmenities();
			String imagePath = cityLayerService.getImagePath(1);
			
			ImageView imageView = new ImageView(imagePath);
			
			BufferedImage image = null;
			image = imageService.loadImage(imagePath);
			imageView.imageLabel.setIcon(new ImageIcon(image));
			
			ImageController imageController = new ImageController(imageView);
			
			imageView.setVisible(true);
			mainView.setVisible(false);
			
		}
	}
	
	class MainNeighbourhoodListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			NeighbourhoodView neighbourhoodView = new NeighbourhoodView();
			NeighbourhoodController neighbourhoodController = new NeighbourhoodController(neighbourhoodView);

			neighbourhoodView.setVisible(true);
			mainView.setVisible(false);
			
		}
	}

	
	class MainAmenityListener implements ActionListener{
	
		public void actionPerformed(ActionEvent e) {
			AmenityView amenityView = new AmenityView();
			AmenityController amenityController = new AmenityController(amenityView);
			
			amenityView.setVisible(true);
			mainView.setVisible(false);
			
		}
	}
	
	class MainPhotoListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			PhotoView photoView = new PhotoView();
			PhotoController photoController = new PhotoController(photoView);
			
			photoView.setVisible(true);
			mainView.setVisible(false);
		}
	}
	
	class MainContactListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			ContactView contactView = new ContactView();
			ContactController contactController = new ContactController(contactView);
		
			contactView.setVisible(true);
			mainView.setVisible(false);
		}
	}
	
	class MainTypeListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			TypeView typeView = new TypeView();
			TypeController typeController = new TypeController(typeView);
			
			typeView.setVisible(true);
			mainView.setVisible(false);
		}
	}
	
	class CityManagementListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			CityManagementView cityManagementView = new CityManagementView();
			CityManagementController cityManagementController = new CityManagementController(cityManagementView);

			cityManagementView.setVisible(true);
			mainView.setVisible(false);
		}
	}

}
