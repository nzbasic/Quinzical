package quinzical.model;

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
	private String _type;

	/**
	 * Default constructor for a question object.
	 * 
	 * @param question The Question text
	 * @param answer   The answer text
	 * @param prize    The prize the user will win when they get the question
	 *                 correct
	 * @param type     e.g. What is, Who is..
	 * @param parent   The category which the question exists within
	 */
	public Question(String question, String answer, String prize, String type, Category parent) {
		_question = question;
		_answer = answer;
		_prize = prize;
		_parent = parent;
		_type = type;
	}

	/**
	 * Sets the status of this question, if it has been attempted or not.
	 */
	public void setAttemped() {
		_attempted = true;
	}

	/**
	 * Sets the result of the question, if they got it right or wrong.
	 * 
	 * @param result
	 */
	public void setResult(boolean result) {
		_result = result;
	}

	/**
	 * @return Status of this question, if it has been attempted or not.
	 */
	public boolean isAttempted() {
		return _attempted;
	}

	/**
	 * @return Result of this question, if it was answered correctly or not.
	 */
	public boolean getResult() {
		return _result;
	}

	/**
	 * @return Parent category of this question.
	 */
	public Category getParentCategory() {
		return _parent;
	}

	/**
	 * @return Question text
	 */
	public String getQuestion() {
		return _question;
	}

	/**
	 * @return First answer for this question
	 */
	public String sayAnswer() {
		String[] data = _answer.split("/");
		return data[0];
	}

	/**
	 * @return Full answer string for this question
	 */
	public String getAnswer() {
		return _answer;
	}

	/**
	 * @return Question type, e.g. "What is"
	 */
	public String getType() {
		return _type;
	}

	/**
	 * @return Prize amount for this question
	 */
	public String getPrize() {
		return _prize;
	}

	/**
	 * @return First letter of the answer.
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
	 * Checks a user string against the answer stored in the object. Returns true if
	 * they match.
	 * 
	 * @param userInput
	 * @return true or false
	 */
	public boolean checkAnswer(String userInput) {

		List<String> possibleAnswers = getAnswersAsList();

		String output = "";
		String[] data = userInput.split(" ");
		if (data[0].equals("the")) {
			int length = data.length;
			for (int i = 1; i < length; i++) {
				output = output + data[i];
			}
		} else {
			output = userInput;
		}

		userInput = output.toLowerCase().trim();

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