package model.server;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServerManagement {
	private static final int CENTRAL_PORT = 4470; //central server port
	private static final int CONTACT_PORT = CENTRAL_PORT + 1;	//port of the contact agent
	private static final int PHOTO_PORT = CENTRAL_PORT + 2;	//port of the photo agent
	private static final int TYPE_PORT = CENTRAL_PORT + 3;	//port of the type amenity agent 
	private static final int CITY_DISPLAY_PORT = CENTRAL_PORT + 4;	//city display agent port
	private static final int NEIGHBOURHOOD_PORT = CENTRAL_PORT + 5;	//neighbourhood agent port
	private static final int CITY_PORT = CENTRAL_PORT + 6;	//city agent port
	private static final int AMENITY_PORT = CENTRAL_PORT + 7;	//amenity agent port
	
	private static ArrayList<ClientHandler> clients = new ArrayList<>();
	private ExecutorService pool = Executors.newCachedThreadPool();
	
	public static int getCentralServerPort() {
		return ServerManagement.CENTRAL_PORT;
	}
	
	public static int getContactPort() {
		return ServerManagement.CONTACT_PORT;
	}
	
	public static int getPhotoPort() {
		return ServerManagement.PHOTO_PORT;
	}
	
	public static int getTypePort() {
		return ServerManagement.TYPE_PORT;
	}
	
	public static int getCityDisplayPort() {
		return ServerManagement.CITY_DISPLAY_PORT;
	}
	
	public static int getNeighbourhoodPort() {
		return ServerManagement.NEIGHBOURHOOD_PORT;
	}
	
	public static int getCityPort() {
		return ServerManagement.CITY_PORT;
	}
	
	public static int getAmenityPort() {
		return ServerManagement.AMENITY_PORT;
	}
	
	
	public void parseActions() throws Exception{
		Selector selector = Selector.open();

		int[] ports = {CENTRAL_PORT, CONTACT_PORT, PHOTO_PORT, TYPE_PORT, 
				CITY_DISPLAY_PORT, NEIGHBOURHOOD_PORT, CITY_PORT, AMENITY_PORT};

		for (int port : ports) {
			//create a server on each port
			ServerSocketChannel server = ServerSocketChannel.open();
			server.configureBlocking(false);

			server.socket().bind(new InetSocketAddress(port));
			// we are only interested when accept evens occur on this socket
			server.register(selector, SelectionKey.OP_ACCEPT); 
		}

		//use the selector to redirect the client socket to the right server
		//Use an interator to go through the list
		//remove the iterator after a complete search, to be able to find the key again
		//when we identify the port, start a thread on the corresponding agent (or main server)
		while (selector.isOpen()) {
		   selector.select();
		   Set readyKeys = selector.selectedKeys();
		   Iterator iterator = readyKeys.iterator();

		   while (iterator.hasNext()) {
			   SelectionKey key = (SelectionKey) iterator.next();
		      
			   if (key.isAcceptable()) {

				   SocketChannel client = ((ServerSocketChannel)key.channel()).accept();
				   Socket socket = client.socket();	//get the client socket and check it's port
				   System.out.println("Port" + socket.getLocalPort());
		    	  
				   if (socket.getLocalPort() == CENTRAL_PORT) {
					   ClientHandler clientThread = new ClientHandler(socket);
					   clients.add(clientThread);
			            	
					   pool.execute(clientThread);
				   }
		    	  
				   else if (socket.getLocalPort() == CONTACT_PORT) {
					   ContactHandler contactHandler = new ContactHandler(socket);
		    		  
					   pool.execute(contactHandler);
				   }
				   
				   else if (socket.getLocalPort() == PHOTO_PORT) {
					   PhotoHandler photoHandler = new PhotoHandler(socket);
		    		  
					   pool.execute(photoHandler);
				   }
				   
				   else if (socket.getLocalPort() == TYPE_PORT) {
					   TypeHandler typeHandler = new TypeHandler(socket);
		    		  
					   pool.execute(typeHandler);
				   }
				   
				   else if (socket.getLocalPort() == CITY_DISPLAY_PORT) {
					   CityDisplayHandler cityDisplayHandler = new CityDisplayHandler(socket);
		    		  
					   pool.execute(cityDisplayHandler);
				   }
				   
				   else if (socket.getLocalPort() == NEIGHBOURHOOD_PORT) {
					   NeighbourhoodHandler neighbourhoodHandler = new NeighbourhoodHandler(socket);
		    		  
					   pool.execute(neighbourhoodHandler);
				   }
				   
				   else if (socket.getLocalPort() == CITY_PORT) {
					   CityHandler cityHandler = new CityHandler(socket);
					   
					   pool.execute(cityHandler);
				   }
				   
				   else if (socket.getLocalPort() == AMENITY_PORT) {
					   AmenityHandler amenityHandler = new AmenityHandler(socket);
					   
					   pool.execute(amenityHandler);
				   }
			   }
		      
			   iterator.remove(); 
		   }
		}
		
	}
	
	//start the servers
	public static void main(String args[]) throws Exception{
		 new ServerManagement().parseActions();
	}
	
}
