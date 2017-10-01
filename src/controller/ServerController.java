package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.sql.SQLException;

import network.NetworkMessage;
import network.SocketClient;
import model.Account;
import model.Booking;
import model.DatabaseConnection;
import model.Room;

public class ServerController {
	private static final int PORT = 8080;
	private static final String DB_URL = "jdbc:mysql://localhost/room_booking_manager";
	private static final String DB_LOGIN = "root";
	private static final String DB_PASSWORD = null;
	private static ServerController self;

	private ServerSocket server;
	private DatabaseConnection db;

	private ServerController() {
		try {
			this.server = new ServerSocket(PORT);
			this.db = new DatabaseConnection();
			this.db.connect(DB_URL, DB_LOGIN, DB_PASSWORD);
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static ServerController getInstance() {
		if(self == null)
			self = new ServerController();
		return self;
	}

	public void waitForClients() {
		System.out.println("Server started, waiting for clients...");
		while(true) {
			try {
				new ServerClient(new SocketClient(this.server.accept()));
			} catch(IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	private class ServerClient extends Thread {
		private SocketClient client;
		private Account currentUser;

		private ServerClient(SocketClient client) {
			System.out.println("New client connected.");
			this.client = client;
			start();
		}

		@Override
		public void run() {
			byte[] shortBuffer = new byte[256];
			ByteBuffer buffer = ByteBuffer.allocate(8192);
			int bytesReceived;
			int totalBytesReceived = 0;
			int messageSize = -1;
			while(true) {
				try {
					if((bytesReceived = this.client.receive(shortBuffer)) == -1)
						break;
					totalBytesReceived += bytesReceived;
					System.out.println(bytesReceived + " bytes received from the client.");
					buffer.put(shortBuffer, 0, bytesReceived);
					if(messageSize == -1) {
						buffer.position(0); // get back to the start
						messageSize = buffer.getInt();
						System.out.println(messageSize);
						buffer.position(totalBytesReceived); // then to the end
					}
					if(totalBytesReceived == messageSize + 4) {
						byte[] bytes = new byte[messageSize];
						buffer.position(4); // skip the message size integer
						buffer.get(bytes, 0, messageSize);
						processRequest(NetworkMessage.deserialize(bytes));
						
						// reset all variables
						buffer.clear(); 
						totalBytesReceived = 0;
						messageSize = -1;
					}
				} catch(IOException e) {
					e.printStackTrace();
					break;
				} catch(ClassNotFoundException e) {
					e.printStackTrace();
					break;
				} catch(SQLException e) {
					try {
						this.client.send(new NetworkMessage(NetworkMessage.SERVER_MESSAGE, e.getMessage()).serialize());
						
						// reset all variables
						buffer.clear(); 
						totalBytesReceived = 0;
						messageSize = -1;
					} catch(IOException e2) {
						e.printStackTrace();
						break;
					}
				}
			}
			this.client.close();
			System.out.println("Client deconnected.");
		}

		private void processRequest(NetworkMessage request) throws SQLException, IOException {
			Object[] content = request.getContent();
			NetworkMessage response = null;
			switch(request.getId()) {
				case NetworkMessage.LOG_IN:
					this.currentUser = db.logIn((String) content[0], (String) content[1]);
					response = new NetworkMessage(NetworkMessage.SERVER_RESPONSE, this.currentUser);
					break;
				case NetworkMessage.REGISTER:
					this.currentUser = db.createAccount((Account) content[0], (String) content[1]);
					response = new NetworkMessage(NetworkMessage.SERVER_RESPONSE, this.currentUser);
					break;
				case NetworkMessage.ADD_ROOM:
					response = new NetworkMessage(NetworkMessage.SERVER_RESPONSE, db.createRoom((Room) content[0]));
					break;
				case NetworkMessage.BOOK_ROOM:
					response = new NetworkMessage(NetworkMessage.SERVER_RESPONSE, db.createBooking((Booking) content[0]));
					break;
				case NetworkMessage.CONFIRM_BOOKING:
					response = new NetworkMessage(NetworkMessage.SERVER_RESPONSE, db.updateBooking((Booking) content[0]));
					break;
				case NetworkMessage.GET_TABLES:
					if(this.currentUser == null) {
						response = new NetworkMessage(NetworkMessage.SERVER_MESSAGE, "None user connected.");
						break;
					}
					int accountType = this.currentUser.getAccountType();
					Room[] rooms = db.getAllRooms();
					Booking[] bookings = {};
					Account[] accounts = {};
					if(accountType == Account.CLIENT)
						bookings = db.getUserBookings(this.currentUser.getId());
					else {	
						if(accountType == Account.ROOT)
							accounts = db.getAllAccounts();
						bookings = db.getAllBookings();
					}
					response = new NetworkMessage(NetworkMessage.SERVER_RESPONSE, rooms, bookings, accounts);
					break;
				case NetworkMessage.LOG_OUT:
					this.currentUser = null;
					response = new NetworkMessage(NetworkMessage.SERVER_RESPONSE, true);
					break;
			}
			this.client.send(response.serialize());
		}
	}
}