package tools.input.processors.tap;

import com.badlogic.gdx.math.*;

import java.util.*;

import tools.common.*;
import tools.input.input.*;
import tools.input.processors.grab.*;

public class TapProcessor implements InputProcessable, Updatable, GrabListener {
    private final GrabProcessor grabProcessor;
    private final ArrayList<TapListener> tapListeners = new ArrayList<TapListener>();
    private Containable containable;
    private boolean currentTapIsInside = false;
    private final Vector2 lastTapPosition = new Vector2();
    
    public TapProcessor(Containable containable) {
        grabProcessor = new GrabProcessor(this);
        this.containable = containable;
    }
    
    @Override
    public boolean processInput(Input input) {
        return grabProcessor.processInput(input);
    }
    
    @Override
    public void update(float dt) {
        if (grabProcessor.isGrabbed()) checkForTapMovement();
    }
    
    @Override
    public boolean grabTry(float x, float y, int pointer) {
        if (containable.contains(x, y)) {
            for (TapListener tapListener : tapListeners) tapListener.tapStarted();
            currentTapIsInside = true;
            lastTapPosition.set(x, y);
            return true;
        }
        return false;
    }
    
    @Override
    public void grabMoved(float x, float y, int pointer) {
        lastTapPosition.set(x, y);
        checkForTapMovement();
    }
    
    private void checkForTapMovement() {
        boolean nextTapIsOutside
          = !containable.contains(lastTapPosition.x, lastTapPosition.y);
        if (currentTapIsInside && nextTapIsOutside) {
            for (TapListener tapListener : tapListeners) tapListener.tapMovedOutside();
            currentTapIsInside = !currentTapIsInside;
        } else if (!currentTapIsInside && !nextTapIsOutside) {
            for (TapListener tapListener : tapListeners) tapListener.tapMovedInside();
            currentTapIsInside = !currentTapIsInside;
        }
    }
    
    @Override
    public void grabReleased(float x, float y, int pointer) {
        if (containable.contains(x, y)) {
            for (TapListener tapListener : tapListeners) tapListener.tapEndedInside();
        } else {
            for (TapListener tapListener : tapListeners) tapListener.tapEndedOutside();
        }
        currentTapIsInside = false;
        lastTapPosition.set(x, y);
    }
    
    @Override
    public void grabReset(int pointer) {
        for (TapListener tapListener : tapListeners) tapListener.tapReset();
    }
    
    public void setContainable(Containable containable) {
        this.containable = containable;
    }
    
    public TapProcessor addTapListener(TapListener tapListener) {
        tapListeners.add(tapListener);
        return this;
    }
    
    public boolean isTapped() {
        return grabProcessor.isGrabbed();
    }
}
