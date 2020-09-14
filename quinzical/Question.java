package quinzical;

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

}