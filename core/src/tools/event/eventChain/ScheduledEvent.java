package tools.event.eventChain;

import tools.event.*;

class ScheduledEvent implements Comparable<ScheduledEvent> {
    final Event event;
    final float scheduleTime;
    
    ScheduledEvent(final Event event, final float scheduleTime) {
        this.event = event;
        this.scheduleTime = scheduleTime;
    }
    
    @Override
    public int compareTo(final ScheduledEvent scheduledEvent) {
        return scheduleTime < scheduledEvent.scheduleTime ? -1 : 1;
    }
}
