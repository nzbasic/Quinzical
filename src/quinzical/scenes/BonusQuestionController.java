package quinzical.scenes;

import java.io.IOException;
import java.util.List;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import quinzical.Quinzical;
import quinzical.data.tracking.AttemptTrack;
import quinzical.data.FxmlFile;
import quinzical.data.Sections;
import quinzical.data.tracking.WinningsTrack;

public class BonusQuestionController {
	@FXML
	Label country1, country2, country3;
	@FXML
	Text points;
	@FXML
	Button b1q1, b1q2, b2q1, b2q2, b3q1, b3q2, returnMenu, NZ;

	private final AttemptTrack _attempt = new AttemptTrack();
	private int _count;

	/**
	 * Loads the buttons in the Game Screen with question data.
	 *
	 */
	@FXML
	public void setUp() {
		Label[] categories = { country1, country2, country3 };
		points.setText(new WinningsTrack().getWinnings());
		List<String> _categoryNames = _attempt.readCategoriesGenerated(Sections.INTERNATIONAL);
		for (int i = 0; i < 3; i++) {
			categories[i].setText(_categoryNames.get(i));
		}
		Button[] questions = { b1q1, b1q2, b2q1, b2q2, b3q1, b3q2 };
		// Hide questions already attempted
		boolean countCate = true;
		int[] attemptedRecord = _attempt.getAttemptedRecord(Sections.INTERNATIONAL);
		// check if all questions attempted
		_count = 0;

		for (int i = 0; i < 6; i++) {
			// Enable one button for each category
			if (i % 2 == 0) {
				countCate = true;
			}

			if (attemptedRecord[i] == 1) {
				// If attempted, hide button,Only Enable lowest value question
				questions[i].setVisible(false);

				_count++;
			} else if (attemptedRecord[i] == 0 && countCate) {
				questions[i].setDisable(false);
				countCate = false;
			}

		}

	}

	/**
	 * Returns to main menu
	 * 
	 * @throws IOException file not found
	 */
	@FXML
	public void returnToMenu() throws IOException {
		Quinzical.loadFXML(FxmlFile.MENU);
	}

	/**
	 * Get the total Number of Questions Attempted for International Section
	 * 
	 * @return Number of questions attempted
	 */
	public int getNumberOfQuestionsAttempted() {
		int[] attemptedRecord = _attempt.getAttemptedRecord(Sections.INTERNATIONAL);
		// check if all questions attempted
		_count = 0;
		for (int i = 0; i < 6; i++) {

			if (attemptedRecord[i] == 1) {

				_count++;
			}

		}
		return _count;
	}

	/**
	 * Returns to NZ question set
	 * 
	 * @throws Exception File not found
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
