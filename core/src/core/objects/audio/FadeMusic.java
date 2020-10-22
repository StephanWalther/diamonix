package core.objects.audio;

import com.badlogic.gdx.audio.*;

import tools.common.*;
import tools.mapping.*;
import tools.progressLine.*;

class FadeMusic implements Updatable, Removable {
    private final Timer musicChangeVolumeTimer;
    private final Volume volume;
    final Music music;
    
    FadeMusic(Volume volume, Music music, float fadeInTime) {
        musicChangeVolumeTimer = new Timer(fadeInTime).increase();
        this.volume = volume;
        this.music = music;
        music.setVolume(0);
        music.setLooping(true);
        music.play();
    }
    
    @Override
    public void update(float dt) {
        musicChangeVolumeTimer.update(dt);
        float musicChangeVolume = PositiveSinWave.halfPeriod.calculate(
          musicChangeVolumeTimer.getProgress());
        music.setVolume(volume.getGlobalVolume()*volume.getMusicVolume()*musicChangeVolume);
    }
    
    void fadeOut(float fadeOutTime) {
        musicChangeVolumeTimer.setEndTime(fadeOutTime);
        musicChangeVolumeTimer.decrease();
    }
    
    @Override
    public boolean isDone() {
        return musicChangeVolumeTimer.getTime() == 0;
    }
}
