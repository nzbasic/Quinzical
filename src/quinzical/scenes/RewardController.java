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

	public void setPoints(String total) {
		points.setText(total);
	}

	@FXML
	public void returnToMainMenu(Event e) throws IOException {
		new GameController().returnToMenu(e);
	}

	@FXML
	public void restartGame(Event e) throws IOException {
		new MenuController().startGame(e);
	}
}
