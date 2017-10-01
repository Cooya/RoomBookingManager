package view;

import controller.ClientController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoginDialog extends Window {
	private TextField loginInput;
	private TextField passwordInput;

	public Stage innerDisplay(Stage mainStage, Object... args) {
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
					String login = loginInput.getText();
					String password = passwordInput.getText();
					if(!login.isEmpty() && !password.isEmpty())
						ClientController.actionProcessor(ActionId.LOGIN_SUBMIT, login, password);
				}
			}
		};

		// create the login label
		Label loginLabel = new Label("Login :");
		loginLabel.setMaxWidth(Double.MAX_VALUE);
		loginLabel.setAlignment(Pos.CENTER);

		// create the login input
		this.loginInput = new TextField();
		this.loginInput.setPrefWidth(200);
		this.loginInput.setOnKeyPressed(submitByKey);

		// create the password label
		Label passwordLabel = new Label("Password :");
		passwordLabel.setMaxWidth(Double.MAX_VALUE);
		passwordLabel.setAlignment(Pos.CENTER);

		// create the password input
		this.passwordInput = new PasswordField();
		this.passwordInput.setPrefWidth(200);
		this.passwordInput.setOnKeyPressed(submitByKey);

		// create the submit button
		Button submitButton = new Button("Sign in");
		submitButton.setPrefWidth(100);
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String login = loginInput.getText();
				String password = loginInput.getText();
				if(!login.isEmpty() && !password.isEmpty())
					ClientController.actionProcessor(ActionId.LOGIN_SUBMIT, login, password);
			}
		});

		// create the register button
		Button registerButton = new Button("Register");
		registerButton.setPrefWidth(50);
		registerButton.setFont(Font.font(10));
		registerButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ClientController.actionProcessor(ActionId.REGISTER_BUTTON);
			}
		});

		// create the vertical layout
		VBox vBox = new VBox();
		vBox.setPadding(new Insets(30));
		vBox.setSpacing(10);
		vBox.getChildren().add(loginLabel);
		vBox.getChildren().add(this.loginInput);
		vBox.getChildren().add(passwordLabel);
		vBox.getChildren().add(this.passwordInput);
		vBox.getChildren().add(new BorderPane(submitButton));
		vBox.getChildren().add(new BorderPane(registerButton));

		dialog.setScene(new Scene(vBox));
		dialog.setTitle("Authentication");
		dialog.initModality(Modality.NONE);
		dialog.initOwner(mainStage);
		dialog.show();
		return dialog;
	}
}