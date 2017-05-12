package model;
import java.sql.Date;

public class Booking {
	private int roomNumber;
	private String accountName;
	private Date dateFrom;
	private Date dateTo;
	private boolean isConfirmed;
	
	public Booking(int roomNumber, String accountName, Date dateFrom, Date dateTo, boolean isConfirmed) {
		this.roomNumber = roomNumber;
		this.accountName = accountName;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.isConfirmed = isConfirmed;
	}
	
	public int getRoomNumber() {
		return this.roomNumber;
	}
	
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
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
		return this.isConfirmed;
	}
	
	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
}