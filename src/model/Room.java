package model;
public class Room {
	private int number;
	private String type;
	private int size;
	private boolean isAvailable;
	
	public Room(int number, String type, int size, boolean isAvailable) {
		this.number = number;
		this.type = type;
		this.size = size;
		this.isAvailable = isAvailable;
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
		return this.isAvailable;
	}
	
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
}