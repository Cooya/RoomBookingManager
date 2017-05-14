package model;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class DatabaseConnection {
	private Connection connection;
	
	protected void connect(String url, String login, String password) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		this.connection = DriverManager.getConnection(url, login, password);
	}
	
	protected void deconnect() throws SQLException {
		this.connection.close();
	}
	
	private int getResultSetSize(ResultSet set) throws SQLException {
		set.last();
		int size = set.getRow();
		set.beforeFirst();
		return size;
	}
	
	/* ACCOUNTS */
	
	protected Account createAccount(Account account, String password) throws SQLException {
		String query = "INSERT INTO account (name, login, password, type) VALUES (?, ?, ?, ?)";
		PreparedStatement st = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		st.setString(1, account.getName());
		st.setString(2, account.getLogin());
		st.setString(3, password);
		st.setInt(4, account.getAccountType());
		account.setId(st.executeUpdate());
		st.close();
		return account;
	}
	
	protected String getAccountNameById(int id) throws SQLException {
		String query = "SELECT name FROM account WHERE id = ?";
		PreparedStatement st = this.connection.prepareStatement(query);
		st.setInt(1, id);
		ResultSet result = st.executeQuery();
		String name = result.getString("name");
		st.close();
		return name;
	}
	
	protected Account logIn(String login, String password) throws SQLException {
		String query = "SELECT id, name, login, type FROM account WHERE login = ? AND password = ?";
		PreparedStatement st = this.connection.prepareStatement(query);
		st.setString(1, login);
		st.setString(2, password);
		ResultSet result = st.executeQuery();
		Account account = null;
		if(result.first())
			account = new Account(result.getInt("id"), result.getString("name"), result.getString("login"), result.getInt("type")); 
		st.close();
		return account;
	}
	
	protected Account[] getAllAccounts() throws SQLException {
		String query = "SELECT id, name, login, type FROM account";
		PreparedStatement st = this.connection.prepareStatement(query);
		ResultSet set = st.executeQuery();
		Account[] accounts = new Account[getResultSetSize(set)];
		for(int i = 0; set.next(); ++i)
			accounts[i] = new Account(set.getInt("id"), set.getString("name"), set.getString("login"), set.getInt("type"));
		st.close();
		return accounts;
	}
	
	/* ROOMS */
	
	protected Room createRoom(Room room) throws SQLException {
		String query = "INSERT INTO room (number, type, size, available) VALUES (?, ?, ?, ?)";
		PreparedStatement st = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		st.setInt(1, room.getNumber());
		st.setString(2, room.getType());
		st.setInt(3, room.getSize());
		st.setBoolean(4, room.isAvailable());
		room.setId(st.executeUpdate());
		st.close();
		return room;
	}
	
	protected Room[] getAllRooms() throws SQLException {
		String query = "SELECT id, number, type, size, available FROM room";
		PreparedStatement st = this.connection.prepareStatement(query);
		ResultSet set = st.executeQuery();
		Room[] rooms = new Room[getResultSetSize(set)];
		for(int i = 0; set.next(); ++i)
			rooms[i] = new Room(set.getInt("id"), set.getInt("number"), set.getString("type"), set.getInt("size"), set.getBoolean("available"));
		set.close();
		return rooms;
	}
	
	/* BOOKINGS */
	
	protected Booking createBooking(Booking booking) throws SQLException {
		String query = "INSERT INTO booking (roomId, accountId, dateFrom, dateTo, confirmed) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement st = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		st.setInt(1, booking.getRoomId());
		st.setInt(2, booking.getAccountId());
		st.setDate(3, booking.getDateFrom());
		st.setDate(4, booking.getDateTo());
		st.setBoolean(5, booking.isConfirmed());
		booking.setId(st.executeUpdate());
		st.close();
		return booking;
	}
	
	protected Booking[] getAllBookings() throws SQLException {
		String query = "SELECT booking.id, account.id, account.name, room.id, room.number, booking.dateFrom, booking.dateTo, booking.confirmed FROM booking "
			+ "INNER JOIN account ON booking.accountId = account.id "
			+ "INNER JOIN room ON booking.roomId = room.id";
		PreparedStatement st = this.connection.prepareStatement(query);
		ResultSet set = st.executeQuery();
		Booking[] bookings = new Booking[getResultSetSize(set)];
		for(int i = 0; set.next(); ++i)
			bookings[i] = new Booking(set.getInt("booking.id"), set.getInt("room.id"), set.getInt("number"), set.getInt("account.id"), set.getString("name"), set.getDate("dateFrom"), set.getDate("dateTo"), set.getBoolean("confirmed"));
		set.close();
		return bookings;
	}
	
	protected boolean isBookingsBetweenDates(int roomId, Date dateFrom, Date dateTo) throws SQLException {
		String query = "SELECT dateFrom, dateTo FROM booking "
			+ "WHERE roomId = ? AND (dateFrom BETWEEN ? AND ?) OR (dateTo BETWEEN ? AND ?)";
		PreparedStatement st = this.connection.prepareStatement(query);
		st.setInt(1, roomId);
		st.setDate(2, dateFrom);
		st.setDate(3, dateTo);
		st.setDate(4, dateFrom);
		st.setDate(5, dateTo);
		ResultSet set = st.executeQuery();
		boolean isResult = set.first();
		st.close();
		return isResult;
	}
	
	protected boolean updateBooking(Booking booking) throws SQLException {
		String query = "UPDATE booking SET dateFrom = ?,  dateTo = ?, confirmed = ? WHERE id = ?";
		PreparedStatement st = this.connection.prepareStatement(query);
		st.setDate(1, booking.getDateFrom());
		st.setDate(2, booking.getDateTo());
		st.setBoolean(3, booking.isConfirmed());
		st.setInt(4, booking.getId());
		int nbRows = st.executeUpdate();
		st.close();
		return nbRows == 1;
	}
}