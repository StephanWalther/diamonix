package core.objects.audio;

public class Sound {
    private final String string;
    private final SoundPlayer soundPlayer;
    private float volume;
    
    Sound(String string, SoundPlayer soundPlayer) {
        this.string = string;
        this.soundPlayer = soundPlayer;
        volume = 1.f;
    }
    
    public void play() {
        soundPlayer.playSound(string, volume);
    }
    
    public void setVolume(float volume) {
        if (volume < 0 || 1 < volume)
            throw new IllegalArgumentException("Volume have to be between 0 and 1.");
        this.volume = volume;
    }
    
    public float getVolume() {
        return volume;
    }
}
