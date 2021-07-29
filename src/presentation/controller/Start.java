package presentation.controller;

import presentation.client_management.ClientConnection;
import presentation.view.MainView;

public class Start {

	public static void main(String args[]) {
		MainView mainView = new MainView();
		MainController mainController = new MainController(mainView);
		
		ClientConnection.createSocket();
		
		mainView.setVisible(true);
	}
	
}


