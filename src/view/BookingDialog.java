package view;

import java.sql.Date;
import java.time.LocalDate;

import model.Account;
import model.Booking;
import model.Room;
import controller.ClientController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class BookingDialog extends Window {
	private TextField roomNumberInput;
	private DatePicker dateFromPicker;
	private DatePicker dateToPicker;

	@Override
	protected Stage innerDisplay(Stage mainStage, Object... args) {
		Stage dialog = new Stage();

		dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				dialog.close();
			}
		});

		EventHandler<KeyEvent> submitByKey = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ENTER) {
					try {
						LocalDate dateFromValue = dateFromPicker.getValue();
						LocalDate dateToValue = dateToPicker.getValue();
						if(dateFromValue == null || dateToValue == null)
							return;
						Room room = (Room) args[0];
						Account currentUser = (Account) args[1];
						int roomNumber = Integer.parseInt(roomNumberInput.getText());
						Date dateFrom = Date.valueOf(dateFromValue);
						Date dateTo = Date.valueOf(dateToValue);
						ClientController.actionProcessor(ActionId.BOOK_ROOM_SUBMIT, new Booking(-1, room.getId(), roomNumber, currentUser.getId(), currentUser.getName(), dateFrom, dateTo, false));
					}
					catch(NumberFormatException e) {
						
					}
				}
			}
		};

		// create the room number label
		Label roomNumberLabel = new Label("Room number :");
		roomNumberLabel.setMaxWidth(Double.MAX_VALUE);
		roomNumberLabel.setAlignment(Pos.CENTER);

		// create the room number input
		this.roomNumberInput = new TextField(String.valueOf(((Room) args[0]).getNumber()));
		this.roomNumberInput.setEditable(false);
		this.roomNumberInput.setPrefWidth(200);
		this.roomNumberInput.setOnKeyPressed(submitByKey);

		// create the dateFrom label
		Label dateFromLabel = new Label("Date from :");
		dateFromLabel.setMaxWidth(Double.MAX_VALUE);
		dateFromLabel.setAlignment(Pos.CENTER);

		// create the dateFrom pickeer
		this.dateFromPicker = new DatePicker();
		this.dateFromPicker.setPrefWidth(200);
		this.dateFromPicker.setOnKeyPressed(submitByKey);

		// create the dateTo password label
		Label dateToLabel = new Label("Date to :");
		dateToLabel.setMaxWidth(Double.MAX_VALUE);
		dateToLabel.setAlignment(Pos.CENTER);

		// create the dateTo password input
		this.dateToPicker = new DatePicker();
		this.dateToPicker.setPrefWidth(200);
		this.dateToPicker.setOnKeyPressed(submitByKey);

		// create the submit button
		Button submitButton = new Button("Book the room");
		submitButton.setPrefWidth(150);
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					LocalDate dateFromValue = dateFromPicker.getValue();
					LocalDate dateToValue = dateToPicker.getValue();
					if(dateFromValue == null || dateToValue == null)
						return;
					Room room = (Room) args[0];
					Account currentUser = (Account) args[1];
					int roomNumber = Integer.parseInt(roomNumberInput.getText());
					Date dateFrom = Date.valueOf(dateFromValue);
					Date dateTo = Date.valueOf(dateToValue);
					ClientController.actionProcessor(ActionId.BOOK_ROOM_SUBMIT, new Booking(-1, room.getId(), roomNumber, currentUser.getId(), currentUser.getName(), dateFrom, dateTo, false));
				}
				catch(NumberFormatException e) {
					
				}
			}
		});

		// create the vertical layout
		VBox vBox = new VBox();
		vBox.setPadding(new Insets(30));
		vBox.setSpacing(10);
		vBox.getChildren().add(roomNumberLabel);
		vBox.getChildren().add(this.roomNumberInput);
		vBox.getChildren().add(dateFromLabel);
		vBox.getChildren().add(this.dateFromPicker);
		vBox.getChildren().add(dateToLabel);
		vBox.getChildren().add(this.dateToPicker);
		vBox.getChildren().add(new BorderPane(submitButton));

		dialog.setScene(new Scene(vBox));
		dialog.setTitle("Room Booking");
		dialog.initModality(Modality.NONE);
		dialog.initOwner(mainStage);
		dialog.show();
		return dialog;
	}
}