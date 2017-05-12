package controller;

import java.sql.SQLException;

import model.Account;
import model.DataManager;
import view.GUI;

public class Controller {
	private static final String dbUrl = "jdbc:mysql://nicodev.fr/tobby";
	private static final String dbLogin = "tobby";
	private static final String dbPassword = "tobby";
	
	private static DatabaseConnection db;
	private static DataManager dm;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		db.connect(dbUrl, dbLogin, dbPassword);
		dm = new DataManager();
		GUI.display();
	}
	
	public boolean logIn(String login, String password) throws SQLException {
		return db.logIn(login, password);
	}
	
	public boolean register(String name, String login, String password) throws SQLException {
		return db.createAccount(name, login, password, Account.USER);
	}
	
	public void listRooms() throws SQLException {
		dm.storeAccounts(db.getAllAccounts());
	}
}