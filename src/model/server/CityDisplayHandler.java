package model.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class CityDisplayHandler implements Runnable{

	private Socket redirect;
	private ObjectInputStream redirectInputStream;
	private PrintWriter redirectOutput;
	
	private CityDisplayProcessing cityDisplayProcessing = new CityDisplayProcessing();
		
	public CityDisplayHandler(Socket redirect) throws IOException {
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
        		 
        		 String requestGetInfoByClickPosition = "/GET/cityLayer/imagePath/position";
        		 String requestGetNeighourhoodImage = "/GET/cityLayer/imagePath/:2";
        		 System.out.println("Header is: " + header);
  				
        		 if (header.equals(requestGetInfoByClickPosition)) {
        			 cityDisplayProcessing.giveAmenityOnClick(redirect, redirectInputStream, redirectOutput);	 
        		 }
        		 
        		 else if (header.equals(requestGetNeighourhoodImage)) {
        			 cityDisplayProcessing.showNeighbourhoods(redirect, redirectInputStream, redirectOutput);
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
