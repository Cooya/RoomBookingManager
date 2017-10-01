package model;

import java.sql.SQLException;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataManager {
	private static final String DB_URL = "jdbc:mysql://localhost/room_booking_manager";
	private static final String DB_LOGIN = "root";
	
	private DatabaseConnection db;
	private ObservableList<Account> accounts;
	private ObservableList<Room> rooms;
	private ObservableList<Booking> bookings;
	private SimpleObjectProperty<Account> currentUser;
	
	public DataManager() {
		this.db = new DatabaseConnection();
		this.accounts = FXCollections.observableArrayList();
		this.rooms = FXCollections.observableArrayList();
		this.bookings = FXCollections.observableArrayList();
		this.currentUser = new SimpleObjectProperty<Account>();
	}
	
	public void connectToDatabase() throws ClassNotFoundException, SQLException {
		db.connect(DB_URL, DB_LOGIN, null);
	}
	
	public ObservableValue<Account> getCurrentUser() {
		return this.currentUser;
	}
	
	private void fillLocalDataCollection(int accountType) throws SQLException {
		Room[] rooms = this.db.getAllRooms();
		for(Room room : rooms)
			this.rooms.add(room);
		if(accountType == Account.ROOT || accountType == Account.ADMIN) {
			Booking[] bookings = this.db.getAllBookings();
			for(Booking booking : bookings)
				this.bookings.add(booking);
			Account[] accounts = this.db.getAllAccounts();
			for(Account account : accounts)
				this.accounts.add(account);
		}
	}
	
	/* GET OBSERVABLE LIST */
	
	public ObservableList<Account> getAccounts() {
		return this.accounts;
	}
	
	public ObservableList<Room> getRooms() {
		return this.rooms;
	}
	
	public ObservableList<Booking> getBookings() {
		return this.bookings;
	}
	
	/* DATA REQUESTS */
	
	public boolean logIn(String login, String password) throws SQLException {
		Account account = this.db.logIn(login, password);
		if(account != null) {
			this.currentUser.setValue(account);
			fillLocalDataCollection(account.getAccountType());
		}
		return account != null;
	}
	
	public void createAccount(Account account, String password) throws SQLException {
		account = this.db.createAccount(account, password);
		if(this.currentUser.getValue() == null) { // user not connected
			this.currentUser.setValue(account);
			fillLocalDataCollection(account.getAccountType());
		}
		else
			this.accounts.add(account);
	}
	
	public void createRoom(Room room) throws SQLException {
		this.rooms.add(this.db.createRoom(room));
	}
	
	public boolean createBooking(Booking booking) throws SQLException {
		if(this.db.isBookingsBetweenDates(booking.getRoomId(), booking.getDateFrom(), booking.getDateTo()))
			return false;
		this.bookings.add(this.db.createBooking(booking));
		return true;
	}
	
	public boolean updateBooking(Booking booking) throws SQLException {
		boolean success = this.db.updateBooking(booking);
		if(success)
			this.bookings.set(this.bookings.indexOf(booking), booking); // for refresh the TableView
		return success;
	}
}