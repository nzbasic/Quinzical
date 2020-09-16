package quinzical;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String _question;
    private String _answer;
    private String _prize;
    private boolean _attempted = false;
    private boolean _result;
    private Category _parent;

    // When reading files, the category object will be created first before each question, so add all the data to question class
    public Question(String question, String answer, String prize, Category parent) {
        _question = question;
        _answer = answer;
        _prize = prize;
        _parent = parent;
    }

    // Whenever the question is completed, set it as attempted. Might not be needed in this object depending on how we write files
    public void setAttemped() {
        _attempted = true;
    }

    // This can store true if they got it right, false if they got it wrong, I used it to colour buttons red or green after answering to show what they got right/wrong.
    public void setResult(boolean result) {
        _result = result;
    }

    public boolean isAttempted() {
        return _attempted;
    }

    public boolean getResult() {
        return _result;
    }

    public Category getParentCategory() {
        return _parent;
    }

    public String getQuestion() {
        return _question;
    }

    public String getAnswer() {
        return _answer;
    }

    public String getPrize() {
        return _prize;
    }

    //The practice module says something about giving them first letter of answer as hint
    public char getFirstLetter() {
        return _answer.charAt(0);
    }

    //Returns all the possible answers for a question (some have multiple answers)
    public List<String> getAnswersAsList() {
        String[] data = _answer.split("/");
        List<String> output = new ArrayList<String>();
        for (String answer: data) {
            output.add(answer);
        }
        return output;
    }

    public boolean checkAnswer(String userInput) {

        List<String> possibleAnswers = getAnswersAsList();

        userInput.replace("the","");
        userInput.toLowerCase().trim();
        
        for (String answer: possibleAnswers) {
            if (userInput.equals(answer.toLowerCase().trim())) {
                _attempted = true;
                _result = true;
                return true;
            } else {
                _attempted = true;
                _result = false;
                return false;
            }
        }
        return false;
    }
}
