package model.business;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import javax.persistence.*;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@Entity
@Table(name = "City")
public class City implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "Name", nullable = false, length = 255)
	private String name;
	
	@Column(name = "Surface", nullable = false)
	private float surface;
	
	@Column(name = "Population", nullable = false)
	private int population;
	
	@Column(name = "Altitude", nullable = false)
	private int altitude;
	
	@Column(name = "YearFounded", nullable = false)
	private int yearFounded;
	
	@Column(name = "Lat")
	private float latitude;
	
	@Column(name = "Long")
	private float longitude;
	
	@Column(name = "TopLeftX", nullable = false)
	private int topLeftX;
	
	@Column(name = "TopLeftY", nullable = false)
	private int topLeftY;
	
	@Column(name = "CenterX", nullable = false)
	private int centerX;
	
	@Column(name = "CenterY", nullable = false)
	private int centerY;
	
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="CityId")
    @OrderBy("Id")
	private Set<Neighbourhood> neighbourhoods;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="CityId")
	private Set<CityLayer> cityLayers;
	
    private static ArrayList<String> complains;
    
	public City() {
		
	}
	
	/**
	 * constructor 
	 * @param name city's name
	 * @param surface city's surface
	 * @param population city's population
	 * @param altitude city's altitude
	 * @param yearFounded the year when the city was founded
	 * @param latitude city's latitude
	 * @param longitude city's longitude
	 * @param topLeftX city's top left x coordinate in picture
	 * @param topLeftY city's top left y coordinate in picture
	 * @param centerX city's center x coordinate
	 * @param centerY city's center y coordinate
	 */
	public City(String name, float surface, int population, int altitude, 
			int yearFounded, float latitude, float longitude, int topLeftX,
			int topLeftY, int centerX, int centerY) {
		
		this.name = name;
		this.surface = surface;
		this.population = population;
		this.altitude = altitude;
		this.yearFounded = yearFounded;
		this.latitude = latitude;
		this.longitude = longitude;
		this.topLeftX = topLeftX;
		this.topLeftY = topLeftY;
		this.centerX = centerX;
		this.centerY = centerY;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public float getSurface() {
		return surface;
	}

	public int getPopulation() {
		return population;
	}

	public int getAltitude() {
		return altitude;
	}

	public int getYearFounded() {
		return yearFounded;
	}

	public float getLat() {
		return latitude;
	}

	public float getLong() {
		return longitude;
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
	
	public Set<Neighbourhood> getNeighbourhoods() {
		return neighbourhoods;
	}

	public Set<CityLayer> getCityLayers() {
		return cityLayers;
	}

	/**
	 * determine the city rating
	 * the average of every neighbourhood rating
	 * @return the city rating
	 */
	public float calculateRating() {
		
		//compute city rating
		Collection<Neighbourhood> copyList = null;
		float result = 0.0f;
		copyList = Collections.unmodifiableCollection(this.neighbourhoods);
		
		Iterator<Neighbourhood> iterator = copyList.iterator();
		 
		while (iterator.hasNext())
	    {
	       result += iterator.next().getRating();
	    }
		
		return result/copyList.size();
	}
	

	/**
	 * add a new complaint
	 * @param text the complaint a user wants to add
	 */
	public static void addComplaint(String text) {
	
		try(FileWriter fw = new FileWriter("logs\\complaints.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
		{
			out.println(text);
		
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
		
	/**
	 * give the complaints the citizens made
	 * @return an arraylist containing the complaints
	 */
	public static ArrayList<String> getCityComplaints(){
		try {
			City.complains = new ArrayList<>();
			File myObj = new File("logs\\complaints.txt");
			Scanner myReader = new Scanner(myObj);
			
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
		        complains.add(data);
			}
		    
			myReader.close();
			return complains;
		    
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * remove the complaint by the id number
	 * @param id the id of the complaint
	 */
	public static void removeComplaint(int id) {

		try {
			String file = "logs\\complaints.txt";
			File inFile = new File(file);

			if (!inFile.isFile()) {
				System.out.println("Parameter is not an existing file");
				return;
			}

			 //Construct the new file that will later be renamed to the original filename.
			File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

			BufferedReader br = new BufferedReader(new FileReader(file));
			PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

			String line = null;

			//Read from the original file and write to the new
			//unless content matches data to be removed.
			int pos = 1;
			while ((line = br.readLine()) != null) {
		    
				if(pos != id) {
					pw.println(line);
					pw.flush();
				}	
		    	  
				pos++;
				
			}
			pw.close();
			br.close();

			//Delete the original file
			if (!inFile.delete()) {
				System.out.println("Could not delete file");
				return;
			}

			//Rename the new file to the filename the original file had.
			if (!tempFile.renameTo(inFile)) {
				System.out.println("Could not rename file");
			}
			
		}
		catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		}
		catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
	 
	/**
	 * create a JTable containing the id of the complaint and its description
	 * @param complaints the existing complaints
	 */
	 public static void createComplaintsTable(ArrayList<String> complaints) {
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
		 for (String complaint: complaints) {
			 rowData[0] = position;
			 rowData[1] = complaint;
	        	
			 model.addRow(rowData);
	        	position++;
		 }
	        
		 JOptionPane.showMessageDialog(null, new JScrollPane(jTable));
	      
	 }
	
	
}
