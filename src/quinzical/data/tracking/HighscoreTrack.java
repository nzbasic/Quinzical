package quinzical.data.tracking;

import quinzical.data.Files;
import quinzical.data.model.Score;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * This class loads and adds to the score leaderboard.
 */
public class HighscoreTrack {

	private List<Score> _scores = new ArrayList<Score>();

	/**
	 * Loads the current scores if they exist to the internal list.
	 */
	public HighscoreTrack() {
		File file = new File("." + Files.SCORES.toString());
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Scanner sc;
		try {
			sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				Score score = new Score(line);
				_scores.add(score);
			}
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return List of score objects, sorted in descending order.
	 */
	public List<Score> getScores() {
		Collections.sort(_scores, Comparator.comparing(Score::getScore));
		Collections.reverse(_scores);
		return _scores;
	}

	/**
	 * Adds a new score to the internal list and the local file.
	 * 
	 * @param newScore score object to add
	 * @throws IOException file not found
	 */
	public void addScore(Score newScore) throws IOException {
		_scores.add(newScore);
		File file = new File("." + Files.SCORES.toString());
		FileWriter fw = new FileWriter(file);
		for (Score score : _scores) {
			fw.write(score.getScore() + "\n");
		}
		fw.close();
	}
}
