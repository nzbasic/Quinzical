package quinzical.data;

import quinzical.QuinzicalExceptions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Handles the users winnings during the game.
 */
public class Winnings {

	private int _points;
	private boolean _exist;

	private final static String _winningsFile = "./attempt/winnings.txt";

	/**
	 * Default constructor for Winnings. Loads the current winnings file.
	 */
	public Winnings() {
		File winningsFile = new File(_winningsFile);
		_exist = winningsFile.exists();
	}

	/**
	 * This method adds/subtracts the amount passed into the method to the
	 * totalWinnings.
	 * 
	 * @param amount The amount to be added/subtracted.
	 */
	public void updateWinnings(int amount) {
		_points = _points + amount;
		try {
			FileWriter fw = new FileWriter(_winningsFile);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(Integer.toString(_points));
			bw.newLine();
			bw.close();
		} catch (Exception e) {
			throw new QuinzicalExceptions(e.getMessage());
		}
	}

	/**
	 * This method reads the total winnings in history.
	 */
	public void readWinnings() {
		try {
			String line = null;
			BufferedReader reader = new BufferedReader(new FileReader(_winningsFile));
			while ((line = reader.readLine()) != null) {
				_points = Integer.parseInt(line);
			}
			reader.close();
		} catch (Exception e) {
			throw new QuinzicalExceptions(e.getMessage());
		}
	}

	/**
	 * This method returns the total winnings as a string.
	 * 
	 * @return
	 */
	public String getWinnings() {
		if (_exist) {
			readWinnings();
			return Integer.toString(_points);
		}
		return "0";

	}

	/**
	 * Sets the current winnings to 0.
	 */
	public void resetWinnings() {
		_points = 0;
		updateWinnings(0);
	}
}
