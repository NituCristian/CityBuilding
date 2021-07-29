package model.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import model.server.ServerManagement;

public class Intent {
	private String entity;
	private String action;
	public static Map<Intent, Integer> possibleIntents = new HashMap<>();
	
	public Intent() {
		
	}
	
	/**
	 * create an object of type intent
	 * @param entity the entity we operate on
	 * @param action the operation made
	 */
	public Intent(String entity, String action) {
		this.entity = entity;
		this.action = action;
	}
	
	public String getEntity() {
		return this.entity;
	}
	
	public String getAction() {
		return this.action;
	}
	
	/**
	 * construct the map of the intents (key = intent object; value = agent port)
	 */
	public static void constructPossibleIntents() {
		possibleIntents.put(new Intent("click", "show"), ServerManagement.getCityDisplayPort());
		possibleIntents.put(new Intent("neighbourhood", "display"), ServerManagement.getCityDisplayPort());
		
		possibleIntents.put(new Intent("neighbourhood", "show"), ServerManagement.getNeighbourhoodPort());
		possibleIntents.put(new Intent("city", "evaluate"), ServerManagement.getCityPort());
		possibleIntents.put(new Intent("neighbourhood", "evaluate"), ServerManagement.getNeighbourhoodPort());
		
		possibleIntents.put(new Intent("complaint", "make"), ServerManagement.getCityPort());
		possibleIntents.put(new Intent("complaint", "resolve"), ServerManagement.getCityPort());
		possibleIntents.put(new Intent("complaint", "show"), ServerManagement.getCityPort());
		possibleIntents.put(new Intent("amenity", "find"), ServerManagement.getAmenityPort());
		possibleIntents.put(new Intent("amenity", "go"), ServerManagement.getAmenityPort());
				
		possibleIntents.put(new Intent("amenity", "build"), ServerManagement.getAmenityPort());
		possibleIntents.put(new Intent("amenity", "update"), ServerManagement.getAmenityPort());
		possibleIntents.put(new Intent("amenity", "delete"), ServerManagement.getAmenityPort());
		possibleIntents.put(new Intent("amenity", "show"), ServerManagement.getAmenityPort());
		possibleIntents.put(new Intent("rule", "show"), ServerManagement.getAmenityPort());
		possibleIntents.put(new Intent("rule", "propose"), ServerManagement.getAmenityPort());
		possibleIntents.put(new Intent("rule", "unpropose"), ServerManagement.getAmenityPort());
		
		possibleIntents.put(new Intent("contact", "add"), ServerManagement.getContactPort());
		possibleIntents.put(new Intent("contact", "update"), ServerManagement.getContactPort());
		possibleIntents.put(new Intent("contact", "delete"), ServerManagement.getContactPort());
		possibleIntents.put(new Intent("contact", "show"), ServerManagement.getContactPort());
		
		possibleIntents.put(new Intent("photo", "add"), ServerManagement.getPhotoPort());
		possibleIntents.put(new Intent("photo", "update"), ServerManagement.getPhotoPort());
		possibleIntents.put(new Intent("photo", "delete"), ServerManagement.getPhotoPort());
		possibleIntents.put(new Intent("photo", "show"), ServerManagement.getPhotoPort());
	
		possibleIntents.put(new Intent("type", "add"), ServerManagement.getTypePort());
		possibleIntents.put(new Intent("type", "update"), ServerManagement.getTypePort());
		possibleIntents.put(new Intent("type", "delete"), ServerManagement.getTypePort());
		possibleIntents.put(new Intent("type", "show"), ServerManagement.getTypePort());
		
	}
	
	/**
	 * get the port of the agent which can manage the specified intent
	 * @return the port where the agent is running or -1, if there is no agent to handle the intent
	 */
	public int getAgentPortForIntent() {
		Set<Map.Entry<Intent, Integer>> intentSet = possibleIntents.entrySet(); 
		
		int port = -1;
		
		for(Map.Entry<Intent, Integer> currentIntent : intentSet) {
			if(this.equals(currentIntent.getKey())) {
				port = currentIntent.getValue();
				break;
			}
		}
		
		return port;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!Intent.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        final Intent other = (Intent) obj;
        
        if (
        		(!(this.getAction().equalsIgnoreCase(other.getAction()))) ||
        		(!(this.getEntity().equalsIgnoreCase(other.getEntity())))
        	) {
        	return false;
        }
     
        return true;
    }
	
	
}
