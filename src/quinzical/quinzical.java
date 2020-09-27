package quinzical;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import quinzical.scenes.GameController;

/**
 * Entry point class for the program. Loads the menu and starts the game.
 */
public class quinzical extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setController(new GameController());
		Scene scene = new Scene(FXMLLoader.load(getClass().getResource("scenes/Menu.fxml")));
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
