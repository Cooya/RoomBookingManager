package view;
import model.Room;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Table implements Window {
	
	public void display(Stage mainStage) {

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
		TableColumn<Room, Boolean> isAvailable = new TableColumn<Room, Boolean>("Available");
		isAvailable.setCellValueFactory(new PropertyValueFactory<Room, Boolean>("isAvailable"));

		// add columns to table
		roomsTable.setItems(); // TODO
		roomsTable.getColumns().addAll(number, type, size, isAvailable);

		// adapt width of columns
		number.prefWidthProperty().bind(roomsTable.widthProperty().multiply(0.25));
		type.prefWidthProperty().bind(roomsTable.widthProperty().multiply(0.25));
		size.prefWidthProperty().bind(roomsTable.widthProperty().multiply(0.25));
		isAvailable.prefWidthProperty().bind(roomsTable.widthProperty().multiply(0.25));

		// buttons
		Button bookRoom = new Button("Book a room");
		bookRoom.setMinWidth(100);
		bookRoom.setOnAction(); // TODO
		
		Button confirmBooking = new Button("Confirm a booking");
		confirmBooking.setMinWidth(100);
		confirmBooking.setOnAction(); // TODO
		
		Button createAdmin = new Button("Create a new admin");
		createAdmin.setMinWidth(100);
		createAdmin.setOnAction(); // TODO
		
		// create vertical layout
		VBox vBox = new VBox();
		VBox.setVgrow(roomsTable, Priority.ALWAYS);
		vBox.setPadding(new Insets(10));
		vBox.setSpacing(10);
		vBox.getChildren().add(roomsTable);

		// create border layout // TODO
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(0, 50, 0, 50));
		borderPane.setLeft(loadButton);
		borderPane.setCenter(uploadButton);
		borderPane.setRight(saveButton);
		vBox.getChildren().add(borderPane);

		// display the GUI
		mainStage.setMaximized(true);
		mainStage.setScene(new Scene(vBox));
		mainStage.setTitle("Room Booking Manager");
		mainStage.show();
		// delegate the focus to the container
		vBox.requestFocus();
	}
}