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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import quinzical.HelperThread;
import quinzical.model.Question;
import quinzical.model.Winnings;

/**
 * Controller to display questions and accept user text input for when they
 * answer a question.
 */
public class QuestionController {
	@FXML
	private Label question;
	@FXML
	private TextField answer;
	@FXML
	private Button giveup;
	@FXML
	private Button submit;
	@FXML
	private Label message;
	@FXML
	private Button continueGame;
	@FXML
	private Button mainMenu, normal, fast, slow;
	@FXML
	private Label firstLetter;
	@FXML
	private AnchorPane popup;

	private int lineNumber;
	private List<Question> questionsAndAnswers;
	private boolean practiceMode = false;
	private Question questionObj;
	private int retryNumber = 0;
	private String questionText;

	/**
	 * Sets the controller to be in practice mode. Changes behaviour when getting a question wrong.
	 */
	public void setPracticeMode() {
		practiceMode = true;
		giveup.setVisible(false);
	}

	/**
	 * Sets the current question to a question object.
	 * @param q Question object
	 */
	public void setQuestion(Question q) {
		question.setVisible(true);
		questionObj = q;
		question.setText(questionObj.getQuestion());
		questionText = questionObj.getQuestion();
		new HelperThread(questionText, 1).run();
	}

	/**
	 * Sets the current question to a question string.
	 * @param s Question string
	 */
	public void setQuestion(String s) {
		questionText = s;
		new HelperThread(questionText, 1).run();
		question.setText(s);
	}

	@FXML
	public void playQuestionSpeech(Event e) {

		HelperThread helper = null;
		String ButtonId = ((Control) e.getSource()).getId();
		if (ButtonId.equals("normal")) {
			helper = new HelperThread(questionText, 1);
		} else if (ButtonId.equals("slow")) {
			helper = new HelperThread(questionText, 0);
		} else if (ButtonId.equals("fast")) {
			helper = new HelperThread(questionText, 2);
		}
		helper.start();
	}

	/**
	 * Sets the index and questionLines for the current question to assist with reading the question.
	 * @param index Index of the question
	 * @param questionLines List of questions
	 */
	public void setQuestionLines(int index, List<Question> questionLines) {
		lineNumber = index;
		questionsAndAnswers = questionLines;

	}


	/**
	 * Called when user submits their answer. If practice mode is on, they get 3 attempts. Otherwise, show if they
	 * are right or wrong.
	 * @param e
	 */
	@FXML
	public void checkAnswer(Event e) {
		if (practiceMode) {
			// increase retry number, once they hit 3 then they dont get any more attempts
			retryNumber++;

			// Check if what they wrote is correct, update question
			if (questionObj.checkAnswer(answer.getText())) {
				questionObj.setResult(true);
				message.setText("Correct!");
				new HelperThread("Correct!", 1).run();
			} else {

				if (retryNumber == 2) {
					char first = questionObj.getFirstLetter();
					popup.setVisible(true);
					String textHint = "The first letter is: " + Character.toUpperCase(first);
					firstLetter.setText(textHint);
					new HelperThread(textHint, 1).run();
				}
				if (retryNumber < 3) {
					message.setText("Incorrect, " + (3 - retryNumber) + " attempts remain");
					message.setVisible(true);
					return;
				} else {
					firstLetter.setVisible(false);
					String answerText = "The correct answer was " + questionObj.sayAnswer();
					message.setText(answerText);
					new HelperThread(answerText, 1).run();
				}
			}
		} else {

			String usrInput = answer.getText();
			// Check Answers
			Question q = questionsAndAnswers.get(lineNumber - 1);
			if (q.checkAnswer(usrInput)) {
				
				// Add Winnings
				Winnings winningController = new Winnings();
				winningController.readWinnings();
				winningController.updateWinnings(Integer.parseInt(q.getPrize()));
				message.setText("Correct!");
				new HelperThread("Correct!", 1).run();
			} else {
				
				String answerTxt = "Your answer was incorrect";
				message.setText(answerTxt);
				new HelperThread(answerTxt, 1).run();
			}

		}
		popup.setVisible(true);
		submit.setVisible(false);
		giveup.setVisible(false);
		message.setVisible(true);
		continueGame.setVisible(true);
		mainMenu.setVisible(true);
		firstLetter.setVisible(false);
	}

	/**
	 * Returns user to a question selection screen, depending on if they came from Games or Practice module.
	 * @param e
	 * @throws IOException
	 */
	@FXML
	public void returnToQuestionSelection(Event e) throws IOException {
		if (practiceMode) {
			FXMLLoader gameLoad = new FXMLLoader(getClass().getResource("Practice.fxml"));

			Parent gameParent = gameLoad.load();

			Scene gameScene = new Scene(gameParent);
			Stage quinzicalStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			quinzicalStage.setScene(gameScene);
			quinzicalStage.show();
		} else {
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
	}

	/**
	 * Returns user to the main menu.
	 * @param e
	 * @throws IOException
	 */
	@FXML
	public void returnToMenu(Event e) throws IOException {
		new GameController().returnToMenu(e);
	}
}
