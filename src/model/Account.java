package model;

import java.io.Serializable;

public class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final int ROOT = 0;
	public static final int ADMIN = 1;
	public static final int CLIENT = 2;
	
	private int id;
	private String name;
	private String login;
	private int type;
	
	public Account(int id, String name, String login, int type) {
		this.id = id;
		this.name = name;
		this.login = login;
		this.type = type;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLogin() {
		return this.login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getType() {
		switch(this.type) {
			case ROOT: return "root";
			case ADMIN: return "admin";
			case CLIENT: return "client";
		}
		return null;
	}
	
	public int getAccountType() {
		return this.type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
}