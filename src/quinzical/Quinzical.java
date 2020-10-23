package quinzical;

import java.io.IOException;
import quinzical.data.FxmlFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import quinzical.scenes.GameController;

/**
 * Entry point class for the program. Loads the menu and starts the game.
 */
public class Quinzical extends Application {

	private static Stage _stage;

	@Override
	public void start(Stage primaryStage) throws IOException {
		primaryStage.setResizable(false);
		_stage = primaryStage;
		loadFXML(FxmlFile.MENU);
		primaryStage.setTitle("Quinzical");
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void loadFXML(FxmlFile fxmlfile) throws IOException {
		FXMLLoader fxml = new FXMLLoader(GameController.class.getResource(fxmlfile.getPath()));
		Parent parent = fxml.load();
		Scene scene = new Scene(parent);
		_stage.setScene(scene);
		_stage.show();
	}

}
