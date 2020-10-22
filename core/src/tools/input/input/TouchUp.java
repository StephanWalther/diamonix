package tools.input.input;

import tools.input.processors.*;

public final class TouchUp implements Input {
    private final float x;
    private final float y;
    private final int pointer;
    private final int button;
    
    public TouchUp(float x, float y, int pointer, int button) {
        this.x = x;
        this.y = y;
        this.pointer = pointer;
        this.button = button;
    }
    
    @Override
    public boolean process(InputProcessor inputProcessor) {
        return inputProcessor.receiveTouchUp(x, y, pointer, button);
    }
}
