package quinzical.scenes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import quinzical.model.AttemptTrack;
import quinzical.model.Category;
import quinzical.model.CategoryLoader;
import quinzical.model.Question;
import quinzical.model.RandomGenerator;

public class PracticeController extends Help {
	private CategoryLoader loader;
	private RandomGenerator generator;
	private List<Category> categories;
	private ToggleButton[] buttons;
	private ToggleButton[] selected;
	@FXML
	private ToggleButton ctg1, ctg2, ctg3, ctg4, ctg5, ctg6, ctg7, ctg8, ctg9, s1, s2, s3, s4, s5;
	@FXML
	private ToggleButton tester;
	@FXML
	private Button practise, confirm;
	@FXML
	private HBox selection;
	@FXML
	private Label selectionLabel, title;
	private boolean practiseMode = true;
	private int[] numberSelected = new int[5];
	private List<Integer> freeSpace = new ArrayList<Integer>();
	private int total = 0;

	@FXML
	private void initialize() {
		loader = new CategoryLoader("NZ");
		generator = new RandomGenerator();
		categories = loader.getCategories();
		buttons = new ToggleButton[] { ctg1, ctg2, ctg3, ctg4, ctg5, ctg6, ctg7, ctg8, ctg9 };
		selected = new ToggleButton[] { s1, s2, s3, s4, s5 };
		for (int i = 0; i < 9; i++) {
			buttons[i].setText(categories.get(i).getName());
		}
		for (int i = 0; i < 5; i++) {
			freeSpace.add(i);
		}

	}

	/**
	 * Display category selection screen
	 */
	public void setCategorySelection() {
		practise.setVisible(false);
		practiseMode = false;
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
		Question question = generator.generateRandomQuestionFromList(list);
		loadQuestion(question, e);
	}

	private void loadQuestion(Question question, Event e) throws IOException {
		FXMLLoader questionLoad = new FXMLLoader(getClass().getResource("QuestionAndAnswer.fxml"));
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
		Category category = categories.get(categoryNum - 1);
		if (practiseMode == true) {

			Question question = generator.generatePracticeQuestion(category);
			loadQuestion(question, e);

		} else {
			int index = 0;
			// User selects five categories

			// Check if the space is free
			if (freeSpace.contains(total)) {
				index = total;
			}

			if (total < 5) {
				index = freeSpace.get(0); // might try sorted list
				numberSelected[index] = categoryNum - 1; // stores button index
				buttons[categoryNum - 1].setVisible(false);
				selected[index].setVisible(true);
				freeSpace.remove(Integer.valueOf(index));
				selected[index].setText(category.getName());
				total++;
			}
			if (total >= 5) {
				confirm.setVisible(true);
				buttons[categoryNum - 1].setSelected(false);
			}

		}

	}

	@FXML
	public void deselect(Event e) {
		String id = ((Control) e.getSource()).getId();
		int index = Integer.parseInt(id.substring(1));
		// deselect the button will make button invisible
		selected[index - 1].setVisible(false);
		freeSpace.add(index - 1);
		// the category will be visible to be selected again
		buttons[numberSelected[index - 1]].setVisible(true);
		buttons[numberSelected[index - 1]].setSelected(false);
		total--;
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
			gameCategories.add(selected[i].getText());
		}
		RandomGenerator rg = new RandomGenerator();
		// generate game questions
		rg.setGameCategories(gameCategories);
		rg.generateGameQuestions(5, "NZ");

		FXMLLoader gameLoad = new FXMLLoader(getClass().getResource("Game.fxml"));
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
