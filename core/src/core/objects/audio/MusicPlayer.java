package core.objects.audio;

import com.badlogic.gdx.audio.*;

import java.util.*;

import core.objects.assets.*;
import tools.common.*;

public class MusicPlayer {
    private final Assets assets;
    private final Volume volume;
    private float fadeOutTime = 1.f, fadeInTime = 1.f;
    private final List<FadeMusic> fadeOutMusics = new ArrayList<FadeMusic>();
    private FadeMusic currentMusic;
    
    MusicPlayer(final Assets assets, final Volume volume) {
        this.assets = assets;
        this.volume = volume;
    }
    
    public void setFadeOutTime(float fadeOutTime) {
        this.fadeOutTime = fadeOutTime;
    }
    
    public void setFadeInTime(float fadeInTime) {
        this.fadeInTime = fadeInTime;
    }
    
    public void playMusic(String song) {
        Music nextMusic = assets.getMusic(song);
        if (currentMusic != null) {
            if (nextMusic == currentMusic.music) return;
            currentMusic.fadeOut(fadeOutTime);
            fadeOutMusics.add(currentMusic);
        }
        currentMusic = new FadeMusic(volume, nextMusic, fadeInTime);
    }
    
    public void update(final float dt) {
        if (currentMusic != null) currentMusic.update(dt);
        UpdateDistributer.updateAll(fadeOutMusics, dt);
        Remover.removeAllDoneElements(fadeOutMusics);
    }
}
