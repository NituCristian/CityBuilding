package model.business;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

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
import javax.persistence.Table;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@Entity
@Table(name = "Neighbourhood")
public class Neighbourhood implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;
	
	@Column(name = "Name", nullable = false, length = 255)
	private String name;
	
	@Column(name = "Rating", nullable = false)
	private float rating;
	
	@Column(name = "CityId", nullable = false)
    private int cityId;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="NeighbourhoodId")
	private Set<Amenity> amenities;
	
	public Neighbourhood() {
		
	}
	
	/**
	 * constructor
	 * @param name neighbourhood name
	 * @param rating neighbourhood rating
	 * @param cityId the city the neighbourhood belongs to
	 */
	public Neighbourhood(String name, float rating, int cityId) {
		this.name = name;
		this.rating = rating;
		this.cityId = cityId;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public float getRating() {
		return rating;
	}

	void setRating(float rating) {
		this.rating = rating;
	}

	public int getCityId() {
		return cityId;
	}

	public Set<Amenity> getAmenities() {
		return amenities;
	}
	
	/**
	 * determine a neighbourhood rating
	 */
	public void modifyRating() {
		//set a neighbour rating
		Collection<Amenity> copyList = null	;
		float result = 0.0f;
			
		copyList = Collections.unmodifiableCollection(this.amenities);
			
		Iterator<Amenity> iterator = copyList.iterator();
			 
		while (iterator.hasNext())
		{
			result += iterator.next().getPrestige();
		}
			
		result /= copyList.size();
			
		this.setRating(result);
	}
	
	/**
	 * determine the top left point of a neighbourhood
	 * traverse the amenities from a neighbourhood
	 * get the min of top left x and y coordinates and form a point 
	 * @return point of min top left coordinates
	 */
	public Point getTopLeftPoint(){
		//get the top left city point in a neighbourhood
		
		ArrayList<Point> points = new ArrayList<>();
		
		for (Amenity a: amenities) {
			points.add(new Point(a.getTopLeftX(), a.getTopLeftY()));
		}
		
		points.sort(Comparator.comparing(Point::getX));
		int xmin = points.get(0).x;
		
		points.sort(Comparator.comparing(Point::getY));
		int ymin = points.get(0).y;
		
		points.clear();
		
		return new Point(xmin, ymin);
	}
	
	/**
	 * determine the bottom right point of a neighbourhood
	 * traverse the amenities from a neighbourhood
	 * get the max of bottom right x and y coordinates and form a point 
	 * @return point of max bottom right coordinates
	 */
	public Point getBottomRightPoint(){
		//get the bottom right point of a neighbourhood
		ArrayList<Point> points = new ArrayList<>();
		
		int bottomRightX = -1;
		int bottomRightY = -1;
		
		for (Amenity a: amenities) {
			
			bottomRightX = a.getTopLeftX() + 2 * (a.getCenterX() - a.getTopLeftX());
			bottomRightY = a.getTopLeftY() + 2 * (a.getCenterY() - a.getTopLeftY());
			
			points.add(new Point(bottomRightX, bottomRightY));
		}
		
		points.sort(Comparator.comparing(Point::getX));
		int xmax = points.get(points.size() - 1).x;
		
		points.sort(Comparator.comparing(Point::getY));
		int ymax = points.get(points.size() - 1).y;
	
		points.clear();
		
		return new Point(xmax, ymax);
	}
	
	/**
	 * form a neighbourhood rectangle
	 * @param a top left point of the rectangle
	 * @param b bottom right point of the rectangle
	 * @return a new rectangle made of these 2 points
	 */
	public Rectangle getNeighbourhoodDelimits(Point a, Point b) {
		a = this.getTopLeftPoint();
		b = this.getBottomRightPoint();
	
		return new Rectangle(a.x, a.y, b.x - a.x, b.y - a.y);
	}
	
	/**
	 * create an image of the town
	 * It contains land, water the neighbourhood and amenities representaions
	 * @param path the image path
	 * @param neighbourhoods the set of neighbourhoods existing in city
	 */
	public static void diplayCityWithNeighbourhoods(String path, Set<Neighbourhood> neighbourhoods) {    
		final int WIDTH = 512;
		final int HEIGHT = 512;
		
		BufferedImage cityDisplay = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
				  
		// Create a graphics which can be used to draw into the buffered image
		Graphics2D g2dCover = cityDisplay.createGraphics();
		  
		g2dCover.setColor(Color.white);
		g2dCover.fillRect(0, 0, WIDTH, HEIGHT);
		         
		for (int i = 0; i < WIDTH; i++){
			
			for (int j = 0; j < HEIGHT; j++){
		            	 
				if (i >= 230 && i <= 310 && j >= 250 && j <= 340) {
					g2dCover.setColor(Color.blue);
					g2dCover.drawLine(i, j, i, j);
				}
				
				else {
					g2dCover.setColor(Color.green);
					g2dCover.drawLine(i, j, i, j);
				}
		                
			}
		}
		        
		for (Neighbourhood neighbourhood: neighbourhoods) {
	
			Rectangle rectangle = neighbourhood.getNeighbourhoodDelimits(
					neighbourhood.getTopLeftPoint(),
					neighbourhood.getBottomRightPoint());
			
			g2dCover.setColor(Color.yellow);
			g2dCover.fill(rectangle);
			 
			for (Amenity amenity: neighbourhood.getAmenities()) {
				int amenityTopLeftX = amenity.getTopLeftX();
				int amenityTopLeftY = amenity.getTopLeftY();
				
				int amenityWidth = 2 * (amenity.getCenterX() - amenity.getTopLeftX());
				int amenityHeight = 2 * (amenity.getCenterY() - amenity.getTopLeftY());
				
				Image image = null;
					
				if(amenity.getType().getIcon() == null) {
					g2dCover.setColor(Color.red);
					g2dCover.fillRect(amenity.getTopLeftX(), amenity.getTopLeftY(), 
							amenityWidth, amenityHeight);
				}
					
				else {
						
					try {
						image = ImageIO.read(new File(amenity.getType().getIcon()));
						g2dCover.drawImage(image, amenityTopLeftX, amenityTopLeftY,
								amenityWidth, amenityHeight, Color.white, null);
//							
					} catch (IOException e) {
						System.out.println("Icon not found");	
						try {
							image = ImageIO.read(new File(".//icons//undefined.png"));
							g2dCover.drawImage(image, amenityTopLeftX, amenityTopLeftY,
									amenityWidth, amenityHeight, Color.white, null);
						} catch (IOException e1) {
								System.out.println(e1.getMessage());
						}
					}
				}	
			}
			
		}      
		g2dCover.dispose();
		       
		// Save as JPEG
		File file = new File(path);
		       
		try {
			ImageIO.write(cityDisplay, "jpg", file); //save the image
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		                 
	}
	
	/**
	 * create a JTable of the existing neighbourhoods in city
	 * @param neighbourhoods the set of existing neighbourhoods
	 */
	public void createTable(Set<Neighbourhood> neighbourhoods) {
		JTable jTable1 = new javax.swing.JTable();

		jTable1.setModel(new javax.swing.table.DefaultTableModel(
				new Object [][] {

	            },
	            new String [] {
	                "Id", "Name", "Rating", "CityId"
	            }
	     ));
		
		DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        Object rowData[] = new Object[4];
      
        for (Neighbourhood neighbourhood: neighbourhoods) {
        	rowData[0] = neighbourhood.getId();
        	rowData[1] = neighbourhood.getName();
        	rowData[2] = neighbourhood.getRating();
        	rowData[3] = neighbourhood.getCityId();
        	model.addRow(rowData);
        }
        
        JOptionPane.showMessageDialog(null, new JScrollPane(jTable1));
      
	}
}