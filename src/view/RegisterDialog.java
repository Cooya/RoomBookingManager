package view;

import model.Account;
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

public class RegisterDialog extends Window {
	private TextField nameInput;
	private TextField loginInput;
	private TextField passwordInput;
	private TextField confirmPasswordInput;

	@Override
	public Stage innerDisplay(Stage mainStage, Object... args) {
		boolean adminCreation = (boolean) args[0];
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
					String name = nameInput.getText();
					String login = loginInput.getText();
					String password = passwordInput.getText();
					String confirmPassword = confirmPasswordInput.getText();
					if(!name.isEmpty() && !login.isEmpty() && !password.isEmpty() && confirmPassword.equals(password))
						ClientController.actionProcessor(ActionId.REGISTER_SUBMIT, new Account(-1, name, login, adminCreation ? Account.ADMIN : Account.CLIENT), password);
				}
			}
		};

		// create the name label
		Label nameLabel = new Label("Name :");
		nameLabel.setMaxWidth(Double.MAX_VALUE);
		nameLabel.setAlignment(Pos.CENTER);

		// create the name input
		this.nameInput = new TextField();
		this.nameInput.setPrefWidth(200);
		this.nameInput.setOnKeyPressed(submitByKey);

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

		// create the confirm password label
		Label confirmPasswordLabel = new Label("Confirm password :");
		confirmPasswordLabel.setMaxWidth(Double.MAX_VALUE);
		confirmPasswordLabel.setAlignment(Pos.CENTER);

		// create the confirm password input
		this.confirmPasswordInput = new PasswordField();
		this.confirmPasswordInput.setPrefWidth(200);
		this.confirmPasswordInput.setOnKeyPressed(submitByKey);

		// create the submit button
		Button submitButton = new Button(adminCreation ? "Create admin" : "Register");
		submitButton.setPrefWidth(100);
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String name = nameInput.getText();
				String login = loginInput.getText();
				String password = passwordInput.getText();
				String confirmPassword = confirmPasswordInput.getText();
				if(!name.isEmpty() && !login.isEmpty() && !password.isEmpty() && confirmPassword.equals(password))
					ClientController.actionProcessor(ActionId.REGISTER_SUBMIT, new Account(-1, name, login, adminCreation ? Account.ADMIN : Account.CLIENT), password);
			}
		});
		
		// create the login button
		Button loginButton = new Button("Login");
		loginButton.setPrefWidth(50);
		loginButton.setFont(Font.font(10));
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ClientController.actionProcessor(ActionId.LOGIN_BUTTON);
			}
		});

		// create the vertical layout
		VBox vBox = new VBox();
		vBox.setPadding(new Insets(30));
		vBox.setSpacing(10);
		vBox.getChildren().add(nameLabel);
		vBox.getChildren().add(this.nameInput);
		vBox.getChildren().add(loginLabel);
		vBox.getChildren().add(this.loginInput);
		vBox.getChildren().add(passwordLabel);
		vBox.getChildren().add(this.passwordInput);
		vBox.getChildren().add(confirmPasswordLabel);
		vBox.getChildren().add(this.confirmPasswordInput);
		vBox.getChildren().add(new BorderPane(submitButton));
		if(!adminCreation) vBox.getChildren().add(new BorderPane(loginButton));

		dialog.setScene(new Scene(vBox));
		dialog.setTitle(adminCreation ? "Admin creation" : "Registration");
		dialog.initModality(Modality.NONE);
		dialog.initOwner(mainStage);
		dialog.show();
		return dialog;
	}
}