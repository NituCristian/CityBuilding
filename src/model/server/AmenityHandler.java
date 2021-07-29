package model.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class AmenityHandler implements Runnable{

	private Socket redirect;
	private ObjectInputStream redirectInputStream;
	private PrintWriter redirectOutput;
	
	private AmenityProcessing amenityProcessing = new AmenityProcessing();
	private CityManagementProcessing cityManagementProcessing = new CityManagementProcessing();	
	
	
	public AmenityHandler(Socket redirect) throws IOException {
		// TODO Auto-generated constructor stub
		this.redirect = redirect;
		redirectInputStream = new ObjectInputStream(redirect.getInputStream());
        redirectOutput = new PrintWriter(redirect.getOutputStream(), true);
	}

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
        	while(true) {
        	
        		 String header = (String) redirectInputStream.readObject();
        		
        		 String requestSearchAmenityByName = "/GET/amenity/find/:name";
        		 String requestGoToAmenityByName = "/GET/amenity/go/:name";
        		 
        		 String requestBuildAmenity = "/POST/amenity";
        		 String requestUpdateAmenity = "/POST/amenity/:id";
        		 String requestDeleteAmenity = "/DELETE/amenity/:id";
        		 String requestShowAmenities = "/GET/amenity";
        		 
        		 String requestShowRules = "/GET/rule";
        		 String requestProposeRule = "/POST/rule";
        		 String requestUnproposeRule = "/DELETE/rule/:id";
        		
        		 if(header.equals(requestSearchAmenityByName)) {
        			 cityManagementProcessing.findPlaceProcessing(redirect, redirectInputStream, redirectOutput);
        		 }
  				
        		 else if(header.equals(requestGoToAmenityByName)) {
        			 cityManagementProcessing.goToPlaceProcessing(redirect, redirectInputStream, redirectOutput);
        		 }
  				
        		 else if(header.equals(requestBuildAmenity)) {
        			 amenityProcessing.amenityInsertProcessing(redirect, redirectInputStream, redirectOutput);
        		 }
        		 
        		 else if(header.equals(requestUpdateAmenity)) {
        			 amenityProcessing.amenityUpdateProcessing(redirect, redirectInputStream, redirectOutput);
        		 }
  			
        		 else if(header.equals(requestDeleteAmenity)) {
        			 amenityProcessing.amenityDeleteProcessing(redirect, redirectInputStream, redirectOutput);
        		 }
  				
        		 else if(header.equals(requestShowAmenities)) {
        			 amenityProcessing.showAmenitiesProcessing(redirect, redirectInputStream, redirectOutput);
        		 }
  				
        		 else if(header.equals(requestShowRules)) {
        			 amenityProcessing.showImplementedRulesProcessing(redirect, redirectInputStream, redirectOutput);
        		 }
  				
        		 else if(header.equals(requestProposeRule)) {
        			 amenityProcessing.addRuleProcessing(redirect, redirectInputStream, redirectOutput);
        		 }
  				
        		 else if(header.equals(requestUnproposeRule)) {
        			 amenityProcessing.removeUnimplementedProcessing(redirect, redirectInputStream, redirectOutput);
        		 }
        	}
        	
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
        	System.out.println(e.getMessage());
			e.printStackTrace();
			
        } catch (IOException e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} finally {
			try {
				redirectInputStream.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			redirectOutput.close();
		}
		
	}

}
