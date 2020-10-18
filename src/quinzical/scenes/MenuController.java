package quinzical.scenes;

import java.io.IOException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import quinzical.model.AttemptTrack;
import quinzical.model.Winnings;
import quinzical.quinzical;

/**
 * Controller for the main menu screen.
 */
public class MenuController extends Help {
	@FXML
	private Label money;

	@FXML
	private AnchorPane popup;

	@FXML
	private ImageView background, playImage, exitImage;

	/**
	 * Method run on fxml load. Loads the images in a new thread and displays
	 * winnings.
	 */
	@FXML
	public void initialize() {
		money.setText("Current Winnings: $" + new Winnings().getWinnings());
	}

	/**
	 * First checks if a previous save is found. If a save is found, a popup is
	 * shown so the user can decide if they want to continue or start again.
	 * 
	 * @param e Button Event
	 * @throws IOException
	 */
	@FXML
	public void startGame(Event e) throws IOException {

		AttemptTrack track = new AttemptTrack();
		int[] record = track.getAttemptedRecord("NZ");
		boolean flag = false;

		// Check if the user has attempted any questions in the current save.
		for (int i : record) {
			if (i == 1) {
				flag = true;
			}
		} // If they haven't, load a new game.
		if (!flag) {
			newGame(e);
		} else { // Otherwise, show the popup to see if they would like to continue or start
					// again.
			popup.setVisible(true);
		}
	}

	/**
	 * Practice module is loaded.
	 * 
	 * @param e Button Event
	 * @throws IOException
	 */
	@FXML
	public void startPractice(Event e) throws IOException {
		quinzical.loadFXML("Practice");
	}

	/**
	 * Exit button, game closes.
	 * 
	 * @param e Button Event
	 */
	@FXML
	public void exit(Event e) {
		Stage quinzicalStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		quinzicalStage.close();
	}

	/**
	 * User selects continue game button from popup window. Old data is loaded into
	 * questions and the game continues.
	 * 
	 * @param e Button Event
	 * @throws Exception
	 */
	@FXML
	public void continueGame(Event e) throws Exception {
		FXMLLoader gameLoad = new FXMLLoader(getClass().getResource("Game.fxml"));
		Parent gameParent = gameLoad.load();
		GameController gc = gameLoad.getController();
		gc.oldGameData();
		Scene gameScene = new Scene(gameParent);
		Stage quinzicalStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		gc.setStage(quinzicalStage);
		quinzicalStage.setScene(gameScene);
		quinzicalStage.show();
		gc.checkIfAllAttempted();
	}

	/**
	 * User selects new game button from popup window. Old data is removed and
	 * questions are loaded.
	 * 
	 * @param e Button Event
	 * @throws IOException
	 */
	@FXML
	public void newGame(Event e) throws IOException {
		FXMLLoader gameLoad = new FXMLLoader(getClass().getResource("Game.fxml"));
		Parent gameParent = gameLoad.load();
		GameController gc = gameLoad.getController();
		gc.newGameData();
		Scene gameScene = new Scene(gameParent);
		Stage quinzicalStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		quinzicalStage.setScene(gameScene);
		quinzicalStage.show();
	}

}
