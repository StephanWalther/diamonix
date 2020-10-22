package core.objects.progress;

import core.objects.files.*;

public class TutorialPlayed {
    private final Files files;
    private boolean played;
    
    TutorialPlayed(Files files) {
        this.files = files;
        played = files.loadTutorialPlayed();
    }
    
    public boolean wasPlayed() {
        return played;
    }
    
    public void notifyWasPlayed() {
        if (played) return;
        played = true;
        files.saveTutorialPlayed();
    }
}
