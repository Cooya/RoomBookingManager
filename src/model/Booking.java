package model;

import java.io.Serializable;
import java.sql.Date;

public class Booking implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int roomId;
	private int roomNumber;
	private int accountId;
	private String accountName;
	private Date dateFrom;
	private Date dateTo;
	private boolean confirmed;
	
	public Booking(int id, int roomId, int roomNumber, int accountId, String accountName, Date dateFrom, Date dateTo, boolean confirmed) {
		this.id = id;
		this.roomId = roomId;
		this.roomNumber = roomNumber;
		this.accountId = accountId;
		this.accountName = accountName;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.confirmed = confirmed;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getRoomId() {
		return this.roomId;
	}
	
	public int getRoomNumber() {
		return this.roomNumber;
	}
	
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	public int getAccountId() {
		return this.accountId;
	}
	
	public String getAccountName() {
		return this.accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public Date getDateFrom() {
		return this.dateFrom;
	}
	
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	
	public Date getDateTo() {
		return this.dateTo;
	}
	
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	
	public boolean isConfirmed() {
		return this.confirmed;
	}
	
	public String getConfirmed() {
		return this.confirmed ? "yes" : "no";
	}
	
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}
}