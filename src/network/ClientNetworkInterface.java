package network;

import java.io.IOException;
import java.nio.ByteBuffer;

public class ClientNetworkInterface extends Thread {
	private static final String SERVER_ADDRESS = "127.0.0.1";
	private static final int SERVER_PORT = 8080;
	
	private SocketClient client;
	
	public ClientNetworkInterface() throws IOException {
		this.client = new SocketClient(SERVER_ADDRESS, SERVER_PORT);
	}
	
	public NetworkMessage sendRequest(NetworkMessage msg) throws IOException, ClassNotFoundException {
		this.client.send(msg.serialize());
		byte[] shortBuffer = new byte[256];
		ByteBuffer buffer = ByteBuffer.allocate(8192);
		int bytesReceived;
		int totalBytesReceived = 0;
		int messageSize = -1;
		while(true) {
			if((bytesReceived = this.client.receive(shortBuffer)) == -1)
				throw new IOException("Deconnected from the server.");
			totalBytesReceived += bytesReceived;
			System.out.println(bytesReceived + " bytes received from the server.");
			buffer.put(shortBuffer, 0, bytesReceived);
			if(messageSize == -1) {
				buffer.rewind(); // get back to the start
				messageSize = buffer.getInt();
				buffer.position(totalBytesReceived); // then to the end
			}
			if(totalBytesReceived == messageSize + 4) {
				byte[] bytes = new byte[messageSize];
				buffer.position(4); // skip the message size integer
				buffer.get(bytes, 0, messageSize);
				return NetworkMessage.deserialize(bytes);
			}
		}
	}
}