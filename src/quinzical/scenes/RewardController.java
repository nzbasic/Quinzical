package quinzical.scenes;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * Controller to display the users winnings once they have completed the game.
 */
public class RewardController {

	@FXML
	private Label points;

	/**
	 * Sets the current winnings of the user for display.
	 * 
	 * @param total Points
	 */
	public void setPoints(String total) {
		points.setText(total);
	}

	/**
	 * Returns user to the main menu, keeps data.
	 * 
	 * @param e Button event
	 * @throws IOException File not found
	 */
	@FXML
	public void returnToMainMenu(Event e) throws IOException {
		new GameController().returnToMenu(e);
	}

	/**
	 * Restarts the game, removing old data.
	 * 
	 * @param e Button event
	 * @throws IOException File not found
	 */
	@FXML
	public void restartGame(Event e) throws IOException {
		new MenuController().newGame(e);
	}
}
