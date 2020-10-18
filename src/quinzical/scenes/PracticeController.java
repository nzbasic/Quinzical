package quinzical.scenes;

import java.io.IOException;
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
import javafx.stage.Stage;
import javafx.scene.Node;
import quinzical.model.AttemptTrack;
import quinzical.model.Category;
import quinzical.model.CategoryLoader;
import quinzical.model.Question;
import quinzical.model.RandomGenerator;

public class PracticeController {
    private CategoryLoader loader;
    private RandomGenerator generator;
    private List<Category> categories;
    private ToggleButton[] buttons;
    @FXML
    private ToggleButton ctg1,ctg2,ctg3,ctg4,ctg5,ctg6,ctg7,ctg8,ctg9;
    @FXML private ToggleButton tester;
    @FXML private Button practise;
    private boolean practiseMode=true;
    
    @FXML
    private void initialize() {
        loader = new CategoryLoader("NZ");
        generator = new RandomGenerator();
        categories = loader.getCategories();
        buttons = new ToggleButton[]{ctg1,ctg2,ctg3,ctg4,ctg5,ctg6,ctg7,ctg8,ctg9};
        for (int i=0; i < 9; i++) {
            buttons[i].setText(categories.get(i).getName());
        }
    }
    
    /**
     * Display category selection screen
     */
    public void setCategorySelection() {
    	practise.setVisible(false);
    	practiseMode=false;

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
    	if (practiseMode==true) {
        String id = ((Control) e.getSource()).getId();
        char[] chars = id.toCharArray();
        int categoryNum = Integer.parseInt(Character.toString(chars[3]));
        Category category = categories.get(categoryNum-1);
        Question question = generator.generatePracticeQuestion(category);
        loadQuestion(question,e);
        //Question question = generator.generatePracticeQuestion(category);
        //loadQuestion(question, e);
    	}
    	
    }

    /**
     * Return to main menu
     * @throws IOException 
     */
    @FXML
    public void returnToMenu(Event e) throws IOException {
    	new GameController().returnToMenu(e);
    }

}
