package model;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataManager {
	private ObservableList<Account> accounts;
	private ObservableList<Room> rooms;
	private ObservableList<Booking> bookings;
	
	public DataManager() {
		this.accounts = FXCollections.observableArrayList();
		this.rooms = FXCollections.observableArrayList();
		this.bookings = FXCollections.observableArrayList();
	}
	
	public ObservableList<Account> getAccounts() {
		return this.accounts;
	}
	
	public void storeAccounts(ResultSet set) throws SQLException {
		while(set.next())
			this.accounts.add(new Account(set.getString("name"), set.getString("login"), set.getInt("type")));
		set.close();
	}
	
	public ObservableList<Room> getRooms() {
		return this.rooms;
	}
	
	public void storeRooms(ResultSet set) throws SQLException {
		while(set.next())
			this.rooms.add(new Room(set.getInt("number"), set.getString("type"), set.getInt("size"), set.getBoolean("isAvailable")));
		set.close();
	}
	
	public ObservableList<Booking> getBookings() {
		return this.bookings;
	}
	
	public void storeBookings(ResultSet set) throws SQLException {
		while(set.next())
			this.bookings.add(new Booking(set.getInt("number"), set.getString("name"), set.getDate("dateFrom"), set.getDate("dateTo"), set.getBoolean("confirmed")));
		set.close();
	}
}