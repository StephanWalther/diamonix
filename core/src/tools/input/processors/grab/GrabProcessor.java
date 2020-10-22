package tools.input.processors.grab;

import tools.input.processors.*;

public class GrabProcessor extends DefaultInputProcessor {
    private GrabListener grabListener;
    private boolean grabbed = false;
    private int pointer = 0;
    private boolean automaticPointer = false;
    
    public GrabProcessor() {}
    
    public GrabProcessor(GrabListener grabListener) {
        this.grabListener = grabListener;
    }
    
    public void setGrabListener(GrabListener grabListener) {
        this.grabListener = grabListener;
    }
    
    public void setPointer(int pointer) {
        this.pointer = pointer;
    }
    
    @Override
    public boolean receiveTouchDown(float x, float y, int pointer, int button) {
        if (automaticPointer && !grabbed) this.pointer = pointer;
        else if (this.pointer != pointer) return false;
        if (grabListener.grabTry(x, y, pointer)) {
            grabbed = true;
            return true;
        }
        return false;
    }
    
    @Override
    public boolean receiveTouchDragged(float x, float y, int pointer) {
        if (this.pointer == pointer && grabbed) {
            grabListener.grabMoved(x, y, pointer);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean receiveTouchUp(float x, float y, int pointer, int button) {
        if (this.pointer == pointer && grabbed) {
            grabbed = false;
            grabListener.grabReleased(x, y, pointer);
            return true;
        }
        return false;
    }
    
    @Override
    public void receiveReset() {
        if (grabbed) {
            grabbed = false;
            grabListener.grabReset(pointer);
        }
    }
    
    public boolean isGrabbed() {
        return grabbed;
    }
    
    public void activateAutomaticPointer() {
        automaticPointer = true;
    }
    
    public void deactivateAutomaticPointer() {
        automaticPointer = false;
    }
}
