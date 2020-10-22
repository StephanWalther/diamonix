package core.objects.audio;

import com.badlogic.gdx.math.*;

import java.io.*;

public class Volume {
    private float globalVolume = 1.f;
    private float musicVolume = 1.f;
    private float soundVolume = 1.f;
    
    public void setGlobalVolume(float globalVolume) {
        this.globalVolume = clamp(globalVolume);
    }
    
    public float getGlobalVolume() {
        return globalVolume;
    }
    
    public void setMusicVolume(float musicVolume) {
        this.musicVolume = clamp(musicVolume);
    }
    
    public float getMusicVolume() {
        return musicVolume;
    }
    
    public void setSoundVolume(float soundVolume) {
        this.soundVolume = clamp(soundVolume);
    }
    
    public float getSoundVolume() {
        return soundVolume;
    }
    
    private float clamp(float value) {
        return MathUtils.clamp(value, 0.f, 1.f);
    }
}
