package model.business;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


@Entity
@Table(name = "Photo")
public class Photo implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	@Column(name = "AmenityId", nullable = false)
	private int amenityId;
	
	@Column(name = "Width", nullable = false)
	private int width;
	
	@Column(name = "Height", nullable = false)
	private int height;
	
	@Column(name = "Path", nullable = false, length = 255)
	private String path;
	
	public Photo() {
		
	}
	
	public Photo(int amenityId, int width, int height, String path) {
		this.amenityId = amenityId;
		this.width = width;
		this.height = height;
		this.path = path;
	}
	
	public Photo(String path) {
		this.path = path;
	}

	public int getId() {
		return id;
	}

	public int getAmenityId() {
		return amenityId;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * create a JTabel of the photos existing in city
	 * @param photos the arrayList of existing photos
	 */
	public static void createTable(List<Photo> photos) {
		JTable jTable1 = new javax.swing.JTable();

		jTable1.setModel(new javax.swing.table.DefaultTableModel(
				new Object [][] {

	            },
	            new String [] {
	                "Id", "AmenityId", "Width", "Height", "Path"
	            }
	     ));
		
		DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        Object rowData[] = new Object[5];
      
        for (Photo photo: photos) {
        	rowData[0] = photo.getId();
        	rowData[1] = photo.getAmenityId();
        	rowData[2] = photo.getWidth();
        	rowData[3] = photo.getHeight();
        	rowData[4] = photo.getPath();
      
        	model.addRow(rowData);
        }
        
        JOptionPane.showMessageDialog(null, new JScrollPane(jTable1));
      
	}
	
	/**
	 * validate whether the photo is valid
	 * @return true whether we can create a photo
	 */
	public boolean validatePhoto() {

		if (this.getPath().length() > 255) {
			 return false;
		}
		
		return true;
		
	}

}