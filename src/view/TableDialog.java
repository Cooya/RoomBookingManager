package view;

import controller.ClientController;
import model.Account;
import model.Booking;
import model.Room;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TableDialog extends Window {
	private ObservableValue<Account> currentUser;
	private ObservableList<Room> rooms;
	private ObservableList<Booking> bookings;
	private ObservableList<Account> accounts;

	public TableDialog(ObservableValue<Account> currentUser, ObservableList<Room> rooms, ObservableList<Booking> bookings, ObservableList<Account> accounts) {
		this.currentUser = currentUser;
		this.rooms = rooms;
		this.bookings = bookings;
		this.accounts = accounts;
	}

	@Override
	public Stage innerDisplay(Stage mainStage, Object... args) {
		Stage dialog = new Stage();

		dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				dialog.close();
			}
		});

		// create tabs
		TabPane tabPane = new TabPane();
		int accountType = this.currentUser.getValue().getAccountType();
		tabPane.getTabs().add(createRoomsTab(accountType));
		tabPane.getTabs().add(createBookingsTab(accountType));
		if(accountType == Account.ROOT || accountType == Account.ADMIN)
			tabPane.getTabs().add(createAccountsTab());

		// delegate the focus to the tab pane
		tabPane.requestFocus();

		// display the GUI
		//dialog.setMaximized(true);
		dialog.setWidth(500);
		dialog.setScene(new Scene(tabPane));
		dialog.setTitle("Room Booking Manager");
		dialog.initOwner(mainStage);
		dialog.show();
		return dialog;
	}

	@SuppressWarnings("unchecked")
	private Tab createRoomsTab(int accountType) {
		// create table
		TableView<Room> roomsTable = new TableView<Room>();
		roomsTable.setEditable(false);

		// create columns
		TableColumn<Room, Integer> number = new TableColumn<Room, Integer>("Number");
		number.setCellValueFactory(new PropertyValueFactory<Room, Integer>("number"));
		TableColumn<Room, String> type = new TableColumn<Room, String>("Type");
		type.setCellValueFactory(new PropertyValueFactory<Room, String>("type"));
		TableColumn<Room, Integer> size = new TableColumn<Room, Integer>("Size");
		size.setCellValueFactory(new PropertyValueFactory<Room, Integer>("size"));
		TableColumn<Room, String> isAvailable = new TableColumn<Room, String>("Available");
		isAvailable.setCellValueFactory(new PropertyValueFactory<Room, String>("available"));
		
		// adapt width of columns
		number.prefWidthProperty().bind(roomsTable.widthProperty().multiply(0.25));
		type.prefWidthProperty().bind(roomsTable.widthProperty().multiply(0.25));
		size.prefWidthProperty().bind(roomsTable.widthProperty().multiply(0.25));
		isAvailable.prefWidthProperty().bind(roomsTable.widthProperty().multiply(0.25));

		// add columns to table
		roomsTable.setItems(this.rooms);
		roomsTable.getColumns().addAll(number, type, size, isAvailable);

		// create buttons
		Button addRoom = new Button("Add a room");
		addRoom.setMinWidth(100);
		addRoom.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ClientController.actionProcessor(ActionId.ADD_ROOM_BUTTON);
			}
		});
		Button bookRoom = new Button("Book a room");
		bookRoom.setMinWidth(100);
		bookRoom.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Room room = roomsTable.getSelectionModel().getSelectedItem();
				if(room != null)
					ClientController.actionProcessor(ActionId.BOOK_ROOM_BUTTON, room, currentUser.getValue());
			}
		});
		Button logout = new Button("Log out");
		logout.setMinWidth(100);
		logout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ClientController.actionProcessor(ActionId.LOGOUT_BUTTON);
			}
		});

		// create vertical layout
		VBox vBox = new VBox();
		VBox.setVgrow(roomsTable, Priority.ALWAYS);
		vBox.setPadding(new Insets(10));
		vBox.setSpacing(10);
		vBox.getChildren().add(roomsTable);

		// create border layout
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(0, 50, 0, 50));
		if(accountType == Account.ROOT || accountType == Account.ADMIN) {
			borderPane.setLeft(addRoom);
			borderPane.setCenter(bookRoom);
			borderPane.setRight(logout);
		}
		else {
			borderPane.setLeft(bookRoom);
			borderPane.setRight(logout);
		}
		vBox.getChildren().add(borderPane);

		Tab tab = new Tab();
		tab.setText("Rooms");
		tab.setClosable(false);
		tab.setContent(vBox);
		return tab;
	}

	@SuppressWarnings("unchecked")
	private Tab createBookingsTab(int accountType) {
		// create table
		TableView<Booking> bookingsTable = new TableView<Booking>();
		bookingsTable.setEditable(false);

		// create columns
		TableColumn<Booking, Integer> roomNumber = new TableColumn<Booking, Integer>("Room number");
		roomNumber.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("roomNumber"));
		TableColumn<Booking, String> accountName = new TableColumn<Booking, String>("Account name");
		accountName.setCellValueFactory(new PropertyValueFactory<Booking, String>("accountName"));
		TableColumn<Booking, String> dateFrom = new TableColumn<Booking, String>("From date");
		dateFrom.setCellValueFactory(new PropertyValueFactory<Booking, String>("dateFrom"));
		TableColumn<Booking, String> dateTo = new TableColumn<Booking, String>("To date");
		dateTo.setCellValueFactory(new PropertyValueFactory<Booking, String>("dateTo"));
		TableColumn<Booking, String> isConfirmed = new TableColumn<Booking, String>("Confirmed");
		isConfirmed.setCellValueFactory(new PropertyValueFactory<Booking, String>("confirmed"));
		
		// adapt width of columns
		roomNumber.prefWidthProperty().bind(bookingsTable.widthProperty().multiply(0.2));
		accountName.prefWidthProperty().bind(bookingsTable.widthProperty().multiply(0.2));
		dateFrom.prefWidthProperty().bind(bookingsTable.widthProperty().multiply(0.2));
		dateTo.prefWidthProperty().bind(bookingsTable.widthProperty().multiply(0.2));
		isConfirmed.prefWidthProperty().bind(bookingsTable.widthProperty().multiply(0.2));
		
		// add columns to table
		bookingsTable.setItems(this.bookings);
		bookingsTable.getColumns().addAll(roomNumber, accountName, dateFrom, dateTo, isConfirmed);
		
		// create buttons
		Button confirmBooking = new Button("Confirm a booking");
		confirmBooking.setMinWidth(100);
		confirmBooking.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Booking booking = bookingsTable.getSelectionModel().getSelectedItem();
				if(booking != null)
					ClientController.actionProcessor(ActionId.CONFIRM_BOOKING_BUTTON, booking);
			}
		});
		Button logout = new Button("Log out");
		logout.setMinWidth(100);
		logout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ClientController.actionProcessor(ActionId.LOGOUT_BUTTON);
			}
		});

		// create vertical layout
		VBox vBox = new VBox();
		VBox.setVgrow(bookingsTable, Priority.ALWAYS);
		vBox.setPadding(new Insets(10));
		vBox.setSpacing(10);
		vBox.getChildren().add(bookingsTable);

		// create border layout
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(0, 50, 0, 50));
		if(accountType == Account.ROOT || accountType == Account.ADMIN)
			borderPane.setCenter(confirmBooking);
		else
			borderPane.setCenter(logout);
		vBox.getChildren().add(borderPane);

		Tab tab = new Tab();
		tab.setText("Bookings");
		tab.setClosable(false);
		tab.setContent(vBox);
		return tab;
	}

	@SuppressWarnings("unchecked")
	private Tab createAccountsTab() {
		// create table
		TableView<Account> accountsTable = new TableView<Account>();
		accountsTable.setEditable(false);

		// create columns
		TableColumn<Account, String> name = new TableColumn<Account, String>("Name");
		name.setCellValueFactory(new PropertyValueFactory<Account, String>("name"));
		TableColumn<Account, String> login = new TableColumn<Account, String>("Login");
		login.setCellValueFactory(new PropertyValueFactory<Account, String>("login"));
		TableColumn<Account, String> accountType = new TableColumn<Account, String>("Account type");
		accountType.setCellValueFactory(new PropertyValueFactory<Account, String>("type"));
		
		// adapt width of columns
		name.prefWidthProperty().bind(accountsTable.widthProperty().multiply(0.33));
		login.prefWidthProperty().bind(accountsTable.widthProperty().multiply(0.33));
		accountType.prefWidthProperty().bind(accountsTable.widthProperty().multiply(0.33));
		
		// add columns to table
		accountsTable.setItems(this.accounts);
		accountsTable.getColumns().addAll(name, login, accountType);
		
		// create buttons
		Button createAdmin = new Button("Create a new admin");
		createAdmin.setMinWidth(150);
		createAdmin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ClientController.actionProcessor(ActionId.CREATE_ADMIN_BUTTON);
			}
		});
		Button logout = new Button("Log out");
		logout.setMinWidth(100);
		logout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ClientController.actionProcessor(ActionId.LOGOUT_BUTTON);
			}
		});
		
		// create vertical layout
		VBox vBox = new VBox();
		VBox.setVgrow(accountsTable, Priority.ALWAYS);
		vBox.setPadding(new Insets(10));
		vBox.setSpacing(10);
		vBox.getChildren().add(accountsTable);

		// create border layout
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(0, 50, 0, 50));
		borderPane.setLeft(createAdmin);
		borderPane.setRight(logout);
		vBox.getChildren().add(borderPane);
		
		Tab tab = new Tab();
		tab.setText("Accounts");
		tab.setClosable(false);
		tab.setContent(vBox);
		return tab;
	}
}