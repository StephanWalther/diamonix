package tools.misc;

import com.badlogic.gdx.math.*;

import misc.*;
import tools.common.*;
import tools.mapping.*;
import tools.progressLine.*;
import tools.screen.*;

public class CleanPack implements Updatable, Drawable, Removable {
    private final Whole whole;
    private final Vector2 initialScale;
    private final float initialAlpha;
    private final Timer timer = new Timer(Constants.SPAWN_TIME).decrease();
    private final ProgressLineInfo progressLineInfo;
    
    public CleanPack(Whole whole) {
        this(whole, null);
    }
    
    public CleanPack(Whole whole, ProgressLineInfo progressLineInfo) {
        this.whole = whole;
        initialScale = whole.getDefaultPhysicComponent().getScale();
        initialAlpha = whole.getDefaultColorComponent().getColorA();
        timer.setTime(timer.getEndTime());
        this.progressLineInfo = progressLineInfo;
    }
    
    @Override
    public void update(float dt) {
        timer.update(dt);
        float progress = timer.getProgress();
        float limit = 0.2f;
        if (progressLineInfo != null && progressLineInfo.getProgress() < limit) {
            progress = progress*(progressLineInfo.getProgress()/limit);
        }
        float scale = Mapping.scaleUp.calculate(progress);
        whole.getDefaultPhysicComponent().setScale(initialScale.x*scale, initialScale.y*scale);
        float alpha = PositiveSinWave.halfPeriod.calculate(progress);
        whole.getDefaultColorComponent().setColorA(initialAlpha*alpha);
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(whole);
    }
    
    @Override
    public boolean isDone() {
        return timer.getTime() == 0 ||
                 (progressLineInfo != null && progressLineInfo.getProgress() == 0);
    }
    
    public Whole getWhole() {
        return whole;
    }
}
