package quinzical.scenes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import quinzical.QuinzicalExceptions;
import quinzical.data.tracking.AttemptTrack;
import quinzical.data.model.Question;
import quinzical.data.tracking.WinningsTrack;

/**
 * Controller to display questions and accept user text input for when they
 * answer a question.
 */
public class QuestionController extends Help {
	@FXML
	private Label question, message, timerDisplay, fixedDisplay, firstLetter;
	@FXML
	private Text type;
	@FXML
	private TextField answer;
	@FXML
	private Button giveup, submit, continueGame, mainMenu;
	@FXML
	private AnchorPane popup;
	@FXML
	private ImageView clock;

	private int _linenumber;
	private List<Question> _questionsAndAnswers;
	private boolean _practiceMode = false;
	private boolean _internationalSection = false;
	private Question _questionObj;
	private int _retryNumber = 0;
	private String _questionText;
	private Timeline _animation;
	private boolean _gameExit =false;

	/**
	 * Sets the controller to be in practice mode. Changes behaviour when getting a
	 * question wrong.
	 */
	public void setPracticeMode() {
		_practiceMode = true;
		submit.setTranslateX(-175);
		giveup.setVisible(false);
	}

	/**
	 * Sets the current question to a question object.
	 * 
	 * @param q Question object
	 */
	public void setQuestion(Question q) {
		question.setVisible(true);
		_questionObj = q;
		question.setText(_questionObj.getQuestion());
		_questionText = replaceText(_questionObj.getQuestion());
		setType();
		speaking(_questionText, 1, 1);

	}

	public void setBonusAttempt() {
		_internationalSection = true;
	}

	public String getQuestionText() {
		return _questionText;
	}

	/**
	 * Sets the current question to a question string.
	 * 
	 * @param s Question string
	 */
	public void setQuestion(String s) {
		_questionText = replaceText(s);
		question.setText(s);
	}

	@FXML
	public void playQuestionSpeech(Event e) {
		String ButtonId = ((Control) e.getSource()).getId();
		if (ButtonId.equals("normal")) {
			speaking(_questionText, 1, 1);
		} else if (ButtonId.equals("slow")) {
			speaking(_questionText, 0, 1);
		} else if (ButtonId.equals("fast")) {
			speaking(_questionText, 2, 1);
		}

	}

	/**
	 * Sets the index and questionLines for the current question to assist with
	 * reading the question.
	 * 
	 * @param index         Index of the question
	 * @param questionLines List of questions
	 */
	public void setQuestionLines(int index, List<Question> questionLines) {
		_linenumber = index;
		_questionsAndAnswers = questionLines;
		setType();
	}

	private void setType() {
		String typeString;
		if (_questionObj != null) {
			typeString = _questionObj.getType();
		} else {
			Question q = _questionsAndAnswers.get(_linenumber - 1);
			typeString = q.getType();
		}
		type.setText(typeString + ":");
	}
	
	/**
	 * stop timer
	 */
	private void stopTimerAnimation(){
		clock.setVisible(false);
		fixedDisplay.setVisible(false);
		timerDisplay.setVisible(false);
		if (_animation != null) {
			_animation.stop();
		}
	}

	/**
	 * Called when user submits their answer. If practice mode is on, they get 3
	 * attempts. Otherwise, show if they are right or wrong.
	 * 
	 * @param e
	 */
	@FXML
	public void checkAnswer(Event e) {
		_gameExit =true;
		stopTimerAnimation();
		if (_practiceMode) {
			// increase retry number, once they hit 3 then they dont get any more attempts
			_retryNumber++;

			// Check if what they wrote is correct, update question
			if (_questionObj.checkAnswer(answer.getText())) {
				_questionObj.setResult(true);
				message.setText("Correct!");
				speaking("Correct!", 1, 1);
				
			} else {

				if (_retryNumber == 2) {
					char first = _questionObj.getFirstLetter();
					popup.setVisible(true);
					String textHint = "The first letter is: " + Character.toUpperCase(first);
					firstLetter.setText(textHint);
					speaking(textHint, 1, 2);
				}
				if (_retryNumber < 3) {
					message.setText("Incorrect, " + (3 - _retryNumber) + " attempts remain");
					message.setVisible(true);
					return;
				} else {
					firstLetter.setVisible(false);
					String answerText = "The correct answer was " + _questionObj.sayAnswer();
					message.setText(answerText);
					speaking(answerText, 1, 2);
				}
			}
		} else {

			// animation.stop();

			String usrInput = answer.getText();
			// Check Answers
			Question q = _questionsAndAnswers.get(_linenumber - 1);
			if (q.checkAnswer(usrInput)) {

				// Add Winnings
				WinningsTrack winningController = new WinningsTrack();
				winningController.readWinnings();
				winningController.updateWinnings(Integer.parseInt(q.getPrize()));
				message.setText("Correct!");
				speaking("Correct!", 1, 2);
				new AttemptTrack().removeCorrectlyAttemptedQuestion(q);
				// new HelperThread("Correct!", 1, 1).run();
			} else {

				String answerTxt = "Your answer was incorrect";
				message.setText(answerTxt);
				speaking(answerTxt, 1, 2);
				new AttemptTrack().writeWrongQuestion(q);
			}

		}

		answer.setDisable(true);
		popup.setVisible(true);
		submit.setVisible(false);
		giveup.setVisible(false);
		message.setVisible(true);
		continueGame.setVisible(true);
		mainMenu.setVisible(true);
		firstLetter.setVisible(false);
	}

	public String replaceText(String s) {
		s = s.replace("ā", "aa");
		s = s.replace("/", "or");
		return s;
	}

	/**
	 * Destroy all the current text-to-speech processes
	 */
	public void killProcesses() {
		Stream<ProcessHandle> descendents = ProcessHandle.current().descendants();
		descendents.filter(ProcessHandle::isAlive).forEach(ph -> {
			ph.destroy();
		});
	}

	/**
	 * 
	 * @param textToSpeech
	 * @param speechRate   0 for slow, 1 for normal,2 for fast
	 * @param playTime     0 if this the first time the question gets played
	 */

	public void speaking(String textToSpeech, int speechRate, int playTime) {
		textToSpeech = textToSpeech.replace("ā", "aa");
		textToSpeech = textToSpeech.replace("/", "or");
		String GameText = textToSpeech;
		Thread taskThread = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					killProcesses();
					FileWriter fw = new FileWriter("./attempt/question.scm");
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write("(voice_akl_nz_jdt_diphone)");
					bw.newLine();
					if (speechRate == 0) {
						bw.write("(Parameter.set \'Duration_Stretch 2.1)");
						bw.newLine();
					} else if (speechRate == 2) {
						bw.write("(Parameter.set \'Duration_Stretch 0.7)");
						bw.newLine();
					}

					bw.write("(SayText \"" + GameText + "\")");
					bw.close();
					Process p = new ProcessBuilder("bash", "-c", "festival -b ./attempt/question.scm").start();

					// TImer only appears after the first time the question gets played
					if (playTime == 0 ) {
						p.waitFor();
						//int gameExit = p.exitValue();
						if (!_gameExit || playTime == 3) {
							Platform.runLater(new Runnable() {
								private int count = 60;
								private String display;

								private void updateTimer() {
									if (count > 0) {
										count--;
										if (count < 10) {
											display = "0" + count;
										} else {
											display = count + "";
										}
									} else {
										checkAnswer(null);
									}

									timerDisplay.setText(display);
								}

								@Override
								public void run() {
									clock.setVisible(true);
									fixedDisplay.setVisible(true);
									timerDisplay.setVisible(true);
									_animation = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));
									_animation.setCycleCount(Timeline.INDEFINITE);
									_animation.play();

								}

							});
						}
					}

				} catch (Exception e) {
					// add our own exception class to handle runtime exceptions
					throw new QuinzicalExceptions(e.getMessage());
				}
				// bash process
			}

		});
		taskThread.start();
	}

	/**
	 * Returns user to a question selection screen, depending on if they came from
	 * Games or Practice module.
	 * 
	 * @param e
	 * @throws Exception
	 */
	@FXML
	public void returnToQuestionSelection(Event e) throws Exception {
		FXMLLoader gameLoad = null;
		if (_practiceMode) {
			gameLoad = new FXMLLoader(getClass().getResource("./fxml/Practice.fxml"));

			Parent gameParent = gameLoad.load();

			Scene gameScene = new Scene(gameParent);
			Stage quinzicalStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			quinzicalStage.setScene(gameScene);
			quinzicalStage.show();
		} else {
			if (_internationalSection) {
				new GameController().switchToInternationalQuestions(e);
			} else {
				gameLoad = new FXMLLoader(getClass().getResource("./fxml/Game.fxml"));
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
	}

	/**
	 * Returns user to the main menu.
	 * 
	 * @param e
	 * @throws IOException
	 */
	@FXML
	public void returnToMenu(Event e) throws IOException {
		stopTimerAnimation();
		killProcesses();
		new GameController().returnToMenu(e);
	}

	@FXML
	public void macronA() {
		answer.appendText("ā");
	}

	@FXML
	public void macronE() {
		answer.appendText("ē");
	}

	@FXML
	public void macronI() {
		answer.appendText("ī");
	}

	@FXML
	public void macronO() {
		answer.appendText("ō");
	}

	@FXML
	public void macronU() {
		answer.appendText("ū");
	}
}
