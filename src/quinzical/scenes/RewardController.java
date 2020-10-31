package quinzical.scenes;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import quinzical.data.tracking.AttemptTrack;
import quinzical.data.tracking.HighscoreTrack;
import quinzical.data.model.Score;

import java.io.IOException;
import java.util.List;

/**
 * Controller to display the users winnings once they have completed the game.
 */
public class RewardController {

	@FXML
	private TableView<Score> table;
	@FXML
	private TableColumn<Score, String> highscores;
	@FXML
	private Label points;

	@FXML
	private void initialize() {
		new AttemptTrack().resetAll();
		HighscoreTrack reader = new HighscoreTrack();
		List<Score> scores = reader.getScores();
		highscores.setCellValueFactory(new PropertyValueFactory<>("score"));
		for (Score score : scores) {
			table.getItems().add(score);
		}
	}

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
