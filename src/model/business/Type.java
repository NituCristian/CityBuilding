package model.business;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


@Entity
@Table(name = "Type")
public class Type implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;
	
	@Column(name = "Name", nullable = false, length = 255)
	private String name;
	
	@Column(name = "Description", nullable = false, length = 255)
	private String description;
	
	@Column(name = "Icon", length = 255)
	private String icon;
	
	@OneToOne(mappedBy = "type", fetch = FetchType.EAGER)
	private Amenity amenity;
	
	public Type() {
		
	}
	
	/**
	 * constructor
	 * @param name type name
	 * @param description type description
	 * @param icon type icon
	 */	
	public Type(String name, String description, String icon) {
		this.name = name;
		this.description = description;
		this.icon = icon;
	}

	public int getId() {
		return id;
	}

	public Amenity getAmenity() {
		return amenity;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getIcon() {
		return icon;
	}

	public void setAmenity(Amenity amenity) {
		this.amenity = amenity;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * create a JTable of existing types
	 * @param types the arrayList of existing types
	 */
	public static void createTable(List<Type> types) {
		JTable jTable1 = new javax.swing.JTable();

		jTable1.setModel(new javax.swing.table.DefaultTableModel(
				new Object [][] {

	            },
	            new String [] {
	                "Id", "Name", "Description", "Icon"
	            }
	     ));
		
		DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        Object rowData[] = new Object[4];
      
        for (Type type: types) {
        	rowData[0] = type.getId();
        	rowData[1] = type.getName();
        	rowData[2] = type.getDescription();
        	rowData[3] = type.getIcon();
        	
        	model.addRow(rowData);
        }
        
        JOptionPane.showMessageDialog(null, new JScrollPane(jTable1));
      
	}
	
	/**
	 * check whether a type is valid
	 * @return true whether the type is valid
	 */
	public boolean validateType() {
		
		if (this.getDescription().length() > 255 || this.getName().length() > 255 ||
				this.getIcon().length() > 255) {
			return false;
		}
		
		return true;
		
	}
	
}