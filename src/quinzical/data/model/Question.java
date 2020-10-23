package quinzical.data.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Object to store Quinzical question data
 */
public class Question {
	private final String _question;
	private final String _answer;
	private final String _prize;
	private boolean _attempted = false;
	private boolean _result;
	private final Category _parent;
	private final String _type;

	public final static String delimiter = ",";
	public final static String answerDelimiter = "/";

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
	 * Sets the result of the question, if they got it right or wrong.
	 * 
	 * @param result true for correct false for incorrect
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
	 * @return Question text
	 */
	public String getQuestion() {
		return _question;
	}

	/**
	 * @return First answer for this question
	 */
	public String sayAnswer() {
		String[] data = _answer.split(Question.answerDelimiter);
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
		String[] data = _answer.split(Question.answerDelimiter);
		List<String> output = new ArrayList<>();
		Collections.addAll(output, data);
		return output;
	}

	/**
	 * Checks a user string against the answer stored in the object. Returns true if
	 * they match.
	 * 
	 * @param userInput the users answer
	 * @return true or false
	 */
	public boolean checkAnswer(String userInput) {

		List<String> possibleAnswers = getAnswersAsList();

		StringBuilder output = new StringBuilder();
		String[] data = userInput.split(" ");
		int length = data.length;
		if (data[0].equals("the") || data[0].equals("a")) {
			for (int i = 1; i < length; i++) {
				output.append(data[i]);
			}
		} else {
			output = new StringBuilder(userInput);
		}

		userInput = output.toString().toLowerCase().trim();

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

	public String getFormattedStringWrong() {
		return _question + Question.delimiter + _answer + Question.delimiter + _type + "\n";
	}

	public String getFormattedString() {
		return _question + Question.delimiter + _answer + Question.delimiter + _prize + Question.delimiter
		+ _parent.getName() + Question.delimiter + _type;
	}
}