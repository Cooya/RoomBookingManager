package controller;

import java.sql.SQLException;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import model.Account;
import model.Booking;
import model.DataManager;
import model.Room;
import view.GUI;

public class Controller {
	private static GUI gui;
	private static DataManager dm;
	private static SimpleStringProperty message;
	private static SimpleBooleanProperty error;

	public static void main(String[] args) {
		message = new SimpleStringProperty();
		error = new SimpleBooleanProperty(false);
		dm = new DataManager();
		gui = new GUI(message, error, dm.getCurrentUser(), dm.getRooms(), dm.getBookings(), dm.getAccounts());
		
		try {
			dm.connectToDatabase();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
			error.setValue(true);
			message.setValue("The SQL driver is missing.");
		} catch(SQLException e) {
			e.printStackTrace();
			error.setValue(true);
			message.setValue("Failed to connect to the database.");
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
					case LOGIN_SUBMIT: logIn((String) args[0], (String) args[1]); break;
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
	
	private static void logIn(String login, String password) {
		try {
			if(dm.logIn(login, password)) { // connected
				gui.dismiss("loginDialog");
				gui.display("tableDialog");
			}
			else {
				message.setValue("Invalid credentials.");
				gui.display("infoDialog");
			}
		} catch(SQLException e) {
			e.printStackTrace();
			message.setValue(e.getMessage());
			gui.display("infoDialog");
		}
	}

	private static void register(Account account, String password) {
		boolean isConnected = dm.getCurrentUser().getValue() != null;
		try {
			dm.createAccount(account, password);
			gui.dismiss("registerDialog");
			if(!isConnected)
				gui.display("tableDialog");
		} catch(SQLException e) {
			e.printStackTrace();
			message.setValue(e.getMessage());
			gui.display("infoDialog");
		}
	}

	private static void addRoom(Room room) {
		try {
			dm.createRoom(room);
			gui.dismiss("roomDialog");
		} catch(SQLException e) {
			e.printStackTrace();
			message.setValue(e.getMessage());
			gui.display("infoDialog");
		}
	}

	private static void bookRoom(Booking booking) {
		try {
			if(dm.createBooking(booking))
				gui.dismiss("bookingDialog");
			else {
				message.setValue("Sorry, the room is already booked for this time interval.");
				gui.display("infoDialog");
			}
		} catch(SQLException e) {
			e.printStackTrace();
			message.setValue(e.getMessage());
			gui.display("infoDialog");
		}
	}
	
	private static void confirmBooking(Booking booking) {
		try {
			booking.setConfirmed(true);
			dm.updateBooking(booking);
		} catch(SQLException e) {
			e.printStackTrace();
			message.setValue(e.getMessage());
			gui.display("infoDialog");
		}
	}
}