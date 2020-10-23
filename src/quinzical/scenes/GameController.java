package quinzical.scenes;

import java.io.IOException;
import java.util.List;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import quinzical.data.AttemptTrack;
import quinzical.data.HighscoreTrack;
import quinzical.data.Question;
import quinzical.data.Score;
import quinzical.data.Sections;
import quinzical.data.Winnings;
import quinzical.Quinzical;

/**
 * Controller for the screen where the user selects which question they would
 * like to pick.
 */
public class GameController extends Help {

	@FXML
	private Label category1, category2, category3, category4, category5, points;
	@FXML
	private Button c1q1, c1q2, c1q3, c1q4, c1q5, c2q1, c2q2, c2q3, c2q4, c2q5, c3q1, c3q2, c3q3, c3q4, c3q5, c4q1, c4q2,
			c4q3, c4q4, c4q5, c5q1, c5q2, c5q3, c5q4, c5q5, returnMenu, unlock;
	@FXML
	AnchorPane finalPopUp;

	private Button[] _questionbuttons;
	private int[] _completedCategories = { 0, 0, 0, 0, 0 };
	private AttemptTrack _attempt = new AttemptTrack();
	private List<String> _categoryNames;
	private List<Question> _allq;
	private int _count;
	private boolean _internationalSection = false;
	private Stage _gameStage;

	/**
	 * Starts a new game of quinzical, throws out old data.
	 * 
	 * @throws IOException
	 */
	public void newGameData() throws IOException {
		_attempt.resetAll();
		setupGame();

	}

	/**
	 * Loads data from old save file.
	 * 
	 * @throws IOException
	 */
	public void oldGameData() throws IOException {
		setupGame();

	}

	/**
	 * Loads the buttons in the Game Screen with question data.
	 * 
	 * @throws IOException
	 */
	public void setupGame() throws IOException {
		int attemptedInCurrentCate = 0;
		Label[] categories = { category1, category2, category3, category4, category5 };
		points.setText(new Winnings().getWinnings());
		_categoryNames = _attempt.readCategoriesGenerated(Sections.NZ);
		for (int i = 0; i < 5; i++) {
			categories[i].setText(_categoryNames.get(i));
		}
		Button[] questions = { c1q1, c1q2, c1q3, c1q4, c1q5, c2q1, c2q2, c2q3, c2q4, c2q5, c3q1, c3q2, c3q3, c3q4, c3q5,
				c4q1, c4q2, c4q3, c4q4, c4q5, c5q1, c5q2, c5q3, c5q4, c5q5 };
		_questionbuttons = questions;
		// Hide questions already attempted
		boolean countCate = true;
		int[] attemptedRecord = _attempt.getAttemptedRecord(Sections.NZ);
		// check if all questions attempted
		_count = 0;

		for (int i = 0; i < attemptedRecord.length; i++) {
			// Enable one button for each category
			if (i % 5 == 0) {
				countCate = true;
				int quotient = i / 5;
				if (quotient > 0) {
					_completedCategories[quotient - 1] = attemptedInCurrentCate;
				}
				attemptedInCurrentCate = 0;
			}

			if (attemptedRecord[i] == 1) {
				// If attempted, hide button,Only Enable lowest value question
				questions[i].setVisible(false);
				attemptedInCurrentCate++;

				_count++;
			} else if (attemptedRecord[i] == 0 && countCate == true) {
				questions[i].setDisable(false);
				countCate = false;
			}
			// Record for number of completed Questions for the last category
			if (i == 24) {
				_completedCategories[4] = attemptedInCurrentCate;
			}

		}

	}

	/**
	 * Sets the stage of this controller.
	 * 
	 * @param s
	 */
	public void setStage(Stage s) {
		_gameStage = s;
	}

	/**
	 * Checks if all questions have been attempted, if they have, go to reward
	 * screen. If 2 categories have been attempted unlock International questions.
	 * 
	 * @throws Exception
	 */
	public void checkIfAllAttempted() throws Exception {

		if (_count == 25) {
			// Ask user if they would like to attempt remaining questions in International
			// Section
			if (new BonusQuestionController().getNumberOfQuestionsAttempted() < 6) {
				finalPopUp.setVisible(true);
				unlock.setDisable(true);
			} else {
				// All questions attempted, go to reward screen
				toRewardScreen();
			}

		}
		// Check if International Questions can be unlocked
		int numCompleted = 0;
		for (int i = 0; i < 5; i++) {
			if (_completedCategories[i] == 5) {
				numCompleted++;
			}
			if (numCompleted == 2) {
				unlock.setVisible(true);
				break;
			}
		}

	}

	/**
	 * Switch to RewardScreen
	 * 
	 * @throws IOException
	 */
	@FXML
	public void toRewardScreen() throws IOException {
		String winnings = new Winnings().getWinnings();
		Score score = new Score(winnings);
		HighscoreTrack tracker = new HighscoreTrack();
		tracker.addScore(score);
		FXMLLoader rewardLoad = new FXMLLoader(getClass().getResource("./fxml/Reward.fxml"));
		Parent rewardParent = rewardLoad.load();
		RewardController rc = rewardLoad.getController();
		rc.setPoints(winnings);

		Scene rewardScene = new Scene(rewardParent);

		_gameStage.setScene(rewardScene);
		_gameStage.show();
	}

	public void setInternationalSection(Event e) throws Exception {
		_internationalSection = true;
		changeToAnswerScreen(e);
	}

	/**
	 * When user selects a question, load the answer screen with that question's
	 * data.
	 * 
	 * @param e
	 * @throws IOException
	 */
	@FXML
	public void changeToAnswerScreen(Event e) throws IOException {
		// Get Id of button clicked, works if clicked object extends control
		Sections section = null;
		String ButtonId = ((Control) e.getSource()).getId();
		int lineNumber = 0;
		// Set button invisible
		((Button) e.getSource()).setVisible(false);

		// Get Question
		int categoryIndex = Integer.parseInt(ButtonId.substring(1, 2));
		int questionIndex = Integer.parseInt(ButtonId.substring(3));
		// Set Question as attempted
		if (ButtonId.substring(0, 1).equals("c")) {
			section = Sections.NZ;
			lineNumber = (categoryIndex - 1) * 5 + questionIndex;
			// Enable nextbutton click
			if (questionIndex < 5) {
				(_questionbuttons[lineNumber]).setDisable(false);
			}
		} else {
			section = Sections.INTERNATIONAL;
			lineNumber = (categoryIndex - 1) * 2 + questionIndex;
		}
		new AttemptTrack().setAttempted(lineNumber - 1, section);
		// questionObjects
		_allq = _attempt.getQuestionsGenerated(section);

		String question = _allq.get(lineNumber - 1).getQuestion();

		FXMLLoader questionLoad = new FXMLLoader(getClass().getResource("./fxml/QuestionAndAnswer.fxml"));
		Parent questionParent = questionLoad.load();
		QuestionController qc = questionLoad.getController();
		if (_internationalSection) {
			qc.setBonusAttempt();
		}
		qc.setQuestion(question);
		qc.setQuestionLines(lineNumber, _allq);

		Scene questionScene = new Scene(questionParent);
		Stage quinzicalStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		quinzicalStage.setScene(questionScene);
		quinzicalStage.show();
		qc.speaking(qc.getQuestionText(), 1, 0);
	}

	/**
	 * User chooses to attempt Questions from the international Section
	 * 
	 * @param e
	 * @throws IOException
	 */
	@FXML
	public void switchToInternationalQuestions(Event e) throws IOException {
		FXMLLoader questionLoad = new FXMLLoader(getClass().getResource("./fxml/InternationalQuestions.fxml"));
		Parent questionParent = questionLoad.load();
		BonusQuestionController qc = questionLoad.getController();
		qc.setUp();
		Scene questionScene = new Scene(questionParent);
		Stage quinzicalStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		quinzicalStage.setScene(questionScene);
		quinzicalStage.show();
	}

	/**
	 * Returns user to the main menu.
	 * 
	 * @param e
	 * @throws IOException
	 */
	@FXML
	public void returnToMenu(Event e) throws IOException {
		Quinzical.loadFXML("Menu");
	}

	@Override
	@FXML
	public void help() {
		if (finalPopUp.isVisible()) {
			return;
		} else {
			super.help();
		}
	}
}
