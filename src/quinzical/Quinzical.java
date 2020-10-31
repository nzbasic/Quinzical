package quinzical;

import java.io.IOException;
import quinzical.data.FxmlFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import quinzical.scenes.GameController;
import quinzical.scenes.QuestionController;

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

	/**
	 * Loads an fxml file to the main stage.
	 * 
	 * @param fxmlfile FxmlFile enum
	 * @throws IOException file not found
	 */
	public static void loadFXML(FxmlFile fxmlfile) throws IOException {
		FXMLLoader fxml = new FXMLLoader(GameController.class.getResource(fxmlfile.getPath()));
		Parent parent = fxml.load();
		Scene scene = new Scene(parent);
		_stage.setScene(scene);
		_stage.setOnHiding(event -> {
			new QuestionController().killProcesses();
		});
		_stage.show();

	}

	private static Parent _controllerParent;

	/**
	 * This will load a given Fxml file and return the controller for that fxml. It
	 * will also store the parent in _controllerParent to load later with
	 * Quinzical#loadStoredFXML().
	 * 
	 * @param fxmlfile FxmlFile enum
	 * @return Controller defined in the fxml
	 * @throws IOException file not found
	 */
	public static Object loadGetController(FxmlFile fxmlfile) throws IOException {
		FXMLLoader load = new FXMLLoader(GameController.class.getResource(fxmlfile.getPath()));
		_controllerParent = load.load();
		return load.getController();
	}

	/**
	 * Loads the parent currently stored in Quinzical._controllerParent to the main
	 * stage.
	 */
	public static void loadStoredFXML() {
		if (_controllerParent == null) {
			throw new NullPointerException("Parent not loaded");
		}
		Scene scene = new Scene(_controllerParent);
		_stage.setScene(scene);
		_stage.show();
	}

}
