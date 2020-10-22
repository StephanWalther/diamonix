package tools.progressLine;

import com.badlogic.gdx.math.*;

import tools.common.*;

public class Timer implements ProgressLine, Removable {
    private float endTime = Float.MAX_VALUE;
    private float time = 0;
    private boolean increasing = false;
    private float increaseFactor = 1.f;
    private float decreaseFactor = 1.f;
    
    public Timer() {}
    
    public Timer(float endTime) {
        this.endTime = endTime;
        checkEndTime();
    }
    
    private void checkEndTime() {
        if (endTime < 0) throw new java.lang.IllegalArgumentException("EndTime must be positive.");
    }
    
    @Override
    public void update(final float dt) {
        if (increasing) {
            time += increaseFactor*dt;
            if (endTime < time) time = endTime;
        } else {
            time -= decreaseFactor*dt;
            if (time < 0) time = 0;
        }
    }
    
    public Timer setEndTime(float endTime) {
        float progress = getProgress();
        this.endTime = endTime;
        checkEndTime();
        time = endTime*progress;
        return this;
    }
    
    public float getEndTime() {
        return endTime;
    }
    
    public Timer setTime(final float time) {
        this.time = Math.max(0, Math.min(time, endTime));
        return this;
    }
    
    public float getTime() {
        return time;
    }
    
    @Override
    public Timer setProgress(float progress) {
        time = progress*endTime;
        time = MathUtils.clamp(time, 0, endTime);
        return this;
    }
    
    @Override
    public float getProgress() {
        return endTime == 0 ? 1 : time/endTime;
    }
    
    @Override
    public Timer increase() {
        increasing = true;
        return this;
    }
    
    @Override
    public Timer decrease() {
        increasing = false;
        return this;
    }
    
    @Override
    public boolean isIncreasing() {
        return increasing;
    }
    
    public Timer setIncreaseFactor(float increaseFactor) {
        this.increaseFactor = increaseFactor;
        return this;
    }
    
    public float getIncreaseFactor() {
        return increaseFactor;
    }
    
    public Timer setDecreaseFactor(float decreaseFactor) {
        this.decreaseFactor = decreaseFactor;
        return this;
    }
    
    public float getDecreaseFactor() {
        return decreaseFactor;
    }
    
    @Override
    public Timer extract() {
        Timer timer = new Timer(endTime);
        timer.setTime(time);
        if (isIncreasing()) timer.increase();
        timer.setIncreaseFactor(increaseFactor);
        timer.setDecreaseFactor(decreaseFactor);
        return timer;
    }
    
    @Override
    public boolean isDone() {
        boolean isDoneIncreasing = isIncreasing() && getProgress() == 1;
        boolean isDoneDecreasing = !isIncreasing() && getProgress() == 0;
        return isDoneIncreasing || isDoneDecreasing;
    }
}
