package core;

import core.objects.audio.*;

public final class Sounds {
    static SoundPlayer soundPlayer;
    
    private Sounds() {}
    
    public static Sound get(String string) {
        return soundPlayer.getSound(string);
    }
    
    public static LimitedSound get(String string, float timeLimit) {
        return soundPlayer.getLimitedSound(string, timeLimit);
    }
}
