package quinzical.scenes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import quinzical.data.tracking.AttemptTrack;
import quinzical.data.model.Category;
import quinzical.Quinzical;
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
	private boolean _practiceMode = true;
	private int[] _numberSelected = new int[5];
	private TreeSet<Integer> _freeSpace = new TreeSet<Integer>();
	private int _total = 0;

	@FXML
	private ToggleButton ctg1, ctg2, ctg3, ctg4, ctg5, ctg6, ctg7, ctg8, ctg9, s1, s2, s3, s4, s5;
	@FXML
	private ToggleButton tester;
	@FXML
	private Button practise, confirm, help,random;
	@FXML
	private HBox selection;
	@FXML
	private Label selectionLabel, title;
	@FXML
	private AnchorPane categoryOverlay;

	@FXML
	private void initialize() {
		_loader = new CategoryLoader(Sections.NZ);
		_generator = new RandomGenerator();
		List<Question> list = new AttemptTrack().getWrongQuestions();
		if (list.size() == 0) {
			practise.setVisible(false);
		}
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
		help.setVisible(true);
		practise.setVisible(false);
		_practiceMode = false;
		selection.setVisible(true);
		selectionLabel.setVisible(true);
		random.setVisible(true);
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
		QuestionController qc = (QuestionController) Quinzical.loadGetController(FxmlFile.QUESTION_AND_ANSWER);
		qc.setPracticeMode();
		qc.setQuestion(question);

		Quinzical.loadStoredFXML();
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
		if (_practiceMode == true) {

			Question question = _generator.generatePracticeQuestion(category);
			loadQuestion(question, e);

		} else {
			int index = 0;
			
			// Check if the space is free
			if (_freeSpace.contains(_total)) {
				index = _total;
			}

			if (_total < 5) {
				index = _freeSpace.first(); 
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

		
		GameController gc = (GameController)Quinzical.loadGetController(FxmlFile.GAME);
		gc.newGameData();
		Quinzical.loadStoredFXML();
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
    
	@FXML
	public void reset() {
	    _total=0;
	    initialize();
	    for (ToggleButton b :_buttons) {
	    	b.setVisible(true);
	    }
	    setCategorySelection();
        
		
	}
	/**
	 * Generate 5 random categories for users.
	 */
	@FXML
	public void generateRandomCategories() {
		
		reset();
		RandomGenerator myRG=new RandomGenerator();
		myRG.generateCategoriesAtRandom(5, Sections.NZ);
		
		_total=5;
	    int i=0;
		for (String s:myRG.getGeneratedCategories()) {
		    for (ToggleButton b :_buttons) {
		    	if ( b.getText().equals(s)) {
			     b.setVisible(false);
			     char[] chars = b.getId().toCharArray();
				 int categoryNum = Integer.parseInt(Character.toString(chars[3]));
			     _numberSelected[i] = categoryNum-1;// stores button index
			     _selected[i].setVisible(true);
			     _selected[i].setText(s);
			     _freeSpace.remove(i);
			     i++;
		    	}
		    }
        
		}
		
		confirm.setVisible(true);
		
	}

	@Override
	public void help() {
		if (_practiceMode) {
			super.help();
		} else {
			categoryOverlay.setVisible(true);
		}
	}

	@Override
	public void helpDown() {
		if (_practiceMode) {
			super.helpDown();
		} else {
			categoryOverlay.setVisible(false);
		}
	}
}
