package model.business;

import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.imageio.ImageIO;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.business.amenity_rules.AmenitiesOverlapping;
import model.business.amenity_rules.BuildFactory;
import model.business.amenity_rules.BuildOnWater;
import model.business.amenity_rules.OnePerCity;
import model.business.amenity_rules.OnePerNeighbourhood;
import model.business.amenity_rules.Rule;


@Entity
@Table(name = "Amenity")
public class Amenity implements Serializable{
	
	private static final Logger logger = Logger.getLogger("MyLog");  
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id") 
	private int id;
	
	@Column(name = "NeighbourhoodId", nullable = false)
	private int neighbourhoodId;
	
	@Column(name = "TopLeftX", nullable = false)
	private int topLeftX;
	
	@Column(name = "TopLeftY", nullable = false)
	private int topLeftY;
	
	@Column(name = "CenterX", nullable = false)
	private int centerX;
	
	@Column(name = "CenterY", nullable = false)
	private int centerY;
	
	@Column(name = "Prestige", nullable = false)
	private float prestige;
	
	@Column(name = "Address", nullable = false, length = 255)
	private String address;
	
	@Column(name = "IsAvailable", nullable = false)
	private boolean isAvailable;
	
	@Column(name = "PermanentlyClosed", nullable = false)
	private boolean permanentlyClosed;
	
	@Column(name = "Name", nullable = false, length = 255, unique = true)
	private String name;
	
	@Column(name = "Description", nullable = false, length = 255)
	private String description;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "AmenityId")
	private Set<Photo> photos;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "AmenityId")
	private Set<Contact> contacts;
	
	@OneToOne
    @JoinColumn(name = "TypeId")
    private Type type;
	
	public static ArrayList<String> unimplementedRules = new ArrayList<>();
	
	public static enum Operation {
		 	INSERT,
		 	UPDATE,
		 	DELETE
	}
	
	public Amenity() {
		
	}
	
	public Amenity(float prestige, boolean isAvailable,
			boolean permanentlyClosed, String name, String description) {
		
		this.prestige = prestige;
		this.isAvailable = isAvailable;
		this.permanentlyClosed = permanentlyClosed;
		this.name = name;
		this.description = description;
	}
	
	/**
	 * 
	 * @param neighbourhoodId the id of the neighbourhood the amenity belongs to
	 * @param type the type of the amenity
	 * @param topLeftX top left x coordinate
	 * @param topLeftY top left y coordinate
	 * @param centerX center x coordinate
	 * @param centerY center y coordinate
	 * @param prestige the prestige of the amenity
	 * @param address the address of the amenity
	 * @param isAvailable say whether the amenity is available
	 * @param permanentlyClosed say whether the amenity is closed all the time
	 * @param name the name of the amenity
	 * @param description the description of the amenity
	 */
	public Amenity(int neighbourhoodId, Type type, int topLeftX, int topLeftY, int centerX,
			int centerY, float prestige, String address, boolean isAvailable,
			boolean permanentlyClosed, String name, String description) {
		
		this.neighbourhoodId = neighbourhoodId;
		this.type = type;
		this.topLeftX = topLeftX;
		this.topLeftY = topLeftY;
		this.centerX = centerX;
		this.centerY = centerY;
		this.prestige = prestige;
		this.address = address;
		this.isAvailable = isAvailable;
		this.permanentlyClosed = permanentlyClosed;
		this.name = name;
		this.description = description;
		
	}

	public int getId() {
		return id;
	}

	public int getNeighbourhoodId() {
		return neighbourhoodId;
	}

	public int getTopLeftX() {
		return topLeftX;
	}

	public int getTopLeftY() {
		return topLeftY;
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public float getPrestige() {
		return prestige;
	}

	public String getAddress() {
		return address;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public boolean isPermanentlyClosed() {
		return permanentlyClosed;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setPrestige(float prestige) {
		this.prestige = prestige;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public void setPermanentlyClosed(boolean permanentlyClosed) {
		this.permanentlyClosed = permanentlyClosed;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Photo> getPhotos() {
		return photos;
	}
	
	public Set<Contact> getContact() {
		return contacts;
	}

	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	/**
	 * gives the neighbourhood id for an amenity, by looking at its position
	 * @param x	the top left x coordinate of the amenity
	 * @param y the top left y coordinate of the amenity
	 * @return the neighbourhood id of the amenity
	 */
	
	public int computeNeighbourhood(int x, int y) {

		int neighbourhood = -1;
		
		if (x < 256 && y >= 256) {
			neighbourhood = 1;
		}
		
		else if (x >= 256 && y >= 256) {
			neighbourhood = 2;
		}
		
		else if (x < 256 && y < 256) {
			neighbourhood = 3;
		}
		
		else if (x >= 256 && y < 256) {
			neighbourhood = 4;
		}
		
		return neighbourhood;
	}
	
	
	/**
	 *`display the town (draw land, water and every amenity existent) 
	 * @param path	the image path
	 * @param amenities	constructed in the amenity
	 */
	public static void displayCityWithoutNeighbourhoods(String path, List<Amenity> amenities) {    
		      
		final int WIDTH = 512;
		final int HEIGHT = 512;
			
		BufferedImage displayCity = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g2dCover = displayCity.createGraphics();
		  
		g2dCover.setColor(Color.white);
		g2dCover.fillRect(0, 0, WIDTH, HEIGHT);
			         
		for (int i = 0; i < WIDTH; i++) {
			
			for (int j = 0; j < HEIGHT; j++){
		            	 
				if (i >= 230 && i <= 310 && j >= 250 && j <= 340) {
					g2dCover.setColor(Color.blue);
					g2dCover.drawLine(i, j, i, j);
				}
				else {
					g2dCover.setColor(Color.green);
					g2dCover.drawLine(i, j, i, j);
				}
		                
			} //water and land
		}
		         
		for (Amenity amenity: amenities) {
			int amenityTopLeftX = amenity.getTopLeftX();
			int amenityTopLeftY = amenity.getTopLeftY();
			
			int amenityWidth = 2 * (amenity.getCenterX() - amenity.getTopLeftX());
			int amenityHeight = 2 * (amenity.getCenterY() - amenity.getTopLeftY());
			
			Image image = null;
			
			//if no icon, color red
			if(amenity.getType().getIcon() == null) {
				g2dCover.setColor(Color.red);
				g2dCover.fillRect(amenity.getTopLeftX(), amenity.getTopLeftY(), 
						amenityWidth, amenityHeight);
			}
				
			else {

				try { //if icon, try to represent it
					image = ImageIO.read(new File(amenity.getType().getIcon()));
					g2dCover.drawImage(image, amenityTopLeftX, amenityTopLeftY,
							amenityWidth, amenityHeight, Color.white, null);
					
				} catch (IOException e) {
					System.out.println("Icon not found");
					try {//if no icon, represent icon with a question mark
						image = ImageIO.read(new File(".//icons//undefined.png"));
							g2dCover.drawImage(image, amenityTopLeftX, amenityTopLeftY,
									amenityWidth, amenityHeight, Color.white, null);
						} catch (IOException e1) {
							System.out.println(e1.getMessage());
						}
				}
			}
				
		}
			  
		g2dCover.dispose();
		
		// Save as JPEG
		File file = new File(path);
		       
		try {
			ImageIO.write(displayCity, "jpg", file); //save image
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		         
	}	
	
	/**
	 *	determine if a point is in an existent amenity
	 * @param p	a point
	 * @param amenities	the existent amenities
	 * @return	the amenity id (or -1 whether the point is in no amenity)
	 */
	public static int pointInAmenity(Point p, List<Amenity> amenities) {
 		int pos = -1;
	
 		for (Amenity amenity: amenities) {
 			int amenityTopLeftX = amenity.getTopLeftX();
 			int amenityTopLeftY = amenity.getTopLeftY();
 			int amenityCenterX = amenity.getCenterX();
 			int amenityCenterY = amenity.getCenterY();
 			
 			int bottomRightX = amenityTopLeftX + 2 * (amenityCenterX - amenityTopLeftX);
 			int bottomRightY = amenityTopLeftY + 2 * (amenityCenterY - amenityTopLeftY);
 			
 			if (p.x > amenityTopLeftX && p.x < bottomRightX
 				&&  p.y > amenityTopLeftY && p.y < bottomRightY) {
 				
 				pos = amenity.getId();
 				break;
 			}
 		}
 		
 		return pos;
	}
	
	/**
	 * log in file if an operation was made on an amenity (insert/update/delete)
	 * @param operation: the operation we want to execute (0 for insert, 1 for update, 2 for delete)	
	 */
	 public void logAmenityOperation(int operation) {		  
		
		 FileHandler fh; 
		 StringBuilder builder = new StringBuilder();
		 
		 try {   
			 fh = new FileHandler("logs\\Logger.txt", true);  
			 logger.addHandler(fh);
			 SimpleFormatter formatter = new SimpleFormatter();  
			 fh.setFormatter(formatter);  

			 int bottomRightX = topLeftX + 2 * (centerX - topLeftX);
			 int bottomRightY = topLeftY + 2 * (centerY - topLeftY);	

			 if (operation == Operation.INSERT.ordinal()) {
				 builder.append("INSERT: ");
			 }
			 else if (operation == Operation.UPDATE.ordinal()) {
				 builder.append("UPDATE: ");
			 }
			 else if (operation == Operation.DELETE.ordinal()) {
				 builder.append("DELETE: ");
			 }
			 
			 builder.append("Amenity with id: ");
			 builder.append(this.id);
			 builder.append(" and coordinatesL top left: (");
			 builder.append(this.topLeftX);
			 builder.append(", ");
			 builder.append(this.topLeftY);
			 builder.append("), and bottom right:(");
			 builder.append(bottomRightX);
			 builder.append(", ");
			 builder.append(bottomRightY);
			 builder.append(") and type: ");
			 builder.append(this.type.getName());
			 
			 if (operation == Operation.INSERT.ordinal()) {
				 builder.append(" was created ");
			 }
			 else if (operation == Operation.UPDATE.ordinal()) {
				 builder.append(" was updated ");
			 }
			 else if (operation == Operation.DELETE.ordinal()) {
				 builder.append(" was deleted ");
			 }
			 
			 logger.info(builder.toString());  

		 } catch (SecurityException e) {  
			 e.printStackTrace();  
		 } catch (IOException e) {  
			 e.printStackTrace();  
		 }  

	 } 
	 
	 /**
		 * add a new rule to our list of unimplemented rules
		 * @param newRule the rule we want to add
		 */
		public static void addUnimplementedRule(String newRule) {
			Amenity.unimplementedRules.add(newRule);
		}
		
		/**
		 * remove a rule from our list of unimplemented rules
		 * @param id the id of the rule we want to remove
		 */
		public static void deleteUnimplementedRule(int id) {
			Amenity.unimplementedRules.remove(id - 1);
		}
		
		/**
		 * JTable of rules that are proposed to be implemeted
		 * @param unimplementedrules the arrayList of unimplemented rules
		 */
		public static void createUnimplementedRulesTable(ArrayList<String> unimplementedrules) {
			JTable jTable = new JTable();

			jTable.setModel(new DefaultTableModel(
					new Object [][] {

		            },
		            new String [] {
		            		"Id", "Description"
		            }
		     ));
			
			DefaultTableModel model = (DefaultTableModel) jTable.getModel();
	        Object rowData[] = new Object[2];
	      
	        int position = 1;
	        for (String rule: unimplementedRules) {
	        	rowData[0] = position;
	        	rowData[1] = rule;
	        	
	        	model.addRow(rowData);
	        	position++;
	        }
	        
	        JOptionPane.showMessageDialog(null, new JScrollPane(jTable));
	      
		}
		
		/**
		 * the rules that are already implemented in town
		 * @return the list of implemented rules
		 */
		public static ArrayList<Rule> getRules(){
			ArrayList<Rule> rules = new ArrayList<>();
			
			rules.add(new BuildOnWater("water rule:  An amenity can be build on water just if it has the type 'Water'"));
			rules.add(new AmenitiesOverlapping("overlapping rule: An amenity can be build just whether it doesn't overlap with another existent amenity"));
			rules.add(new OnePerNeighbourhood("water_park rule: It can be build just one water_park per neighbourhood"));
			rules.add(new BuildFactory("factory rule:  A factory cannot be build at a smaller distance of 40 pixels from water"));
			rules.add(new OnePerCity("town_hall rule: There can be just a townhall in city"));
			rules.add(new OnePerNeighbourhood("police_station rule: There can be just a single police station per neighbourhood"));
			
			return rules;
		}
		
		/**
		 * create map of rule description and the truth value of testing on an amenity
		 * @return the map
		 */
		public Map<String, Boolean> getAmenityRulesMap(){
			Map<String, Boolean> amenityRules = new HashMap<>();
			
			for(Rule rule: Amenity.getRules()) {
				amenityRules.put(rule.getDescription(), rule.canBuildAmenity(this));
			}
			
			return amenityRules;
		}
		
		/**
		 * check whether we can build the amenity by checking every rule from the map
		 * @param amenityRules the map of rule and truth value for applying that rule
		 * @return true whether we can build the amenity
		 */
		public boolean canBuildAmenity(Map<String, Boolean> amenityRules) {
			for (Map.Entry<String, Boolean> entry : amenityRules.entrySet()) {
				if(!entry.getValue()) {
					return false;
				}
			}
			
			return true;
		}
		
		/**
		 * create a JTable containing the rules that are already implemented in town
		 * @param rules the list of implemented rules
		 */
		public static void createRulesTable(ArrayList<Rule> rules) {
			JTable jTable = new JTable();

			jTable.setModel(new DefaultTableModel(
					new Object [][] {

		            },
		            new String [] {
		                "Id", "Description"
		            }
		     ));
			
			DefaultTableModel model = (DefaultTableModel) jTable.getModel();
	        Object rowData[] = new Object[2];
	        
	        int pos = 1;
	        for (Rule rule: rules) {
	        	rowData[0] = pos;
	        	rowData[1] = rule.getDescription();
	        	
	        	model.addRow(rowData);
	        	pos++;
	        }
	        
	        JOptionPane.showMessageDialog(null, new JScrollPane(jTable));
	      
		}
		
		/**
		 * create a JTable of existing amenities in town
		 * @param amenities the list of existing amenities
		 */
		
		public static void createTable(List<Amenity> amenities) {
			JTable jTable1 = new JTable();

			jTable1.setModel(new DefaultTableModel(
					new Object [][] {

		            },
		            new String [] {
		                "Id", "NeighbourhoodId", "TypeId", "TopLeftX", "TopLeftY", "CenterX",
		                "CenterY", "Prestige", "Address", "IsAvailable", "PermanentlyClosed", 
		                "Name", "Description"
		            }
		     ));
			
			DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
	        Object rowData[] = new Object[13];
	      
	        for (Amenity amenity: amenities) {
	        	rowData[0] = amenity.getId();
	        	rowData[1] = amenity.getNeighbourhoodId();
	        	rowData[2] = amenity.getType().getId();
	        	rowData[3] = amenity.getTopLeftX();
	        	rowData[4] = amenity.getTopLeftY();
	        	rowData[5] = amenity.getCenterX();
	        	rowData[6] = amenity.getCenterY();
	        	rowData[7] = amenity.getPrestige();
	        	rowData[8] = amenity.getAddress();
	        	rowData[9] = amenity.isAvailable();
	        	rowData[10] = amenity.isPermanentlyClosed();
	        	rowData[11] = amenity.getName();
	        	rowData[12] = amenity.getDescription();
	        	
	      
	        	model.addRow(rowData);
	        }
	        
	        JOptionPane.showMessageDialog(null, new JScrollPane(jTable1));
	      
		}
		
		/**
		 * validate whether the amenity we would like to build is ok
		 * @return true if it is ok
		 */
		public boolean validateAmenity() {
			 
			if (this.validateUpdatedAmenity()) {
				 
				if (this.getTopLeftX() < 0 || this.getTopLeftX() > 512 || this.getTopLeftY() < 0 ||
						this.getTopLeftY() > 512 || this.getCenterX() < this.getTopLeftX()  ||
						this.getCenterX() > 512 || this.getCenterY() < this.getTopLeftY() ||
						this.getCenterY() > 512 || this.getAddress().length() > 255) {
					 
					return false;
				}
				 	
			}
			 
			return true;

		}
		 
		/**
		 * validate whether the amenity we would like to update is valid
		 * @return true whether the amenity is valid
		 */
		public boolean validateUpdatedAmenity() {
			
			if (this.getPrestige() > 5.0f || this.getName().length() > 255 ||
					this.getDescription().length() > 255) {
				 return false;
			}
			 
			return true;

		}
	
}