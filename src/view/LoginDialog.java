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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class LoginDialog implements Window {
	
	public void display(Stage mainStage) {
		Stage dialog = new Stage();
		
		// create the login label
		Label loginLabel = new Label("Login :");
		loginLabel.setMaxWidth(Double.MAX_VALUE);
		loginLabel.setAlignment(Pos.CENTER);
		
		// create the login input
		TextField loginInput = new TextField();
		//loginInput.setAlignment(Pos.CENTER);
		loginInput.setPrefWidth(200);
		loginInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER) {
					dialog.close();
                }
            }
        });
		
		// create the password label
		Label passwordLabel = new Label("Password :");
		passwordLabel.setMaxWidth(Double.MAX_VALUE);
		passwordLabel.setAlignment(Pos.CENTER);
		
		// create the password input
		PasswordField passwordInput = new PasswordField();
		//passwordInput.setAlignment(Pos.CENTER);
		passwordInput.setPrefWidth(200);
		passwordInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER) {
                	dialog.close();
                }
            }
        });
		
		// create the submit button
		Button submitButton = new Button("Sign in");
		submitButton.setPrefWidth(100);
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.close();
			}
		});
		
		// create the register button
		Button registerButton = new Button("Register");
		registerButton.setPrefWidth(50);
		registerButton.setFont(Font.font(10));
		registerButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.close();
			}
		});
		
		// create the vertical layout
		VBox vBox = new VBox();
		vBox.setPadding(new Insets(30));
		vBox.setSpacing(10);
		vBox.getChildren().add(loginLabel);
		vBox.getChildren().add(loginInput);
		vBox.getChildren().add(passwordLabel);
		vBox.getChildren().add(passwordInput);
		vBox.getChildren().add(new BorderPane(submitButton));
		vBox.getChildren().add(new BorderPane(registerButton));
		
		dialog.setScene(new Scene(vBox));
		dialog.setTitle("Authentification");
		dialog.initModality(Modality.NONE);
		dialog.initOwner(mainStage);
		dialog.show();
	}
}