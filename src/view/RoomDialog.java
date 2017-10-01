package view;

import model.Room;
import controller.ClientController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class RoomDialog extends Window {
	private TextField typeInput;
	private TextField numberInput;
	private TextField sizeInput;
	private CheckBox isAvailableCheckbox;

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
					String type = typeInput.getText();
					try {
						int number = Integer.parseInt(numberInput.getText());
						int size = Integer.parseInt(sizeInput.getText());
						boolean isAvailable = isAvailableCheckbox.isSelected();
						if(!type.isEmpty())
							ClientController.actionProcessor(ActionId.ADD_ROOM_SUBMIT, new Room(0, number, type, size, isAvailable));
					}
					catch(NumberFormatException e) {
						
					}
				}
			}
		};

		// create the type label
		Label typeLabel = new Label("Type :");
		typeLabel.setMaxWidth(Double.MAX_VALUE);
		typeLabel.setAlignment(Pos.CENTER);

		// create the type input
		this.typeInput = new TextField();
		this.typeInput.setPrefWidth(200);
		this.typeInput.setOnKeyPressed(submitByKey);

		// create the number label
		Label numberLabel = new Label("Number :");
		numberLabel.setMaxWidth(Double.MAX_VALUE);
		numberLabel.setAlignment(Pos.CENTER);

		// create the number input
		this.numberInput = new TextField();
		this.numberInput.setPrefWidth(200);
		this.numberInput.setOnKeyPressed(submitByKey);

		// create the size label
		Label sizeLabel = new Label("Size :");
		sizeLabel.setMaxWidth(Double.MAX_VALUE);
		sizeLabel.setAlignment(Pos.CENTER);

		// create the size input
		this.sizeInput = new TextField();
		this.sizeInput.setPrefWidth(200);
		this.sizeInput.setOnKeyPressed(submitByKey);

		// create the isAvailable checkbox
		this.isAvailableCheckbox = new CheckBox("Available ?");
		this.isAvailableCheckbox.setAlignment(Pos.CENTER);
		this.isAvailableCheckbox.setSelected(true);
		this.isAvailableCheckbox.setPrefWidth(200);
		this.isAvailableCheckbox.setOnKeyPressed(submitByKey);

		// create the submit button
		Button submitButton = new Button("Add");
		submitButton.setPrefWidth(100);
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String type = typeInput.getText();
				try {
					int number = Integer.parseInt(numberInput.getText());
					int size = Integer.parseInt(sizeInput.getText());
					boolean isAvailable = isAvailableCheckbox.isSelected();
					if(!type.isEmpty())
						ClientController.actionProcessor(ActionId.ADD_ROOM_SUBMIT, new Room(0, number, type, size, isAvailable));
				}
				catch(NumberFormatException e) {
					
				}
			}
		});

		// create the vertical layout
		VBox vBox = new VBox();
		vBox.setPadding(new Insets(30));
		vBox.setSpacing(10);
		vBox.getChildren().add(typeLabel);
		vBox.getChildren().add(this.typeInput);
		vBox.getChildren().add(numberLabel);
		vBox.getChildren().add(this.numberInput);
		vBox.getChildren().add(sizeLabel);
		vBox.getChildren().add(this.sizeInput);
		vBox.getChildren().add(this.isAvailableCheckbox);
		vBox.getChildren().add(new BorderPane(submitButton));

		dialog.setScene(new Scene(vBox));
		dialog.setTitle("Room creation");
		dialog.initModality(Modality.NONE);
		dialog.initOwner(mainStage);
		dialog.show();
		return dialog;
	}
}