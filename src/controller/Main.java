package controller;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		new Thread() {
			@Override
			public void run() {
				ServerController.getInstance().waitForClients();
			}
		}.start();
		Thread.sleep(3000);
		ClientController.run();
		System.exit(0);
	}
}