package view;

import controller.ClientController;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class InfoDialog extends Window {
	private ObservableValue<String> message;
	private ObservableValue<Boolean> error;
	
	public InfoDialog(ObservableValue<String> message, ObservableValue<Boolean> error) {
		this.message = message;
		this.error = error;
	}

	@Override
	public Stage innerDisplay(Stage mainStage, Object... args) {
		Stage dialog = new Stage();
		
		dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				ClientController.actionProcessor(ActionId.EXIT);
			}
		});
		
		// create the message label
		Label msgLabel = new Label();
		msgLabel.setMaxWidth(Double.MAX_VALUE);
		msgLabel.setAlignment(Pos.CENTER);
		msgLabel.textProperty().bind(this.message);
		
		// create the ok button
		Button okButton = new Button(this.error.getValue() ? "Exit" : "OK");
		okButton.setPrefWidth(100);
		okButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(error.getValue())
					ClientController.actionProcessor(ActionId.EXIT);
				else
					dialog.close();
			}
		});
		
		// create the vertical layout
		VBox vBox = new VBox();
		vBox.setPadding(new Insets(30));
		vBox.setSpacing(10);
		vBox.getChildren().add(msgLabel);
		vBox.getChildren().add(new BorderPane(okButton));
		
		dialog.setScene(new Scene(vBox));
		dialog.setMinWidth(200);
		dialog.setTitle("Message");
		dialog.initModality(Modality.NONE);
		dialog.initOwner(mainStage);
		dialog.show();
		return dialog;
	}
}