package tools.event.eventChain;

import java.util.*;

import tools.event.*;

class SortedEventList {
    private final List<ScheduledEvent> events = new ArrayList<ScheduledEvent>();
    
    void update(final float dt) {
        for (final ScheduledEvent scheduledEvent : events) scheduledEvent.event.update(dt);
    }
    
    void pause() {
        for (final ScheduledEvent scheduledEvent : events) scheduledEvent.event.pause();
    }

    void resume() {
        for (final ScheduledEvent scheduledEvent : events) scheduledEvent.event.resume();
    }

    void stop() {
        for (final ScheduledEvent scheduledEvent : events) scheduledEvent.event.stop();
    }

    boolean isDone() {
        for (final ScheduledEvent scheduledEvent : events) {
            if (!scheduledEvent.event.isDone()) return false;
        }
        return true;
    }
    
    void add(final Event event, final float scheduleTime) {
        events.add(new ScheduledEvent(event, scheduleTime));
        Collections.sort(events);
    }
    
    ScheduledEvent getFirst() {
        return events.get(0);
    }
    
    boolean hasNext(final ScheduledEvent currentEvent) {
        return events.indexOf(currentEvent) < events.size() - 1;
    }
    
    ScheduledEvent getNext(final ScheduledEvent currentEvent) {
        return events.get(events.indexOf(currentEvent) + 1);
    }
}
