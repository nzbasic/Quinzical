package quinzical.data.model;

/**
 * Data class for scores, used in the reward screen leaderboard.
 */
public class Score {

    private String _score;
	public Score(String score) {
        this._score = score;
    }
    public int getScore() {
        return Integer.parseInt(_score);
    }
    
}
