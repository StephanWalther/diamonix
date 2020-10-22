package tools.input.processors;

import tools.input.input.*;

public class DefaultInputProcessor implements InputProcessor {
    @Override
    public boolean processInput(Input input) {
        return input.process(this);
    }
    
    @Override
    public boolean receiveKeyDown(int keycode) {
        return false;
    }
    
    @Override
    public boolean receiveKeyUp(int keycode) {
        return false;
    }
    
    @Override
    public boolean receiveKeyTyped(char character) {
        return false;
    }
    
    @Override
    public boolean receiveTouchDown(float x, float y, int pointer, int button) {
        return false;
    }
    
    @Override
    public boolean receiveTouchDragged(float x, float y, int pointer) {
        return false;
    }
    
    @Override
    public boolean receiveTouchUp(float x, float y, int pointer, int button) {
        return false;
    }
    
    @Override
    public boolean receiveMouseMoved(float x, float y) {
        return false;
    }
    
    @Override
    public boolean receiveScrolled(int amount) {
        return false;
    }
    
    @Override
    public void receiveResize(int width, int height) {}
    
    @Override
    public void receiveReset() {}
}
