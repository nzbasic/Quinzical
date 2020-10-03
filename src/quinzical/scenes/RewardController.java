package quinzical.scenes;

import java.io.IOException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controller to display the users winnings once they have completed the game.
 */
public class RewardController {

	@FXML
	private Label points;
	@FXML
	private Button returnMenu, newGame;

	/**
	 * Sets the current winnings of the user for display.
	 * @param total
	 */
	public void setPoints(String total) {
		points.setText(total);
	}

	/**
	 * Returns user to the main menu, keeps data.
	 * @param e
	 * @throws IOException
	 */
	@FXML
	public void returnToMainMenu(Event e) throws IOException {
		new GameController().returnToMenu(e);
	}

	/**
	 * Restarts the game, removing old data.
	 * @param e
	 * @throws IOException
	 */
	@FXML
	public void restartGame(Event e) throws IOException {
		new MenuController().newGame(e);
	}
}
