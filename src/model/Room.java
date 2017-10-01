package model;

import java.io.Serializable;

public class Room implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int number;
	private String type;
	private int size;
	private boolean available;
	
	public Room(int id, int number, String type, int size, boolean available) {
		this.id = id;
		this.number = number;
		this.type = type;
		this.size = size;
		this.available = available;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public boolean isAvailable() {
		return this.available;
	}
	
	public String getAvailable() {
		return this.available ? "yes" : "no";
	}
	
	public void setAvailable(boolean available) {
		this.available = available;
	}
}