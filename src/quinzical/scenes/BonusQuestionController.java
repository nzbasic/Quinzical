package quinzical.scenes;

import java.io.IOException;
import java.util.List;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import quinzical.Quinzical;
import quinzical.model.AttemptTrack;
import quinzical.model.Winnings;

public class BonusQuestionController {
	@FXML
	Label country1, country2, country3;
	@FXML
	Text points;
	@FXML
	Button b1q1, b1q2, b2q1, b2q2, b3q1, b3q2, returnMenu, NZ;
	private List<String> categoryNames;
	private AttemptTrack attempt = new AttemptTrack();
	private int count;

	/**
	 * Loads the buttons in the Game Screen with question data.
	 * 
	 * @throws IOException
	 */
	@FXML
	public void setUp() throws IOException {
		Label[] categories = { country1, country2, country3 };
		points.setText(new Winnings().getWinnings());
		categoryNames = attempt.readCategoriesGenerated("International");
		for (int i = 0; i < 3; i++) {
			categories[i].setText(categoryNames.get(i));
		}
		Button[] questions = { b1q1, b1q2, b2q1, b2q2, b3q1, b3q2 };
		// Hide questions already attempted
		boolean countCate = true;
		int[] attemptedRecord = attempt.getAttemptedRecord("International");
		// check if all questions attempted
		count = 0;

		for (int i = 0; i < 6; i++) {
			// Enable one button for each category
			if (i % 2 == 0) {
				countCate = true;
			}

			if (attemptedRecord[i] == 1) {
				// If attempted, hide button,Only Enable lowest value question
				questions[i].setVisible(false);

				count++;
			} else if (attemptedRecord[i] == 0 && countCate == true) {
				questions[i].setDisable(false);
				countCate = false;
			}

		}

	}

	/**
	 * Returns to main menu
	 * 
	 * @param e
	 * @throws IOException
	 */
	@FXML
	public void returnToMenu(Event e) throws IOException {
		Quinzical.loadFXML("Menu");
	}

	/**
	 * Get the total Number of Questions Attempted for International Section
	 * 
	 * @return
	 * @throws Exception
	 */
	public int getNumberOfQuestionsAttempted() throws Exception {
		int[] attemptedRecord = attempt.getAttemptedRecord("International");
		// check if all questions attempted
		count = 0;
		for (int i = 0; i < 6; i++) {

			if (attemptedRecord[i] == 1) {

				count++;
			}

		}
		return count;
	}

	/**
	 * Returns to NZ question set
	 * 
	 * @throws Exception
	 */
	@FXML
	public void returnToNZQuestion(Event e) throws Exception {
		new QuestionController().returnToQuestionSelection(e);
	}

	@FXML
	public void changeToAnswerScreen(Event e) throws Exception {
		new GameController().setInternationalSection(e);
	}
}
