package quinzical;

import java.util.ArrayList;
import java.util.List;

/**
 * Object to store a category, and its list of questions
 */
public class Category {
    private List<Question> _questions = null;
    private String _name;

    /**
     * Default constructor for Category
     * @param name Name of category
     */
    public Category(String name) {
        _name = name;
        _questions = new ArrayList<Question>();
    }

    /**
     * Returns name of the category
     * @return
     */
    public String getName() {
        return _name;
    }

    /**
     * Returns list of question objects stored in the category.
     * @return
     */
    public List<Question> getQuestions() {
        return _questions;
    }

    /**
     * Adds a question to the category
     * @param question
     */
    public void add(Question question) {
        _questions.add(question);
    }

    /**
     * Checks if this category has any unattempted questions. Returns false if question is found.
     * @return
     */
    public boolean isEmpty() {
        for (Question question : _questions) {
            if (!question.isAttempted()) {
                return false;
            }
        }
        return true;
    }

}
