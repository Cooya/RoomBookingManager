package view;

import javafx.application.Platform;
import javafx.stage.Stage;

public abstract class Window {
	private Stage stage;
	
	protected void display(Stage mainStage, Object... args) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				stage = innerDisplay(mainStage, args);
			}
		});
	};
	
	protected abstract Stage innerDisplay(Stage mainStage, Object... args);
	
	protected void dismiss() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				stage.close();
			}
		});
	}
}