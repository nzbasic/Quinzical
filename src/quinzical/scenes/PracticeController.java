package quinzical.scenes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import quinzical.data.tracking.AttemptTrack;
import quinzical.data.model.Category;
import quinzical.data.CategoryLoader;
import quinzical.data.FxmlFile;
import quinzical.data.model.Question;
import quinzical.data.RandomGenerator;
import quinzical.data.Sections;

public class PracticeController extends Help {
	private CategoryLoader _loader;
	private RandomGenerator _generator;
	private List<Category> _categories;
	private ToggleButton[] _buttons;
	private ToggleButton[] _selected;
	private boolean _practiseMode = true;
	private int[] _numberSelected = new int[5];
	private TreeSet<Integer> _freeSpace = new TreeSet<Integer>();
	private int _total = 0;

	@FXML
	private ToggleButton ctg1, ctg2, ctg3, ctg4, ctg5, ctg6, ctg7, ctg8, ctg9, s1, s2, s3, s4, s5;
	@FXML
	private ToggleButton tester;
	@FXML
	private Button practise, confirm, help;
	@FXML
	private HBox selection;
	@FXML
	private Label selectionLabel, title;

	@FXML
	private void initialize() {
		_loader = new CategoryLoader(Sections.NZ);
		_generator = new RandomGenerator();
		_categories = _loader.getCategories();
		_buttons = new ToggleButton[] { ctg1, ctg2, ctg3, ctg4, ctg5, ctg6, ctg7, ctg8, ctg9 };
		_selected = new ToggleButton[] { s1, s2, s3, s4, s5 };
		for (int i = 0; i < 9; i++) {
			_buttons[i].setText(_categories.get(i).getName());
		}
		for (int i = 0; i < 5; i++) {
			_freeSpace.add(i);
		}

	}

	/**
	 * Display category selection screen
	 */
	public void setCategorySelection() {
		help.setVisible(false);
		practise.setVisible(false);
		_practiseMode = false;
		selection.setVisible(true);
		selectionLabel.setVisible(true);
		title.setText("Please select 5 categories for your game:");
	}

	@FXML
	private void practiceWrongQuestions(Event e) throws IOException {
		List<Question> list = new AttemptTrack().getWrongQuestions();
		if (list.size() == 0) {
			return;
		}
		Question question = _generator.generateRandomQuestionFromList(list);
		loadQuestion(question, e);
	}

	private void loadQuestion(Question question, Event e) throws IOException {
		FXMLLoader questionLoad = new FXMLLoader(getClass().getResource(FxmlFile.QUESTION_AND_ANSWER.getPath()));
		Parent questionParent = questionLoad.load();
		QuestionController qc = questionLoad.getController();
		qc.setPracticeMode();
		qc.setQuestion(question);

		Scene questionScene = new Scene(questionParent);
		Stage quinzicalStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		quinzicalStage.setScene(questionScene);
		quinzicalStage.show();
	}

	/**
	 * Called when the user presses any of the buttons.
	 * 
	 * @param e Button Event
	 * @throws IOException
	 */
	@FXML
	public void select(Event e) throws IOException {
		// Grabbing the button the user pressed.
		String id = ((Control) e.getSource()).getId();
		char[] chars = id.toCharArray();
		int categoryNum = Integer.parseInt(Character.toString(chars[3]));
		Category category = _categories.get(categoryNum - 1);
		if (_practiseMode == true) {

			Question question = _generator.generatePracticeQuestion(category);
			loadQuestion(question, e);

		} else {
			int index = 0;
			// User selects five categories

			// Check if the space is free
			if (_freeSpace.contains(_total)) {
				index = _total;
			}

			if (_total < 5) {
				index = _freeSpace.first(); // might try sorted list
				_numberSelected[index] = categoryNum - 1; // stores button index
				_buttons[categoryNum - 1].setVisible(false);
				_selected[index].setVisible(true);
				_freeSpace.remove(index);
				_selected[index].setText(category.getName());
				_total++;
			}
			if (_total >= 5) {
				confirm.setVisible(true);
				_buttons[categoryNum - 1].setSelected(false);
			}

		}

	}

	@FXML
	public void deselect(Event e) {
		String id = ((Control) e.getSource()).getId();
		int index = Integer.parseInt(id.substring(1));
		// deselect the button will make button invisible
		_selected[index - 1].setVisible(false);
		_freeSpace.add(index - 1);
		// the category will be visible to be selected again
		_buttons[_numberSelected[index - 1]].setVisible(true);
		_buttons[_numberSelected[index - 1]].setSelected(false);
		_total--;
		confirm.setVisible(false);
	}

	/**
	 * When user presses confirm button after they've done the selection start game
	 * 
	 * @param e
	 * @throws IOException
	 */
	@FXML
	public void finishSelection(Event e) throws IOException {
		List<String> gameCategories = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			gameCategories.add(_selected[i].getText());
		}
		RandomGenerator rg = new RandomGenerator();
		// generate game questions
		rg.setGameCategories(gameCategories);
		rg.generateGameQuestions(5, Sections.NZ);

		FXMLLoader gameLoad = new FXMLLoader(getClass().getResource(FxmlFile.GAME.getPath()));
		Parent gameParent = gameLoad.load();
		GameController gc = gameLoad.getController();
		gc.newGameData();
		Scene gameScene = new Scene(gameParent);
		Stage quinzicalStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		quinzicalStage.setScene(gameScene);
		quinzicalStage.show();
	}

	/**
	 * Return to main menu
	 * 
	 * @throws IOException
	 */
	@FXML
	public void returnToMenu(Event e) throws IOException {
		new GameController().returnToMenu(e);
	}

}
