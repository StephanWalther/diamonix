package core.objects.audio;

import core.objects.assets.*;

public class SoundPlayer {
    private final Assets assets;
    private final Volume volume;
    
    SoundPlayer(Assets assets, Volume volume) {
        this.assets = assets;
        this.volume = volume;
    }
    
    public void playSound(String string) {
        playSound(string, 1);
    }
    
    public void playSound(String string, float volume) {
        if (volume < 0 || 1 < volume) {
            throw new IllegalArgumentException("Volume has to be between 0 and 1.");
        }
        com.badlogic.gdx.audio.Sound sound = assets.getSound(string);
        sound.play(this.volume.getGlobalVolume()*this.volume.getSoundVolume()*volume);
    }
    
    public Sound getSound(String string) {
        return new Sound(string, this);
    }
    
    public LimitedSound getLimitedSound(String string, float timeLimit) {
        return new LimitedSound(string, timeLimit, this);
    }
}
