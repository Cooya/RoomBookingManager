package view;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RegisterDialog implements Window {
	
	public void display(Stage mainStage) {
		Stage dialog = new Stage();
		
		// create the name label
		Label nameLabel = new Label("Name :");
		nameLabel.setMaxWidth(Double.MAX_VALUE);
		nameLabel.setAlignment(Pos.CENTER);
		
		// create the name input
		TextField nameInput = new TextField();
		//loginInput.setAlignment(Pos.CENTER);
		nameInput.setPrefWidth(200);
		
		// create the login label
		Label loginLabel = new Label("Login :");
		loginLabel.setMaxWidth(Double.MAX_VALUE);
		loginLabel.setAlignment(Pos.CENTER);
		
		// create the login input
		TextField loginInput = new TextField();
		//loginInput.setAlignment(Pos.CENTER);
		loginInput.setPrefWidth(200);
		
		// create the password label
		Label passwordLabel = new Label("Password :");
		passwordLabel.setMaxWidth(Double.MAX_VALUE);
		passwordLabel.setAlignment(Pos.CENTER);
		
		// create the password input
		PasswordField passwordInput = new PasswordField();
		//passwordInput.setAlignment(Pos.CENTER);
		passwordInput.setPrefWidth(200);
		
		// create the confirm password label
		Label confirmPasswordLabel = new Label("Confirm password :");
		confirmPasswordLabel.setMaxWidth(Double.MAX_VALUE);
		confirmPasswordLabel.setAlignment(Pos.CENTER);
		
		// create the confirm password input
		PasswordField conirmPasswordInput = new PasswordField();
		//passwordInput.setAlignment(Pos.CENTER);
		conirmPasswordInput.setPrefWidth(200);
		
		// create the submit button
		Button submitButton = new Button("Register");
		submitButton.setPrefWidth(100);
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.close();
			}
		});
		
		// create the vertical layout
		VBox vBox = new VBox();
		vBox.setPadding(new Insets(30));
		vBox.setSpacing(10);
		vBox.getChildren().add(nameLabel);
		vBox.getChildren().add(nameInput);
		vBox.getChildren().add(loginLabel);
		vBox.getChildren().add(loginInput);
		vBox.getChildren().add(passwordLabel);
		vBox.getChildren().add(passwordInput);
		vBox.getChildren().add(confirmPasswordLabel);
		vBox.getChildren().add(conirmPasswordInput);
		vBox.getChildren().add(new BorderPane(submitButton));
		
		dialog.setScene(new Scene(vBox));
		dialog.setTitle("Registration");
		dialog.initModality(Modality.NONE);
		dialog.initOwner(mainStage);
		dialog.show();
	}
}