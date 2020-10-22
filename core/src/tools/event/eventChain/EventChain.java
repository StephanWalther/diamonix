package tools.event.eventChain;

import tools.event.*;

public class EventChain extends EventTimer {
    private final SortedEventList sortedEventList = new SortedEventList();
    private ScheduledEvent currentEvent = null;
    
    @Override
    public void update(final float dt) {
        super.update(dt);
        checkChainedEventStart();
        sortedEventList.update(dt);
    }
    
    private void checkChainedEventStart() {
        while (currentEventNeedsStart()) {
            currentEvent.event.start();
            if (sortedEventList.hasNext(currentEvent)) {
                currentEvent = sortedEventList.getNext(currentEvent);
            }
            else currentEvent = null;
        }
    }
    
    private boolean currentEventNeedsStart() {
        return currentEvent != null && currentEvent.scheduleTime <= timer.getTime();
    }
    
    @Override
    public EventChain start() {
        super.start();
        sortedEventList.stop();
        currentEvent = sortedEventList.getFirst();
        return this;
    }
    
    @Override
    public EventChain pause() {
        super.pause();
        sortedEventList.pause();
        return this;
    }
    
    @Override
    public EventChain resume() {
        super.resume();
        sortedEventList.resume();
        return this;
    }
    
    @Override
    public EventChain stop() {
        super.stop();
        currentEvent = null;
        sortedEventList.stop();
        return this;
    }
    
    @Override
    public boolean isDone() {
        return super.isDone() && sortedEventList.isDone();
    }
    
    public void add(final Event event, final float scheduleTime) {
        if (!isDone()) {
            throw new IllegalStateException("Cannot absorb events to an already running "
                                                        + "EventChain.");
        }
        sortedEventList.add(event, scheduleTime);
        timer.setEndTime(Math.max(scheduleTime, timer.getEndTime()));
        timer.setTime(timer.getEndTime());
    }
}
