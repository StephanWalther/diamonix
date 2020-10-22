package tools.input.processors;

import tools.common.*;

public interface InputProcessor extends InputProcessable {
    boolean receiveKeyDown(int keycode);
    
    boolean receiveKeyUp(int keycode);
    
    boolean receiveKeyTyped(char character);
    
    boolean receiveTouchDown(float x, float y, int pointer, int button);
    
    boolean receiveTouchUp(float x, float y, int pointer, int button);
    
    boolean receiveTouchDragged(float x, float y, int pointer);
    
    boolean receiveMouseMoved(float x, float y);
    
    boolean receiveScrolled(int amount);
    
    void receiveResize(int width, int height);
    
    void receiveReset();
}
