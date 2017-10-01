package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

public class SocketClient {
	private Socket client;
	private InputStream inputStream;
	private OutputStream outputStream;
	
	public SocketClient(String serverIP, int port) throws IOException {
		this.client = new Socket(serverIP, port);
		this.inputStream = this.client.getInputStream();
		this.outputStream = this.client.getOutputStream();
	}
	
	public SocketClient(Socket client) {
		try {
			this.client = client;
			this.inputStream = this.client.getInputStream();
			this.outputStream = this.client.getOutputStream();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public SocketClient(String proxyIP, int proxyPort, String serverIP, int serverPort) {
		try {
			Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxyIP, proxyPort));
			this.client = new Socket(proxy);
			this.client.connect(new InetSocketAddress(serverIP, serverPort));
			this.inputStream = this.client.getInputStream();
			this.outputStream = this.client.getOutputStream();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void send(byte[] bytes) throws IOException {
		this.outputStream.write(bytes);
	}
	
	public int receive(byte[] buffer) throws IOException {
		return this.inputStream.read(buffer);
	}
	
	public int receive(byte[] buffer, int timeout) throws IOException {
		this.client.setSoTimeout(timeout);
		int bytes = receive(buffer);
		this.client.setSoTimeout(0);
		return bytes;
	}
	
	public void close() {
		try {
			this.inputStream.close();
			this.outputStream.close();
			this.client.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isClosed() {
		return this.client.isClosed();
	}
}