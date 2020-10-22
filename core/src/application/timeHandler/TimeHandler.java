package application.timeHandler;

import tools.input.processors.*;
import tools.misc.*;

public abstract class TimeHandler extends DefaultInputProcessor {
    private final Accumulator timeAccumulator = new Accumulator();
    
    TimeHandler() {
        float updatesPerSecond = 60.f;
        float timeTillUpdate = 1.f/updatesPerSecond;
        int maxUpdatesBetweenProgressCalls = 2;
        timeAccumulator.setSubtractionAmount(timeTillUpdate);
        timeAccumulator.setAccumulationLimit(timeTillUpdate*maxUpdatesBetweenProgressCalls);
    }
    
    public int getUpdateCallCount(float timeTillLastCall) {
        timeAccumulator.add(timeTillLastCall);
        return timeAccumulator.subtract();
    }
    
    public float getDt() {
        return timeAccumulator.getSubtractionAmount();
    }
}
