package presentation.client_management;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import model.server.ServerManagement;

public class ClientConnection {
	private static final int PORT = ServerManagement.getCentralServerPort();
	private static Socket socket;
	private static ObjectOutputStream outputStream;
	private static BufferedReader response;
	
	/**
	 * create a socket for a new client
	 */
	public static void createSocket() {
		try { 
			ClientConnection.socket = new Socket("localhost", ClientConnection.PORT);
			ClientConnection.socket.setSoTimeout(0);
			ClientConnection.socket.setKeepAlive(true);
			
			ClientConnection.outputStream = new ObjectOutputStream(socket.getOutputStream());
			ClientConnection.response = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			
		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * close the client socket
	 */
	public static void closeConnections() {
		try {
			ClientConnection.socket.close();
			ClientConnection.outputStream.close();
			ClientConnection.response.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Socket getSocket(){		
		return socket;
	}
	
	public static ObjectOutputStream getOutputStream() {
		return outputStream;
	}
	
	public static BufferedReader getResponse() {
		return response;
	}
	
}
