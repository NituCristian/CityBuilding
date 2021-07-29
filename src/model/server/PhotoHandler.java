package model.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class PhotoHandler implements Runnable{

	private Socket redirect;
	private ObjectInputStream redirectInputStream;
	private PrintWriter redirectOutput;
	
	private PhotoProcessing photoProcessing = new PhotoProcessing();
		
	public PhotoHandler(Socket redirect) throws IOException {
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
        	
        		 String requestGetPhotos = "/GET/photo";
        		 String requestPostPhotos = "/POST/photo";
        		 String requestUpdatePhotos = "/POST/photo/:id";
        		 String requestDeletePhotos = "/DELETE/photo/:id";
          	
        		 if(header.equals(requestGetPhotos)) {
        			 photoProcessing.showPhotosProcessing(redirect, redirectInputStream, redirectOutput);
        		 }
        		 
        		 else if(header.equals(requestPostPhotos)){
        			 photoProcessing.photoInsertProcessing(redirect, redirectInputStream, redirectOutput);
        		 }
        		 
        		 else if(header.equals(requestUpdatePhotos)) {
        			 photoProcessing.photoUpdateProcessing(redirect, redirectInputStream, redirectOutput);
        		 }
        		 
        		 else if(header.equals(requestDeletePhotos)) {
        			 photoProcessing.photoDeleteProcessing(redirect, redirectInputStream, redirectOutput);
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
