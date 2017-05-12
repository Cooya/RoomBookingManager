package view;
import javafx.application.Application;
import javafx.stage.Stage;


public class GUI extends Application {
	
	public static void display() {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		new LoginDialog().display(stage);
		new RegisterDialog().display(stage);
	}
}