package application.timeHandler;

import com.badlogic.gdx.*;

public class DebugTimeHandler extends TimeHandler {
    private int frames = 0;
    private boolean active = false;
    
    @Override
    public boolean receiveKeyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.SPACE: {
                active = !active;
                return true;
            } case Input.Keys.A: {
                if (active) {
                    frames++;
                    return true;
                }
            } case Input.Keys.S: {
                if (active) {
                    frames += 10;
                    return true;
                }
            } case Input.Keys.D: {
                if (active) {
                    frames += 100;
                    return true;
                }
            } case Input.Keys.F: {
                if (active) {
                    frames += 600;
                    return true;
                }
            }
        }
        return false;
    }
    
    public int getUpdateCallCount(float timeTillLastCall) {
        if (active) return removeFrames();
        else return super.getUpdateCallCount(timeTillLastCall);
    }
    
    private int removeFrames() {
        int temp = frames;
        frames = 0;
        return temp;
    }
}
