package core.objects.audio;

import tools.progressLine.*;

public class LimitedSound extends Sound {
    private final Timer timer;
    
    LimitedSound(String string, float timeLimit, SoundPlayer soundPlayer) {
        super(string, soundPlayer);
        timer = new Timer(timeLimit);
        timer.setTime(timer.getEndTime());
        timer.increase();
    }
    
    public void update(float dt) {
        timer.update(dt);
    }
    
    public void play() {
        if (timer.getProgress() == 1) {
            timer.setTime(0);
            super.play();
        }
    }
}
