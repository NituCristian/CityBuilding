package model.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class TypeHandler implements Runnable{

	private Socket redirect;
	private ObjectInputStream redirectInputStream;
	private PrintWriter redirectOutput;
	
	private TypeProcessing typeProcessing = new TypeProcessing();
		
	public TypeHandler(Socket redirect) throws IOException {
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
        	
        		 String requestGetTypes = "/GET/type";
        		 String requestPostTypes = "/POST/type";
        		 String requestUpdateTypes = "/POST/type/:id";
        		 String requestDeleteTypes = "/DELETE/type/:id";
          	
        		 if(header.equals(requestGetTypes)) {
        			 typeProcessing.showTypesProcessing(redirect, redirectInputStream, redirectOutput);
        		 }
        		 
        		 else if(header.equals(requestPostTypes)){
        			 typeProcessing.typeInsertProcessing(redirect, redirectInputStream, redirectOutput);
        		 }
        		 
        		 else if(header.equals(requestUpdateTypes)) {
        			 typeProcessing.typeUpdateProcessing(redirect, redirectInputStream, redirectOutput);
        		 }
        		 
        		 else if(header.equals(requestDeleteTypes)) {
        			 typeProcessing.typeDeleteProcessing(redirect, redirectInputStream, redirectOutput);
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
