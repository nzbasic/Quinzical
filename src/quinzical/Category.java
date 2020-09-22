package quinzical;


import java.util.ArrayList;
import java.util.List;


public class Category {
    private List<Question> _questions = null;
    private String _name;

    public Category(String name) {
        _name = name;
        _questions = new ArrayList<Question>();
    }

    public String getName() {
        return _name;
    }

    // Might not be needed, 
    public List<Question> getQuestions() {
        return _questions;
    }

    public void add(Question question) {
        _questions.add(question);
    }

    // Might not be needed, I think your file writing does this already
    public boolean isEmpty() {
        for (Question question: _questions) {
            if (!question.isAttempted()) {
                return false;
            }
        }
        return true;
    }
    
}
