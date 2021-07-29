package model.business;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CityLayers")
public class CityLayer implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LayerId")
	private int id;
	
	@Column(name = "Type", nullable = false, length = 255)
	private String type;
	
	@Column(name = "ImagePath", nullable = false, length = 255)
	private String imagePath;
	
	@Column(name = "CityId", nullable = false)
    private int cityId;
	
	public CityLayer() {
		
	}
	
	/**
	 * city layer constructor
	 * @param type determine the type of the city layer
	 * @param imagePath the city layer's image path
	 * @param cityId the id of the city
	 */
	public CityLayer(String type, String imagePath, int cityId) {
		this.type = type;
		this.imagePath = imagePath;
		this.cityId = cityId;
	}

	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getImagePath() {
		return imagePath;
	}

	public int getCityId() {
		return cityId;
	}

}
