package controller;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
	private Connection connection;
	
	public void connect(String url, String login, String password) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		this.connection = DriverManager.getConnection(url, login, password);
	}
	
	public void deconnect() throws SQLException {
		this.connection.close();
	}
	
	public boolean createAccount(String name, String login, String password, int type) throws SQLException {
		String query = "INSERT INTO account (name, login, password, type) VALUES (?, ?, ?, ?)";
		PreparedStatement st = this.connection.prepareStatement(query);
		st.setString(1, name);
		st.setString(2, login);
		st.setString(3, password);
		st.setInt(4, type);
		int rowCount = st.executeUpdate();
		st.close();
		return rowCount == 1;
	}
	
	public String getAccountNameById(int id) throws SQLException {
		String query = "SELECT name FROM account WHERE id = ?";
		PreparedStatement st = this.connection.prepareStatement(query);
		st.setInt(1, id);
		ResultSet result = st.executeQuery();
		String name = result.getString("name");
		result.close();
		st.close();
		return name;
	}
	
	public boolean logIn(String login, String password) throws SQLException {
		String query = "SELECT id FROM account WHERE login = ? AND password = ?";
		PreparedStatement st = this.connection.prepareStatement(query);
		st.setString(1, login);
		st.setString(2, password);
		boolean result = st.execute();
		st.close();
		return result;
	}
	
	public ResultSet getAllAccounts() throws SQLException {
		String query = "SELECT (name, login, type) FROM account";
		PreparedStatement st = this.connection.prepareStatement(query);
		ResultSet result = st.executeQuery();
		st.close();
		return result;
	}
	
	public boolean createRoom(int number, String type, int size, boolean isAvailable) throws SQLException {
		String query = "INSERT INTO room (number, type, size, available) VALUES (?, ?, ?, ?)";
		PreparedStatement st = this.connection.prepareStatement(query);
		st.setInt(1, number);
		st.setString(2, type);
		st.setInt(3, size);
		st.setBoolean(4, isAvailable);
		int rowCount = st.executeUpdate();
		st.close();
		return rowCount == 1;
	}
	
	public ResultSet getAllRooms() throws SQLException {
		String query = "SELECT (number, type, size, available) FROM room";
		PreparedStatement st = this.connection.prepareStatement(query);
		ResultSet result = st.executeQuery();
		st.close();
		return result;
	}
	
	public boolean createBooking(int roomId, int accountId, Date dateFrom, Date dateTo, boolean isConfirmed) throws SQLException {
		String query = "INSERT INTO booking (roomId, accountId, dateFrom, dateTo, confirmed) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement st = this.connection.prepareStatement(query);
		st.setInt(1, roomId);
		st.setInt(2, accountId);
		st.setDate(3, dateFrom);
		st.setDate(4, dateTo);
		st.setBoolean(5, isConfirmed);
		int rowCount = st.executeUpdate();
		st.close();
		return rowCount == 1;
	}
	
	public ResultSet getAllBookings() throws SQLException {
		String query = "SELECT (account.name, room.number, booking.dateFrom, booking.dateTo, booking.confirmed) FROM booking"
			+ "INNER JOIN account ON booking.accountId = account.id"
			+ "INNER JOIN room ON booking.roomId = room.id";
		PreparedStatement st = this.connection.prepareStatement(query);
		ResultSet result = st.executeQuery();
		st.close();
		return result;
	}
	
	public ResultSet getBookingsBetweenDates(int number, Date dateFrom, Date dateTo) throws SQLException {
		String query = "SELECT (dateFrom, dateTo) FROM booking "
			+ "WHERE roomId = ? AND (dateFrom BETWEEN ? AND ?) OR (dateTo BETWEEN ? AND ?)";
		PreparedStatement st = this.connection.prepareStatement(query);
		st.setDate(1, dateFrom);
		st.setDate(2, dateTo);
		st.setDate(3, dateFrom);
		st.setDate(4, dateTo);
		ResultSet result = st.executeQuery();
		st.close();
		return result;
	}
}