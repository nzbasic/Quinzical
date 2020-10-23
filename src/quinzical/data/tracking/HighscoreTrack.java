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

public class HighscoreTrack {

    private final List<Score> _scores = new ArrayList<>();

    public HighscoreTrack() {
        File file = new File(Files.SCORES.toString());
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

    public List<Score> getScores() {
        _scores.sort(Comparator.comparing(Score::getScore));
        Collections.reverse(_scores);
        return _scores;
    }

    public void addScore(Score newScore) throws IOException {
        _scores.add(newScore);
        File file = new File(Files.SCORES.toString());
        FileWriter fw = new FileWriter(file);
        for (Score score : _scores) {
            fw.write(score.getScore() + "\n");
        }
        fw.close();
    }
}
