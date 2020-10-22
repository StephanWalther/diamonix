package tools.event;

import tools.progressLine.*;

public abstract class EventTimer implements Event {
    protected final Timer timer = new Timer(0);
    private boolean paused = false;
    
    public EventTimer() {}
    
    @Override
    public void update(final float dt) {
        if (!paused) timer.update(dt);
    }
    
    @Override
    public EventTimer start() {
        timer.setTime(0);
        paused = false;
        return this;
    }
    
    @Override
    public EventTimer pause() {
        paused = true;
        return this;
    }
    
    @Override
    public boolean isPaused() {
        return paused;
    }
    
    @Override
    public EventTimer resume() {
        paused = false;
        return this;
    }
    
    @Override
    public EventTimer stop() {
        timer.setTime(timer.getEndTime());
        return this;
    }
    
    @Override
    public boolean isDone() {
        return timer.getTime() == timer.getEndTime() && timer.isIncreasing();
    }
}
