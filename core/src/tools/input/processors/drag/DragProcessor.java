package tools.input.processors.drag;

import java.util.*;

import tools.common.*;
import tools.input.input.*;
import tools.input.processors.grab.*;

public class DragProcessor implements InputProcessable, GrabListener {
    private final GrabProcessor grabProcessor;
    private final ArrayList<DragListener> dragListeners = new ArrayList<DragListener>();
    private final DragStarter dragStarter;
    
    public DragProcessor(DragStarter dragStarter) {
        grabProcessor = new GrabProcessor(this);
        this.dragStarter = dragStarter;
    }
    
    @Override
    public boolean processInput(Input input) {
        return grabProcessor.processInput(input);
    }
    
    @Override
    public boolean grabTry(float x, float y, int pointer) {
        if (dragStarter.canDragStart(x, y, pointer)) {
            for (DragListener dragListener : dragListeners) dragListener.dragStarted(x, y, pointer);
            return true;
        }
        return false;
    }
    
    @Override
    public void grabMoved(float x, float y, int pointer) {
        for (DragListener dragListener : dragListeners) dragListener.dragged(x, y, pointer);
    }
    
    @Override
    public void grabReleased(float x, float y, int pointer) {
        for (DragListener dragListener : dragListeners) dragListener.dragEnded(x, y, pointer);
    }
    
    @Override
    public void grabReset(int pointer) {
        for (DragListener dragListener : dragListeners) dragListener.dragReset(pointer);
    }
    
    public DragProcessor addDragListener(DragListener dragListener) {
        dragListeners.add(dragListener);
        return this;
    }
}
