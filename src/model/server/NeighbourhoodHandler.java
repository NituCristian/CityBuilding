package model.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class NeighbourhoodHandler implements Runnable{

	private Socket redirect;
	private ObjectInputStream redirectInputStream;
	private PrintWriter redirectOutput;
	
	private NeighbourhoodProcessing neighbourhoodProcessing = new NeighbourhoodProcessing();
		
	public NeighbourhoodHandler(Socket redirect) throws IOException {
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
        		
        		 String requestGetNeighbourhoods = "/GET/neighbourhood";
        		 String requestGetNeighbourhoodRatingById = "/GET/neighbourhood/:id/rating";
	
        		 if(header.equals(requestGetNeighbourhoods)) {
        			 neighbourhoodProcessing.showNeighbourhoodsProcessing(redirect, redirectInputStream, redirectOutput);
        		 }
        		 
        		 else if(header.equals(requestGetNeighbourhoodRatingById)) {
        			 neighbourhoodProcessing.neighbourhoodRatingProcessing(redirect, redirectInputStream, redirectOutput);
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
