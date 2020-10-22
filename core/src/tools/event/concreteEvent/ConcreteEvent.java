package tools.event.concreteEvent;

import tools.event.*;

public class ConcreteEvent extends EventTimer {
    private EventStartListener eventStartListener = null;
    private EventProgressListener eventProgressListener = null;
    private EventEndListener eventEndListener = null;
    
    public ConcreteEvent(final float length) {
        timer.setEndTime(length);
        timer.setTime(length);
    }
    
    @Override
    public void update(final float dt) {
        if (isRunning()) {
            super.update(dt);
            if (isDone()) notifyEnd();
            else notifyProgress();
        }
    }
    
    private boolean isRunning() {
        return !isPaused() && !isDone();
    }
    
    @Override
    public ConcreteEvent start() {
        super.start();
        notifyStart();
        return this;
    }
    
    @Override
    public ConcreteEvent stop() {
        super.stop();
        return this;
    }
    
    private void notifyStart() {
        if (eventStartListener != null) eventStartListener.eventStarted();
    }
    
    private void notifyProgress() {
        if (eventProgressListener != null) {
            eventProgressListener.eventProgressed(timer.getTime()/timer.getEndTime());
        }
    }
    
    private void notifyEnd() {
        if (eventEndListener != null) eventEndListener.eventEnded();
    }
    
    public void setEventStartListener(final EventStartListener eventStartListener) {
        this.eventStartListener = eventStartListener;
    }
    
    public void setEventProgressListener(final EventProgressListener eventProgressListener) {
        this.eventProgressListener = eventProgressListener;
    }
    
    public void setEventEndListener(final EventEndListener eventEndListener) {
        this.eventEndListener = eventEndListener;
    }
}
