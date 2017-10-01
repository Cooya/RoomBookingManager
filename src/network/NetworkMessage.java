package network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

public class NetworkMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final int SERVER_RESPONSE = 0;
	public static final int LOG_IN = 1;
	public static final int REGISTER = 2;
	public static final int ADD_ROOM = 3;
	public static final int BOOK_ROOM = 4;
	public static final int CONFIRM_BOOKING = 5;
	public static final int GET_TABLES = 6;
	public static final int SERVER_MESSAGE = 7;
	public static final int LOG_OUT = 8;
	
	private int id;
	private Object[] content;
	
	public NetworkMessage(int id, Object... objects) {
		this.id = id;
		this.content = objects;
	}
	
	public int getId() {
		return this.id;
	}
	
	public Object[] getContent() {
		return this.content;
	}
	
	public byte[] serialize() throws IOException {
		byte[] content = serializeContent();
		ByteBuffer buffer = ByteBuffer.allocate(4 + content.length);
		buffer.putInt(content.length);
		buffer.put(content);
		return buffer.array();
	}
	
	private byte[] serializeContent() throws IOException {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(out);
	    os.writeObject(this);
	    return out.toByteArray();
	}
	
	public static NetworkMessage deserialize(byte[] data) throws IOException, ClassNotFoundException {
	    ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    return (NetworkMessage) is.readObject();
	}
}