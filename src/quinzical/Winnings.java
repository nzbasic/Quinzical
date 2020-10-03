package quinzical;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Handles the users winnings during the game.
 */
public class Winnings {

	private int points;
	private boolean exist;

	/**
	 * Default constructor for Winnings. Loads the current winnings file.
	 */
	public Winnings() {
		File winningsFile = new File("./attempt/winnings.txt");
		exist = winningsFile.exists();
	}

	/**
	 * This method adds/subtracts the amount passed into the method to the
	 * totalWinnings.
	 * 
	 * @param amount The amount to be added/subtracted.
	 */
	public void updateWinnings(int amount) {
		points = points + amount;
		try {
			FileWriter fw = new FileWriter("./attempt/winnings.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(Integer.toString(points));
			bw.newLine();
			bw.close();
		} catch (Exception e) {
			throw new quinzicalExceptions(e.getMessage());
		}
	}

	/**
	 * This method reads the total winnings in history.
	 */
	public void readWinnings() {
		try {
			String line = null;
			BufferedReader reader = new BufferedReader(new FileReader("./attempt/winnings.txt"));
			while ((line = reader.readLine()) != null) {
				points = Integer.parseInt(line);
			}
			reader.close();
		} catch (Exception e) {
			throw new quinzicalExceptions(e.getMessage());
		}
	}

	/**
	 * This method returns the total winnings as a string.
	 * 
	 * @return
	 */
	public String getWinnings() {
		if (exist) {
			readWinnings();
			return Integer.toString(points);
		}
		return "0";

	}

	/**
	 * Sets the current winnings to 0.
	 */
	public void resetWinnings() {
		points = 0;
		updateWinnings(0);
	}
}
