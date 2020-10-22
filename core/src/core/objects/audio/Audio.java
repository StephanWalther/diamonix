package core.objects.audio;

import core.objects.assets.*;

public class Audio {
    public final Volume volume;
    public final MusicPlayer musicPlayer;
    public final SoundPlayer soundPlayer;
    
    public Audio(final Assets assets) {
        volume = new Volume();
        musicPlayer = new MusicPlayer(assets, volume);
        soundPlayer = new SoundPlayer(assets, volume);
    }
}
