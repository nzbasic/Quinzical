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
import javafx.stage.Stage;
import quinzical.Category;
import quinzical.CategoryLoader;
import javafx.scene.Node;
import quinzical.Question;
import quinzical.RandomGenerator;

/**
 * Controller for the practice module. The user selects which category they
 * would like a question from.
 */
public class PracticeController {
    private CategoryLoader loader;
    private RandomGenerator generator;
    private List<Category> categories;

    @FXML
    private Button ctg1, ctg2, ctg3, ctg4, ctg5, ctg6, ctg7, ctg8, ctg9;

    @FXML
    public void initialize() {
        loader = new CategoryLoader();
        generator = new RandomGenerator();
        categories = loader.getCategories();
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
        String ButtonId = ((Control) e.getSource()).getId();
        // Based on the id of the button, get the equivalent category.
        int categoryNumber = Integer.parseInt(ButtonId.substring(3));
        Question questionSelected = generator.generatePracticeQuestion(categories.get(categoryNumber - 1));
        FXMLLoader questionLoad = new FXMLLoader(getClass().getResource("QuestionAndAnswer.fxml"));
        Parent questionParent = questionLoad.load();
        QuestionController qc = questionLoad.getController();
        qc.setPracticeMode();
        qc.setQuestion(questionSelected);

        Scene questionScene = new Scene(questionParent);
        Stage quinzicalStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        quinzicalStage.setScene(questionScene);
        quinzicalStage.show();
    }
}
