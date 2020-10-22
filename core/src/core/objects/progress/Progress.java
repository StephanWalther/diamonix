package core.objects.progress;

import core.objects.files.*;
import core.objects.progress.highScores.*;

public class Progress {
    public final HighScores highScores;
    public final Achievements achievements;
    public TutorialPlayed tutorialPlayed;
    
    public Progress(Files files) {
        highScores = new HighScores(files);
        achievements = new Achievements(files);
        tutorialPlayed = new TutorialPlayed(files);
    }
}
