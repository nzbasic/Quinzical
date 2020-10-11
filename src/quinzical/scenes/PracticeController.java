package quinzical.scenes;

import java.io.IOException;
import java.util.List;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
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
    @FXML
    private ComboBox<Category> categoryBox;

    @FXML
    private void initialize() {

        loader = new CategoryLoader();
        generator = new RandomGenerator();
        categories = loader.getCategories();

        for (Category category : categories) {
            categoryBox.getItems().add(category);
        }

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
        Category category = categoryBox.getValue();
        Question question = generator.generatePracticeQuestion(category);
        loadQuestion(question, e);

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
