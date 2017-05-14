package view;

import model.Account;
import model.Booking;
import model.Room;
import controller.ActionId;
import controller.Controller;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class GUI extends Application {
	private Stage mainStage;
	
	private Window loginDialog;
	private Window registerDialog;
	private Window infoDialog;
	private Window bookingDialog;
	private Window roomDialog;
	private Window tableDialog;
	
	public GUI() {
		// used for JavaFX application
	}
	
	public GUI(SimpleStringProperty message, 
			SimpleBooleanProperty error, 
			ObservableValue<Account> currentUser,
			ObservableList<Room> rooms,
			ObservableList<Booking> bookings,
			ObservableList<Account> accounts) {
		this.loginDialog = new LoginDialog();
		this.registerDialog = new RegisterDialog();
		this.infoDialog = new InfoDialog(message, error);
		this.bookingDialog = new BookingDialog();
		this.roomDialog = new RoomDialog();
		this.tableDialog = new TableDialog(currentUser, rooms, bookings, accounts);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.mainStage = stage;
		Controller.actionProcessor(ActionId.GUI_READY);
	}
	
	public void runThread() {
		launch();
	}
	
	public void display(String windowName, Object... args) {
		try {
			((Window) getClass().getDeclaredField(windowName).get(this)).display(this.mainStage, args);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void dismiss(String windowName) {
		try {
			((Window) getClass().getDeclaredField(windowName).get(this)).dismiss();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}