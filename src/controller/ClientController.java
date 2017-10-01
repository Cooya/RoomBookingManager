package controller;

import java.io.IOException;

import network.ClientNetworkInterface;
import network.NetworkMessage;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import model.Account;
import model.Booking;
import model.DataManager;
import model.Room;
import view.ActionId;
import view.GUI;

public class ClientController {
	private static ClientNetworkInterface client;
	private static GUI gui;
	private static DataManager dm;
	private static SimpleStringProperty message;
	private static SimpleBooleanProperty error;

	public static void run() {
		dm = new DataManager();
		message = new SimpleStringProperty();
		error = new SimpleBooleanProperty(false);
		gui = new GUI(message, error, dm.getCurrentUser(), dm.getRooms(), dm.getBookings(), dm.getAccounts());
		
		try {
			client = new ClientNetworkInterface();
		} catch(IOException e) {
			e.printStackTrace();
			error.setValue(true);
			message.setValue("The connection to server has failed.");
		}
		
		gui.runThread();
	}

	public static void actionProcessor(ActionId actionId, Object... args) {
		new Thread() {
			@Override
			public void run() {
				switch(actionId) {
					case GUI_READY: launch(); break;
					case EXIT: System.exit(0); break;
					case LOGIN_BUTTON: openLogInDialog(); break;
					case LOGIN_SUBMIT: logIn((String) args[0], (String) args[1]); break;
					case LOGOUT_BUTTON: logOut(); break;
					case REGISTER_BUTTON: openRegisterDialog(); break;
					case REGISTER_SUBMIT: register((Account) args[0], (String) args[1]); break;
					case ADD_ROOM_BUTTON: openAddRoomDialog(); break;
					case ADD_ROOM_SUBMIT: addRoom((Room) args[0]); break;
					case BOOK_ROOM_BUTTON: openRoomBookingDialog(args); break;
					case BOOK_ROOM_SUBMIT: bookRoom((Booking) args[0]); break;
					case CONFIRM_BOOKING_BUTTON: confirmBooking((Booking) args[0]); break;
					case CONFIRM_BOOKING_SUBMIT: break;
					case CREATE_ADMIN_BUTTON: openCreateAdminDialog(); break;
					case CREATE_ADMIN_SUBMIT: register((Account) args[0], (String) args[1]);
					case DELETE_ADMIN_BUTTON: break;
					case DELETE_ADMIN_SUBMIT: break;
				}
			}
		}.start();
	}

	/* OPEN WINDOWS */
	
	private static void launch() {
		gui.display(!error.getValue() ? "loginDialog" : "infoDialog");
	}
	
	private static void openLogInDialog() {
		gui.dismiss("registerDialog");
		gui.display("loginDialog");
	}

	private static void openRegisterDialog() {
		gui.dismiss("loginDialog");
		gui.display("registerDialog", false);
	}

	private static void openAddRoomDialog() {
		gui.display("roomDialog");
	}

	private static void openRoomBookingDialog(Object... args) {
		gui.display("bookingDialog", args);
	}

	private static void openCreateAdminDialog() {
		gui.display("registerDialog", true);
	}

	/* USER ACTIONS */
	
	private static boolean checkResponse(NetworkMessage response) {
		if(response.getId() == NetworkMessage.SERVER_MESSAGE) {
			message.setValue((String) response.getContent()[0]);
			gui.display("infoDialog");
			return true;
		}
		return false;
	}
	
	private static void logIn(String login, String password) {
		try {
			NetworkMessage response = client.sendRequest(new NetworkMessage(NetworkMessage.LOG_IN, login, password));
			if(checkResponse(response))
				return;
			Account account = (Account) response.getContent()[0];
			if(account != null) { // connected
				dm.setCurrentUser(account);
				getTables();
				gui.dismiss("loginDialog");
				gui.display("tableDialog");
			}
			else {
				message.setValue("Invalid credentials.");
				gui.display("infoDialog");
			}
		} catch(Exception e) {
			e.printStackTrace();
			message.setValue(e.getMessage());
			gui.display("infoDialog");
		}
	}
	
	private static void logOut() {
		try {
			NetworkMessage response = client.sendRequest(new NetworkMessage(NetworkMessage.LOG_OUT));
			if(checkResponse(response))
				return;
			if((boolean) response.getContent()[0]) {
				dm.setCurrentUser(null);
				gui.dismiss("tableDialog");
				gui.display("loginDialog");
			}
			else {
				message.setValue("Apparently, it does not work...");
				gui.display("infoDialog");
			}
		} catch(Exception e) {
			e.printStackTrace();
			message.setValue(e.getMessage());
			gui.display("infoDialog");
		}
	}

	private static void register(Account account, String password) {
		boolean isConnected = dm.getCurrentUser().getValue() != null;
		try {
			NetworkMessage response = client.sendRequest(new NetworkMessage(NetworkMessage.REGISTER, account, password));
			if(checkResponse(response))
				return;
			account = (Account) response.getContent()[0];
			if(account != null) {
				if(!isConnected) {
					dm.setCurrentUser(account);
					getTables();
					gui.dismiss("registerDialog");
					gui.display("tableDialog");
				}
				else {
					dm.addAccount(account);
					gui.dismiss("registerDialog");
				}
			}
			else {
				message.setValue("Login already used.");
				gui.display("infoDialog");
			}
		} catch(Exception e) {
			e.printStackTrace();
			message.setValue(e.getMessage());
			gui.display("infoDialog");
		}
	}

	private static void addRoom(Room room) {
		try {
			NetworkMessage response = client.sendRequest(new NetworkMessage(NetworkMessage.ADD_ROOM, room));
			if(checkResponse(response))
				return;
			room = (Room) response.getContent()[0];
			if(room != null) {
				dm.addRoom(room);
				gui.dismiss("roomDialog");
			}
			else {
				message.setValue("Room number already exists.");
				gui.display("infoDialog");
			}
		} catch(Exception e) {
			e.printStackTrace();
			message.setValue(e.getMessage());
			gui.display("infoDialog");
		}
	}

	private static void bookRoom(Booking booking) {
		try {
			NetworkMessage response = client.sendRequest(new NetworkMessage(NetworkMessage.BOOK_ROOM, booking));
			if(checkResponse(response))
				return;
			booking = (Booking) response.getContent()[0];
			if(booking != null) {
				dm.addBooking(booking);
				gui.dismiss("bookingDialog");
			}
			else {
				message.setValue("Sorry, the room is already booked for this time interval.");
				gui.display("infoDialog");
			}
		} catch(Exception e) {
			e.printStackTrace();
			message.setValue(e.getMessage());
			gui.display("infoDialog");
		}
	}
	
	private static void confirmBooking(Booking booking) {
		try {
			NetworkMessage response = client.sendRequest(new NetworkMessage(NetworkMessage.CONFIRM_BOOKING, booking));
			if(checkResponse(response))
				return;
			if((boolean) response.getContent()[0]) {
				booking.setConfirmed(true);
				dm.updateBooking(booking);
			}
			else {
				message.setValue("Apparently, it does not work...");
				gui.display("infoDialog");
			}
		} catch(Exception e) {
			e.printStackTrace();
			message.setValue(e.getMessage());
			gui.display("infoDialog");
		}
	}
	
	private static void getTables() {
		try {
			NetworkMessage response = client.sendRequest(new NetworkMessage(NetworkMessage.GET_TABLES));
			if(checkResponse(response))
				return;
			Object[] tables = response.getContent();
			for(Room room : (Room[]) tables[0])
				dm.addRoom(room);
			for(Booking booking : (Booking[]) tables[1])
				dm.addBooking(booking);
			for(Account account : (Account[]) tables[2])
				dm.addAccount(account);
		} catch(Exception e) {
			e.printStackTrace();
			message.setValue(e.getMessage());
			gui.display("infoDialog");
		}
	}
}