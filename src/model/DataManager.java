package model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataManager {
	private ObservableList<Account> accounts;
	private ObservableList<Room> rooms;
	private ObservableList<Booking> bookings;
	private SimpleObjectProperty<Account> currentUser;
	
	public DataManager() {
		this.accounts = FXCollections.observableArrayList();
		this.rooms = FXCollections.observableArrayList();
		this.bookings = FXCollections.observableArrayList();
		this.currentUser = new SimpleObjectProperty<Account>();
	}
	
	public ObservableList<Account> getAccounts() {
		return this.accounts;
	}
	
	public ObservableList<Room> getRooms() {
		return this.rooms;
	}
	
	public ObservableList<Booking> getBookings() {
		return this.bookings;
	}
	
	public ObservableValue<Account> getCurrentUser() {
		return this.currentUser;
	}
	
	public void setCurrentUser(Account account) {
		this.currentUser.setValue(account);
	}

	public void addAccount(Account account) {
		this.accounts.add(account);
	}
	
	public void addRoom(Room room) {
		this.rooms.add(room);
	}
	
	public void addBooking(Booking booking) {
		this.bookings.add(booking);
	}
	
	public void updateBooking(Booking booking) {
		this.bookings.set(this.bookings.indexOf(booking), booking); // for refresh the TableView
	}
}