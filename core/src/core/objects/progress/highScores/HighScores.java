package core.objects.progress.highScores;

import java.util.*;

import core.objects.files.*;
import tools.progressLine.*;

public class HighScores {
    public static final int MAX_HIGH_SCORES = 8;
    
    private final Files files;
    private final List<Integer> highScores;
    private final HighScoresVisualizationBuilder highScoresVisualizationBuilder
      = new HighScoresVisualizationBuilder();
    private int lastInsertionIndex = -1;
    
    public HighScores(Files files) {
        this.files = files;
        highScores = files.loadHighScores();
    }
    
    public HighScoresVisualization buildHighScoresVisualization(
      ManifoldProgressLine manifoldProgressLine) {
        return highScoresVisualizationBuilder.build(manifoldProgressLine, this);
    }
    
    public void gameEnded(int points) {
        lastInsertionIndex = insertNewHighScore(points);
        if (lastInsertionIndex != -1) {
            highScores.remove(highScores.size() - 1);
            files.saveHighScores(highScores);
        }
    }
    
    private int insertNewHighScore(int points) {
        for (int i = 0; i < highScores.size(); i++) {
            if (highScores.get(i) < points) {
                highScores.add(i, points);
                return i;
            }
        }
        return -1;
    }
    
    public int getLastInsertionIndex() throws LastPointsNotInsertedException {
        if (lastInsertionIndex == -1) throw new LastPointsNotInsertedException();
        return lastInsertionIndex;
    }
    
    public List<Integer> getHighScores() {
        return new ArrayList<Integer>(highScores);
    }
    
    public int getHighScore() {
        return highScores.get(0);
    }
    
    public int getAverageHighScore() {
        int averageHighScore = 0;
        for (Integer integer : highScores) averageHighScore += integer;
        averageHighScore /= MAX_HIGH_SCORES;
        return averageHighScore;
    }
}
