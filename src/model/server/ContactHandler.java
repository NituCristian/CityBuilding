package model.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ContactHandler implements Runnable{

	private Socket redirect;
	private ObjectInputStream redirectInputStream;
	private PrintWriter redirectOutput;
	
	private ContactProcessing contactProcessing = new ContactProcessing();
		
	public ContactHandler(Socket redirect) throws IOException {
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
        		
        		 String requestGetContacts = "/GET/contact";
        		 String requestPostContacts = "/POST/contact";
        		 String requestUpdateContacts = "/POST/contact/:id";
        		 String requestDeleteContacts = "/DELETE/contact/:id";
          
        		 if(header.equals(requestGetContacts)) {
        			 contactProcessing.showContactsProcessing(redirect, redirectInputStream, redirectOutput);
        		 }
        		 
        		 else if(header.equals(requestPostContacts)){
        			 contactProcessing.contactInsertProcessing(redirect, redirectInputStream, redirectOutput);
        		 }
        		 
        		 else if(header.equals(requestUpdateContacts)) {
        			 contactProcessing.contactUpdateProcessing(redirect, redirectInputStream, redirectOutput);
        		 }
        		 
        		 else if(header.equals(requestDeleteContacts)) {
        			 contactProcessing.contactDeleteProcessing(redirect, redirectInputStream, redirectOutput);
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
