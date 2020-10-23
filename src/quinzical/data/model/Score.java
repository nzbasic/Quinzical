package quinzical.data.model;

public class Score {

    private final String _score;
	public Score(String score) {
        this._score = score;
    }
    public int getScore() {
        return Integer.parseInt(_score);
    }
    
}
