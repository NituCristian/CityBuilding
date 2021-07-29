package model.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONObject;

import model.service.Intent;

public class ClientHandler implements Runnable{

	private Socket client;
	private ObjectInputStream inputStream;
	private PrintWriter output;
	
	private static Socket redirect;
	private static BufferedReader redirectBufferedReader;
	private static ObjectOutputStream redirectOutputStream;
	
	
	/**
	 * create objectInputStream and printWriter for the client socket
	 * @param clientSocket the client socket
	 * @throws IOException IOException object
	 */
	public ClientHandler(Socket clientSocket) throws IOException {
		// TODO Auto-generated constructor stub
		this.client = clientSocket;
		inputStream = new ObjectInputStream(client.getInputStream());
        output = new PrintWriter(client.getOutputStream(), true);
	}
	
	/**
	 * check whether the central server already has a connection to the agent
	 * otherwise, it creates a socket to the agent which can handle the intent
	 * @param port the port of the server where the agent is running
	 * @throws UnknownHostException unknownHostException object
	 * @throws IOException IOException object
	 */
	public void getRedirectSocket(int port) throws UnknownHostException, IOException {
		
		if (redirect == null || redirect.getLocalPort() != port) {
			redirect = new Socket("localhost", port);
			redirect.setSoTimeout(0);
			redirect.setKeepAlive(true);
 		
			redirectOutputStream = new ObjectOutputStream(redirect.getOutputStream());
			redirectBufferedReader = new BufferedReader(new InputStreamReader(
					redirect.getInputStream()));	
		}
	}
	
	
	/**
	 * get the type of the request
	 * @param request the request send by the client
	 * @return the request type
	 */
	public String getRequestType(String request) {
		String[] tokens = request.split("/");

		return tokens[1];
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
        	while (true) {
        		
        		String header = (String) inputStream.readObject();	
        		String body = (String) inputStream.readObject();
        		
        		JSONObject bodyJson = new JSONObject(body);
        		
        		JSONObject intentJson = bodyJson.getJSONObject("intentObject");
        		
        		Intent intent = new Intent(intentJson.getString("entity"), intentJson.getString("action"));
        		
        		bodyJson.remove("intentObject");	//we don't need to send the intent to the agent handler
        		
        		String requestType = getRequestType(header);	//get the endpoint 
        		
        		Intent.constructPossibleIntents();	//get the existent intents
        		int port = intent.getAgentPortForIntent();	//get the port where the agent is running 
        		
        		//if the intent is not a valid one, mark it
        		if(port == -1) {
        			output.println("Invalid intent");	
        		}
        		
        		else {
        		
        			getRedirectSocket(port);	//obtain the socket for the agent
        			
        			try {	
        				//send the header and the body of the request to the agent
        				redirectOutputStream.writeObject(header);
        				redirectOutputStream.writeObject(bodyJson.toString());
        				
        				String responseText = redirectBufferedReader.readLine();	//get the resp from the agent
     				
        				output.println(responseText);	//send the response back to the client
        			
        			} catch (IOException e1) {
        				System.out.println(e1.getMessage());
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			} catch (Exception e) {
        				System.out.println(e.getMessage());
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        		
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
				inputStream.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			output.close();
		}
		
	}

}
