package quinzical.model;

public class Score {

    private String score;
	public Score(String score) {
        this.score = score;
    }
    public int getScore() {
        return Integer.parseInt(score);
    }
    
}
