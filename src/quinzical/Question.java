package quinzical;

import java.util.ArrayList;
import java.util.List;

/**
 * Object to store Quinzical question data
 */
public class Question {
    private String _question;
    private String _answer;
    private String _prize;
    private boolean _attempted = false;
    private boolean _result;
    private Category _parent;

    /**
     * Default constructor for a question object.
     * @param question The Question text
     * @param answer The answer text
     * @param prize The prize the user will win when they get the question correct
     * @param parent The category which the question exists within
     */
    public Question(String question, String answer, String prize, Category parent) {
        _question = question;
        _answer = answer;
        _prize = prize;
        _parent = parent;
    }

    /**
     * Sets the status of this question, if it has been attempted or not.
     */
    public void setAttemped() {
        _attempted = true;
    }

    /**
     * Sets the result of the question, if they got it right or wrong.
     * @param result
     */
    public void setResult(boolean result) {
        _result = result;
    }

    /**
     * Returns the status of this question, if it has been attempted or not.
     * @return
     */
    public boolean isAttempted() {
        return _attempted;
    }

    /**
     * Returns the result of this question, if it was answered correctly or not.
     * @return
     */
    public boolean getResult() {
        return _result;
    }

    /**
     * Returns the parent category of this question.
     * @return
     */
    public Category getParentCategory() {
        return _parent;
    }

    /**
     * Returns the question text
     * @return
     */
    public String getQuestion() {
        return _question;
    }

    /**
     * Returns the first answer for this question
     * @return
     */
    public String sayAnswer() {
        String[] data = _answer.split("/");
        return data[0];
    }
    
    /**
     * Returns the full answer string for this question
     * @return
     */
    public String getAnswer() {
    	return _answer;
    }

    /**
     * Returns the prize number for this question
     * @return
     */
    public String getPrize() {
        return _prize;
    }

    /**
     * Returns the first letter of the answer.
     * @return
     */
    public char getFirstLetter() {
        return _answer.charAt(0);
    }

    // Returns all the possible answers for a question (some have multiple answers)
    public List<String> getAnswersAsList() {
        String[] data = _answer.split("/");
        List<String> output = new ArrayList<String>();
        for (String answer : data) {
            output.add(answer);
        }
        return output;
    }

    /**
     * Checks a user string against the answer stored in the object. Returns true if they match.
     * @param userInput
     * @return
     */
    public boolean checkAnswer(String userInput) {

        List<String> possibleAnswers = getAnswersAsList();

        userInput = userInput.replace("the", "").toLowerCase().trim();

        for (String answer : possibleAnswers) {
            if (userInput.equals(answer.toLowerCase().trim())) {
                _attempted = true;
                _result = true;
                return true;
            }
        }
        _attempted = true;
        _result = false;
        return false;
    }
}