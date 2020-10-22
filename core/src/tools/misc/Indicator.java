package tools.misc;

import tools.common.*;
import tools.mapping.*;
import tools.progressLine.*;

public class Indicator implements Updatable {
    private final Timer timer = new Timer(1.f).increase();
    private Mapping scaleMapping = new Concatenation(new Polynomial(1.f, 0.f),
      PositiveSinWave.fullPeriod);
    private Mapping alphaMapping = new Concatenation(new Polynomial(1.f, 0.f),
      PositiveSinWave.fullPeriod);
    
    public void setPeriodTime(float periodTime) {
        timer.setEndTime(periodTime);
    }
    
    public void setScaleBounds(float min, float max) {
        scaleMapping = new Concatenation(new Polynomial(max - min, min),
          PositiveSinWave.fullPeriod);
    }
    
    public void setAlphaBounds(float min, float max) {
        alphaMapping = new Concatenation(new Polynomial(max - min, min),
          PositiveSinWave.fullPeriod);
    }
    
    @Override
    public void update(float dt) {
        timer.update(dt);
        if (timer.getProgress() == 1.f) timer.setTime(0);
    }
    
    public float getScale() {
        return scaleMapping.calculate(timer.getProgress());
    }
    
    public float getAlpha() {
        return alphaMapping.calculate(timer.getProgress());
    }
}
