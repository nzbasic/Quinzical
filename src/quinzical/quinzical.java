package quinzical;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import quinzical.scenes.GameController;

/**
 * Entry point class for the program. Loads the menu and starts the game.
 */
public class quinzical extends Application {

	private static Stage stage;

	@Override
	public void start(Stage primaryStage) throws IOException {
		primaryStage.setResizable(false);
		stage = primaryStage;
		loadFXML("Menu");
		primaryStage.setTitle("Quinzical");
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void loadFXML(String fxmlName) throws IOException {
		FXMLLoader fxml = new FXMLLoader(GameController.class.getResource("./fxml/" + fxmlName + ".fxml"));
		Parent parent = fxml.load();
		Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.show();
	}

}
