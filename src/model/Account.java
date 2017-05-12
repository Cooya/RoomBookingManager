package model;
public class Account {
	public static int ROOT = 0;
	public static int ADMIN = 1;
	public static int USER = 2;
	
	private String name;
	private String login;
	private int type;
	
	public Account(String name, String login, int type) {
		this.name = name;
		this.login = login;
		this.type = type;
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
	
	public int getType() {
		return this.type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
}